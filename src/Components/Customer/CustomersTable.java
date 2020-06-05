package Components.Customer;

import DbDao.DbCustomerDetailsDao;
import POJO.CustomerDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.DBUtils;

import java.util.stream.Stream;

/**
 * RUBRIC B.   Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
 *
 */
public class CustomersTable {
    private TableView<CustomerDetails> customerTableView = new TableView<CustomerDetails>();
    private TableColumn<CustomerDetails, String> nameColumn = new TableColumn("Name");
    private TableColumn<CustomerDetails, String> phoneColumn = new TableColumn("Phone");
    private TableColumn<CustomerDetails, String> addressColumn = new TableColumn("Address");
    private TableColumn<CustomerDetails, String> address2Column = new TableColumn("Address 2");
    private TableColumn<CustomerDetails, String> postalCodeColumn = new TableColumn("Postal Code");
    private TableColumn<CustomerDetails, String> cityColumn = new TableColumn("City");
    private TableColumn<CustomerDetails, String> countryColumn = new TableColumn("Country");
    ObservableList<CustomerDetails> customers = FXCollections.observableArrayList();


    public void initialize() throws Exception {
        customerTableView.getStylesheets().add("CSS/tableView.css");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        address2Column.setCellValueFactory(new PropertyValueFactory<>("address2"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

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
                                        Stage stage;
                                        try {
                                            stage = new CustomerCard().getEditCustomerCardStage(customer);
                                            stage.showAndWait();
                                            // update tableView with new customer stream
                                            if(!stage.isShowing()) {
                                                customers.clear();
                                                Stream<CustomerDetails> stream2 = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource()).getAll();
                                                stream2.forEach(c->customers.add(c));
                                                customerTableView.setItems(customers);
                                            }
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
                                        CustomerDetails customer = getTableView().getItems().get(getIndex());
                                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                                        a.setTitle("Delete Customer");
                                        a.setHeaderText("Are you sure you would like to permanently delete "
                                                + customer.getCustomerName() + " from the database?");
                                        a.setContentText(
                                                "This will also delete the associated address and appointments. \n" +
                                                        "Click OK if you are sure. Click Cancel to exit this window.");
                                        a.showAndWait().ifPresent((btnType) -> {
                                            if (btnType == ButtonType.OK) {
                                                // remove customer from table
                                                getTableView().getItems().remove(customer);
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

        customerTableView.setEditable(true);
        customerTableView.getColumns().addAll(nameColumn, phoneColumn, addressColumn, address2Column, postalCodeColumn,
                cityColumn, countryColumn, editColumn, deleteColumn);
        customerTableView.setItems(customers);
    }

    public TableView<CustomerDetails> getView() throws Exception {
        initialize();
        return customerTableView;
    }
}
