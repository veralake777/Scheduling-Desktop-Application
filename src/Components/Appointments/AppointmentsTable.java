package Components.Appointments;

import Components.Main;
import DbDao.DbAppointmentDao;
import DbDao.DbCustomerDao;
import POJO.Appointment;
import POJO.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.DBUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * C.   Provide the ability to add, update, and delete appointments, capturing the type of appointment and a
 * link to the specific customer record in the database.
 */
public class AppointmentsTable {

    public AppointmentsTable(Main main) throws Exception {
        buildAnchorPane();
        this.main = main;
    }

    private Main main;
    // VBox to hold static left side view
    private VBox leftSideView = new VBox(0);

    // DYNAMIC right side view
    private Node rightSideView;

    //TilePane
    AnchorPane anchorPane;

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

        public String getCustomerName() {
            return customerName;
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
        customerName.setMinWidth(200);

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
                                            updateRightSideView(new AppointmentCard(appointment.getAppointmentId()).getEditAppointmentGridPane());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    setGraphic(editBtn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        editColumn.setCellFactory(editBtnCellFactory);

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
                                                // remove customer from table
                                                try {
                                                    DbAppointmentDao dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
                                                    dao.delete(dao.getById(appointment.getAppointmentId()).get());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                getTableView().getItems().remove(appointment);
                                                // TODO remove from database
                                            } else if (btnType == ButtonType.CANCEL) {
                                                a.close();
                                            }
//                                            clearDialogOptionSelections();;
//                                        System.out.println(customer.getCustomerName()
//                                                + "   " + customer.getPhone());
                                        });
                                    });
                                    setGraphic(deleteBtn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        deleteColumn.setCellFactory(deleteBtnCellFactory);

        // New Button below the table
        newAppointmentBtn.setOnAction(e-> {
              updateRightSideView(new AppointmentCard(main).getNewAppointmentGridPane());
//            Stage stage = null;
//            try {
//                stage = new AppointmentCard(main).getNewAppointmentStage();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            try {
//                stage.showAndWait();
//                if(!stage.isShowing()) {
//                    appointments.clear();
//                    setAppointments();
//                    appointmentTableView.setItems(appointments);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        });

        // init table
        appointmentTableView.setEditable(true);
        appointmentTableView.setPrefHeight(750);
        appointmentTableView.getColumns().addAll(type, customerName, editColumn, deleteColumn);
        appointmentTableView.setItems(appointments);
    }

    private void setAppointments() throws Exception {
        appointments.clear();
        Stream<Appointment> stream2 = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        stream2.forEach(a ->{
            Optional<Customer> customer = Optional.empty();
            try {
                customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(a.getCustomerId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            appointments.add(new LocalAppointment(a.getId(), a.getType(), customer.get().getCustomerName()));
            });
    }

    private VBox getLeftSideView() throws Exception {
        initialize();
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        newAppointmentBtn.setMaxWidth(screenSize.getWidth()/2);
        newAppointmentBtn.setMinHeight(screenSize.getHeight()*.1);
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

    private void buildAnchorPane() throws Exception {
        anchorPane = new AnchorPane();
        rightSideView = new Label("Click a button to load a view.");
        anchorPane.getChildren().addAll(getLeftSideView(),rightSideView);   // Add grid from Example 1-5
        AnchorPane.setLeftAnchor(leftSideView, 0.0);
        AnchorPane.setTopAnchor(rightSideView, 400.0);
        AnchorPane.setRightAnchor(rightSideView, 425.0);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void updateRightSideView(Node node) {
        rightSideView = node;
        anchorPane.getChildren().remove(1);
        anchorPane.getChildren().add(node);
        AnchorPane.setRightAnchor(rightSideView, 20.00);
        AnchorPane.setRightAnchor(rightSideView, 100.0);
    }
}
