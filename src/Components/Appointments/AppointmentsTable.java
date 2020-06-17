package Components.Appointments;

import DbDao.DbAppointmentDao;
import DbDao.DbCustomerDao;
import POJO.Appointment;
import POJO.Customer;
import POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Callback;
import utils.DBUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * C.   Provide the ability to add, update, and delete appointments, capturing the type of appointment and a
 * link to the specific customer record in the database.
 */
public class AppointmentsTable {

    public AppointmentsTable(User user) throws Exception {
        this.user = user;
        buildView();
    }

    private User user;
    // VBox to hold static left side view
    private VBox leftSideView = new VBox(0);

    // DYNAMIC right side view
    private Node rightSideView;

    // GridPane
    GridPane gridPane;

    //table view
    private TableView appointmentTableView = new TableView<Appointment>();

    // columns
    private TableColumn<LocalAppointment, String> type = new TableColumn("Appointment");
    private TableColumn<LocalAppointment, String> customerName = new TableColumn("Customer");
    ObservableList<LocalAppointment> appointments = FXCollections.observableArrayList();

    // new appointment button - edit and delete buttons are built into the table
    Button newAppointmentBtn = new Button("New Appointment");


    public static class LocalAppointment {
        private int appointmentId;
        private String appointmentType;
        private String customerName;

        LocalAppointment(int appointmentId, String appointmentType, String customerName) {
            this.appointmentId = appointmentId;
            this.appointmentType = appointmentType;
            this.customerName = customerName;
        }

        public int getAppointmentId() {
            return appointmentId;
        }

        public String getAppointmentType() {
            return appointmentType;
        }

        public void setAppointmentType(String appointmentType) {
            this.appointmentType = appointmentType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
    }

    public void initialize() throws Exception {
        // set columns to fill full width of table evenly
        appointmentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // get screen size and set table to 50% of width; set style sheet
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        appointmentTableView.setMaxSize(screenBounds.getWidth() * .5, screenBounds.getHeight());
        appointmentTableView.getStylesheets().add("CSS/tableView.css");

        // type column
        type.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        type.setMinWidth(200);

        // customer name column
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerName.setMinWidth(100);

        // set column data
        setAppointments();

        /**
         * BUTTON COLUMNS
         *
         * editColumn includes an edit button with an action handler that show a new stage where the customer
         *     information can be edited and updated.
         * deleteColumn includes a delete button with an action handler that shows an Alert that asks the user if they
         *     are sure they would like to permanently delete the customer and all their corresponding data
         *     (address, appointments) from the database
         **/
        TableColumn editColumn = new TableColumn("Edit");
        editColumn.setCellValueFactory(new PropertyValueFactory<>(null));

        Callback<TableColumn<LocalAppointment, String>, TableCell<LocalAppointment, String>> editBtnCellFactory
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<LocalAppointment, String> param) {
                        final TableCell<LocalAppointment, String> cell = new TableCell<>() {

                            final Button editBtn = new Button("Edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    editBtn.setOnAction(event -> {
                                        LocalAppointment appointment = getTableView().getItems().get(getIndex());
                                        try {
                                            updateRightSideView(new AppointmentCard(appointment.getAppointmentId(), AppointmentsTable.this).getEditAppointmentGridPane());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    setGraphic(editBtn);
                                    setText(null);
                                }
                            }
                        };
                        cell.setMinWidth(125);
                        return cell;
                    }
                };

        editColumn.setCellFactory(editBtnCellFactory);
        editColumn.setMinWidth(100);

        TableColumn deleteColumn = new TableColumn("Delete");
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(null));

        Callback<TableColumn<LocalAppointment, String>, TableCell<LocalAppointment, String>> deleteBtnCellFactory
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<LocalAppointment, String> param) {
                        final TableCell<LocalAppointment, String> cell = new TableCell<>() {

                            final Button deleteBtn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    deleteBtn.setOnAction(event -> {
                                        LocalAppointment appointment = getTableView().getItems().get(getIndex());
                                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                                        a.setTitle("Delete Appointment");
                                        a.setHeaderText("Are you sure you would like to permanently delete "
                                                + appointment.getAppointmentType() + " from the database?");
                                        a.setContentText(
                                                "This will also delete the associated address and appointments. \n" +
                                                        "Click OK if you are sure. Click Cancel to exit this window.");
                                        a.showAndWait().ifPresent((btnType) -> {
                                            if (btnType == ButtonType.OK) {
                                                // remove appointment from database
                                                try {
                                                    DbAppointmentDao dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
                                                    dao.delete(dao.getById(appointment.getAppointmentId()).get());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                // remove appointment from table
                                                getTableView().getItems().remove(appointment);
                                            } else if (btnType == ButtonType.CANCEL) {
                                                a.close();
                                            }
                                        });
                                    });
                                    setGraphic(deleteBtn);
                                    setText(null);
                                }
                            }
                        };
                        cell.setMinWidth(125);
                        return cell;
                    }
                };

        deleteColumn.setCellFactory(deleteBtnCellFactory);
        deleteColumn.setMinWidth(100);

        // New Button below the table
        newAppointmentBtn.setOnAction(e -> {
            try {
                updateRightSideView(new AppointmentCard(AppointmentsTable.this, user).getNewAppointmentGridPane());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // init table
        appointmentTableView.setEditable(true);
        appointmentTableView.setPrefHeight(800);
        appointmentTableView.setPrefSize(600, Screen.getPrimary().getBounds().getHeight() - 175);
        appointmentTableView.scrollTo(1);
        appointmentTableView.getColumns().addAll(type, customerName, editColumn, deleteColumn);
        appointmentTableView.setItems(appointments);
    }

    private void setAppointments() throws Exception {
        appointments.clear();
        Stream<Appointment> stream2 = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        stream2.forEach(a -> {
            if (a.getUserId() == user.getId()) {
                Optional<Customer> customer = Optional.empty();
                try {
                    customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(a.getCustomerId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appointments.add(new LocalAppointment(a.getId(), a.getType(), customer.get().getCustomerName()));
            }
        });
    }

    private VBox getLeftSideView() throws Exception {
        initialize();
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        newAppointmentBtn.setMaxWidth(screenSize.getWidth() / 2);
        newAppointmentBtn.setMinHeight(screenSize.getHeight() * .1);
        newAppointmentBtn.setStyle("-fx-font-family: 'Roboto Bold';\n" +
                "-fx-font-size: 25;\n" +
                "-fx-alignment: center;\n" +
                "-fx-font-weight: BOLD;\n" +
                "-fx-padding: 25 20;\n" +
                "-fx-background-color:#ebaa5d;\n");
        leftSideView.setMaxHeight(680);
        leftSideView.getChildren().addAll(appointmentTableView, newAppointmentBtn);
        return leftSideView;
    }

    private void buildView() throws Exception {
        gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints(600);
        gridPane.getColumnConstraints().add(col1);
        rightSideView = new Label("Click a button to load a view.");
        rightSideView.setStyle("-fx-alignment: CENTER;" +
                "-fx-padding: 0 0 0 500;");
        gridPane.add(getLeftSideView(), 0, 0);
        gridPane.add(rightSideView, 1, 0);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public TableView getAppointmentTableView() {
        type.setMinWidth(125);
        customerName.setMinWidth(225);
        return appointmentTableView;
    }

    public void updateRightSideView(Node node) {
        rightSideView = node;
        gridPane.getChildren().remove(1);
        gridPane.add(rightSideView, 1, 0);
    }
}
