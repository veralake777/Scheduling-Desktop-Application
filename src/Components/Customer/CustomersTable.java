package Components.Customer;

import DbDao.DbCustomerDao;
import DbDao.DbCustomerDetailsDao;
import POJO.Customer;
import POJO.CustomerDetails;
import POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Callback;
import utils.DBUtils;

import java.util.stream.Stream;

/**
 * RUBRIC B.   Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
 *
 */
public class CustomersTable {
    private User user;
    private TableView<CustomerDetails> customerTableView = new TableView<CustomerDetails>();
    private TableColumn<CustomerDetails, String> nameColumn = new TableColumn("Name");
    private TableColumn<CustomerDetails, String> phoneColumn = new TableColumn("Phone");
    private TableColumn<CustomerDetails, String> addressColumn = new TableColumn("Address");
    private TableColumn<CustomerDetails, String> address2Column = new TableColumn("Address 2");
    private TableColumn<CustomerDetails, String> postalCodeColumn = new TableColumn("Postal Code");
    private TableColumn<CustomerDetails, String> cityColumn = new TableColumn("City");
    private TableColumn<CustomerDetails, String> countryColumn = new TableColumn("Country");
    ObservableList<CustomerDetails> customers = FXCollections.observableArrayList();

    public CustomersTable(User user) {
        this.user = user;
    }

    // VBox to hold static left side view
    private VBox leftSideView = new VBox(10);

    // DYNAMIC right side view
    private Node rightSideView;

    // GridPane
    GridPane gridPane = new GridPane();

    public void initialize() throws Exception {
        customerTableView.getStylesheets().add("CSS/tableView.css");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        address2Column.setCellValueFactory(new PropertyValueFactory<>("address2"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        customers.clear();
        Stream<CustomerDetails> stream = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource()).getAll();
        stream.forEach(customer -> customers.add(customer));

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

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> editBtnCellFactory
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<CustomerDetails, String> param) {
                        final TableCell<CustomerDetails, String> cell = new TableCell<>() {

                            final Button editBtn = new Button("Edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    editBtn.setOnAction(event -> {
                                        CustomerDetails customer = getTableView().getItems().get(getIndex());
                                        try {
                                            updateRightSideView(new CustomerCard(customer, CustomersTable.this, CustomersTable.this.user).getCustomerCard());
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

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> deleteBtnCellFactory
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<CustomerDetails, String> param) {
                        final TableCell<CustomerDetails, String> cell = new TableCell<>() {

                            final Button deleteBtn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    deleteBtn.setOnAction(event -> {
                                        CustomerDetails customerDetails = getTableView().getItems().get(getIndex());
                                        // delete customer from database
                                        try {
                                            DbCustomerDao dao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                                            Customer customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(customerDetails.getCustomerId()).get();
                                            dao.delete(customer);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        // delete customer from table

                                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                                        a.setTitle("Delete Customer");
                                        a.setHeaderText("Are you sure you would like to permanently delete "
                                                + customerDetails.getCustomerName() + " from the database?");
                                        a.setContentText(
                                                "This will also delete the associated address and appointments. \n" +
                                                        "Click OK if you are sure. Click Cancel to exit this window.");
                                        a.showAndWait().ifPresent((btnType) -> {
                                            if (btnType == ButtonType.OK) {
                                                // remove customer from table
                                                getTableView().getItems().remove(customerDetails);
                                                // TODO remove from database
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
                        return cell;
                    }
                };

        deleteColumn.setCellFactory(deleteBtnCellFactory);

        customerTableView.setPrefHeight(700);
        customerTableView.setEditable(true);
        customerTableView.getColumns().addAll(nameColumn, phoneColumn, addressColumn, address2Column, postalCodeColumn,
                cityColumn, countryColumn, editColumn, deleteColumn);
        customerTableView.setItems(customers);
    }

    private VBox getLeftSideView() throws Exception {
        initialize();
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        Button newCustomerBtn = new Button("New Customer");
        newCustomerBtn.setMaxWidth(screenSize.getWidth()/2);
        newCustomerBtn.setMinHeight(screenSize.getHeight()*.1);
        newCustomerBtn.setStyle("-fx-font-family: 'Roboto Bold';\n" +
                "-fx-font-size: 25;\n" +
                "-fx-alignment: center;\n" +
                "-fx-font-weight: BOLD;\n" +
                "-fx-padding: 25 20;\n" +
                "-fx-background-color:#ebaa5d;\n");
        newCustomerBtn.setOnAction(e-> {
            try {
                updateRightSideView(new CustomerCard(this, user).getNewCustomerCardGridPane());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        leftSideView.setMaxHeight(680);
        leftSideView.getChildren().addAll(customerTableView, newCustomerBtn);
        return leftSideView;
    }

    public GridPane getView() throws Exception {
        gridPane.add(getLeftSideView(), 0, 0);
        rightSideView = new Label("Click a button to load a view.");
        rightSideView.setStyle("-fx-alignment: CENTER;" +
                "-fx-padding: 0 0 0 450");
        gridPane.add(rightSideView, 1, 0 );
        return gridPane;
    }

    public void updateRightSideView(Node node) {
        rightSideView = node;
        rightSideView.setStyle("-fx-alignment: CENTER;" +
                "-fx-padding: 0 0 0 300");
        gridPane.getChildren().remove(1);
        gridPane.add(rightSideView, 1, 0);
    }

    public ObservableList<CustomerDetails> getCustomersList() {
        return customers;
    }

    public TableView getTableView() {
        return customerTableView;
    }
}
