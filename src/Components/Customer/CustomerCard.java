package Components.Customer;

import Components.ComboBoxes;
import DbDao.DbAddressDao;
import DbDao.DbCityDao;
import DbDao.DbCountryDao;
import DbDao.DbCustomerDao;
import POJO.*;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import utils.DBUtils;

import java.util.Optional;

public class CustomerCard {
    /**
     * ## Provide the ability to add, update, and delete customer records in the database, including name, address, and
     * phone number.
     *
     * Title: All Customers
     * Search: Label("Search"), TextInput("By Name, Address, Phone)
     * Add New: Button("New Customer")
     * Customer Cards: 2 x 2 List
     *      Border: Thick Grey
     *      Customer Name, Edit Button
     *      Customer Address
     *      Customer Phone
     *
     */

    CustomerDetails customer;

    GridPane gridPane = new GridPane();
    // Labels for card view; textFields for onActionEditButton
    Label customerNameLbl = new Label("Customer");
    ComboBox<Customer> customerComboBox = new ComboBoxes().getCustomers();
    TextField customerNameTextFld = new TextField();

    Label phoneLbl = new Label("Phone");
    TextField phoneTxtFld = new TextField();

    Label addressLine1Lbl = new Label("Address");
    TextField addressLine1TxtFld = new TextField();

    Label addressLine2Lbl = new Label("Address 2");
    TextField addressLine2TxtFld = new TextField();

    Label cityLbl = new Label("City");
    // TODO fix city and country combo boxes like customers
    ComboBox<City> cityComboBox = new ComboBoxes().getCities();

    Label postalCodeLbl = new Label("Postal Code");
    TextField postalCodeTxtFld = new TextField();

    Label countryLbl = new Label("Country");
    ComboBox<Country> countryComboBox = new ComboBoxes().getCountries();

    Optional<City> city;
    Optional<Country> country;
    public CustomerCard() throws Exception {
    }

    public CustomerCard(CustomerDetails customer) throws Exception {
        this.customer = customer;
        this.city = new DbCityDao(DBUtils.getMySQLDataSource()).getByName(customer.getCity());
        this.country = new DbCountryDao(DBUtils.getMySQLDataSource()).getByName(customer.getCountry());
        customerComboBox.getSelectionModel().select(customer.getCustomerId());
        phoneTxtFld.setText(customer.getPhone());
        addressLine1TxtFld.setText(customer.getAddress());
        addressLine2TxtFld.setText(customer.getAddress2());
        cityComboBox.getSelectionModel().select(city.get());
        postalCodeTxtFld.setText(customer.getPostalCode());
        countryComboBox.getSelectionModel().select(country.get());
    }

    private GridPane buildNewCustomerGridPane() {
        ColumnConstraints columnConstraints = new ColumnConstraints(150);
        ColumnConstraints columnConstraints1 = new ColumnConstraints(150);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints1);

        int rowCount = 8;
        for(int i = 0; i<rowCount; i++) {
            RowConstraints rowConstraints = new RowConstraints(37);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        double minWidth = 75;

        //START LABELS AND INPUTS
        HBox nameHBox = new HBox(50);
        customerNameLbl.setMinSize(minWidth, 25);
        customerNameTextFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        nameHBox.getChildren().addAll(customerNameLbl, customerNameTextFld);
        gridPane.add(nameHBox, 0, 0);

        HBox phoneHbox = new HBox(50);
        phoneLbl.setMinSize(minWidth, 25);
        phoneTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        phoneHbox.getChildren().addAll(phoneLbl,phoneTxtFld);
        gridPane.add(phoneHbox, 0, 1);

        HBox address1HBox = new HBox(50);
        addressLine1Lbl.setMinSize(minWidth, 25);
        addressLine1TxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        address1HBox.getChildren().addAll(addressLine1Lbl, addressLine1TxtFld);
        gridPane.add(address1HBox, 0, 2);

        HBox address2 = new HBox(50);
        addressLine2Lbl.setMinSize(minWidth, 25);
        addressLine2TxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        address2.getChildren().addAll(addressLine2Lbl, addressLine2TxtFld);
        gridPane.add(address2, 0, 3);

        HBox cityHBox = new HBox(50);
        cityLbl.setMinSize(minWidth, 25);
        cityComboBox.setMinSize(columnConstraints1.getPrefWidth(), 25);
        cityHBox.getChildren().addAll(cityLbl, cityComboBox);
        gridPane.add(cityHBox, 0, 4);

        HBox postalCodeHBox = new HBox(50);
        postalCodeLbl.setMinSize(minWidth, 25);
        postalCodeTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        postalCodeHBox.getChildren().addAll(postalCodeLbl, postalCodeTxtFld);
        gridPane.add(postalCodeHBox, 0, 5);

        HBox countryHBox = new HBox(50);
        countryLbl.setMinSize(minWidth, 25);
        countryComboBox.setMinSize(columnConstraints1.getPrefWidth(), 25);
        countryHBox.getChildren().addAll(countryLbl, countryComboBox);
        gridPane.add(countryHBox, 0, 6);
        //END LABELS AND INPUTS

        // START BUTTONS
        ButtonBar buttonBar = new ButtonBar();
        Button addBtn = new Button("Add");
        addBtn.onMouseClickedProperty().set(e-> {
            try {
                DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                Optional<Address> address = new DbAddressDao(DBUtils.getMySQLDataSource()).getByAddress(addressLine1TxtFld.getText(), phoneTxtFld.getText());
                customerDao.add(new Customer(customerDao.maxId()+1, customerNameTextFld.getText(), address.get().getId()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Customer successfully added to the database.");
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There was an error with the database.");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });
//            Button cancelBtn = new Button("Cancel");
//            cancelBtn.onMouseClickedProperty().set(e->stage.close());
        ButtonBar.setButtonData(addBtn, ButtonBar.ButtonData.OK_DONE);
//            ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(addBtn);
        // END BUTTONS
        // END BUTTONS

        gridPane.add(buttonBar, 1, 7);
        gridPane.setStyle(
                "-fx-border-width: 3.5; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: GREY;" +
                        "-fx-padding: 25, 25, 25, 25;" +
                        "-fx-label-padding: 5;"
        );
        return gridPane;
    }

    private void buildEditCustomerGridPane() {
        ColumnConstraints columnConstraints = new ColumnConstraints(150);
        ColumnConstraints columnConstraints1 = new ColumnConstraints(150);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints1);

        int rowCount = 8;
        for(int i = 0; i<rowCount; i++) {
            RowConstraints rowConstraints = new RowConstraints(37);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        double minWidth = 75;

        //START LABELS AND INPUTS
        HBox nameHBox = new HBox(50);
        customerNameLbl.setMinSize(minWidth, 25);
        customerComboBox.setMinSize(columnConstraints1.getPrefWidth(), 25);
        customerComboBox.getSelectionModel().select(customer.getCustomerId());
        nameHBox.getChildren().addAll(customerNameLbl, customerComboBox);
        gridPane.add(nameHBox, 0, 0);

        HBox phoneHbox = new HBox(50);
        phoneLbl.setMinSize(minWidth, 25);
        phoneTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        phoneTxtFld.setText(customer.getPhone());
        phoneHbox.getChildren().addAll(phoneLbl,phoneTxtFld);
        gridPane.add(phoneHbox, 0, 1);

        HBox address1HBox = new HBox(50);
        addressLine1Lbl.setMinSize(minWidth, 25);
        addressLine1TxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        addressLine1TxtFld.setText(customer.getAddress());
        address1HBox.getChildren().addAll(addressLine1Lbl, addressLine1TxtFld);
        gridPane.add(address1HBox, 0, 2);

        HBox address2 = new HBox(50);
        addressLine2Lbl.setMinSize(minWidth, 25);
        addressLine2TxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        addressLine2TxtFld.setText(customer.getAddress2());
        address2.getChildren().addAll(addressLine2Lbl, addressLine2TxtFld);
        gridPane.add(address2, 0, 3);

        HBox cityHBox = new HBox(50);
        cityLbl.setMinSize(minWidth, 25);
        cityComboBox.setMinSize(columnConstraints1.getPrefWidth(), 25);
        cityComboBox.getSelectionModel().select(city.get());
        cityHBox.getChildren().addAll(cityLbl, cityComboBox);
        gridPane.add(cityHBox, 0, 4);

        HBox postalCodeHBox = new HBox(50);
        postalCodeLbl.setMinSize(minWidth, 25);
        postalCodeTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
        postalCodeTxtFld.setText(customer.getPostalCode());
        postalCodeHBox.getChildren().addAll(postalCodeLbl, postalCodeTxtFld);
        gridPane.add(postalCodeHBox, 0, 5);

        HBox countryHBox = new HBox(50);
        countryLbl.setMinSize(minWidth, 25);
        countryComboBox.setMinSize(columnConstraints1.getPrefWidth(), 25);
        countryComboBox.getSelectionModel().select(country.get());
        countryHBox.getChildren().addAll(countryLbl, countryComboBox);
        gridPane.add(countryHBox, 0, 6);
        //END LABELS AND INPUTS

        // START BUTTONS
        ButtonBar buttonBar = new ButtonBar();
        Button updateBtn = new Button("Update");
        updateBtn.onMouseClickedProperty().set(e-> {
            try {
                DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                DbAddressDao addressDao = new DbAddressDao(DBUtils.getMySQLDataSource());
                // TODO address needs to update all or add new to all
                Optional<Address> address = new DbAddressDao(DBUtils.getMySQLDataSource()).getByAddress(addressLine1TxtFld.getText(), phoneTxtFld.getText());
                if(address.isPresent()){
                    // update current address
                    addressDao.update(new Address(address.get().getId(), addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityComboBox.getSelectionModel().getSelectedItem().getId(), postalCodeTxtFld.getText(), phoneTxtFld.getText()));
                } else {
                    // add new address to address table
                    // get maxId and increment by 1 for unique addressId (PK)
                    int newId = addressDao.getMaxId()+1;
                    addressDao.add(new Address(newId, addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityComboBox.getSelectionModel().getSelectedItem().getId(), postalCodeTxtFld.getText(), phoneTxtFld.getText()));
                    // set address to new address
                    address = addressDao.getById(newId);
                }
                // update customer table
                customerDao.update(
                        new Customer(
                                customer.getCustomerId(),
                                customerComboBox.getSelectionModel().getSelectedItem().getCustomerName(),
                                address.get().getId())
                );

                // Alert for success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Customer successfully updated.");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There was an error with the database.");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });
//            Button cancelBtn = new Button("Cancel");
//            cancelBtn.onMouseClickedProperty().set(e->stage.close());
        ButtonBar.setButtonData(updateBtn, ButtonBar.ButtonData.OK_DONE);
//            ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(updateBtn);
        // END BUTTONS

        gridPane.add(buttonBar, 1, 7);
        gridPane.setStyle(
                "-fx-border-width: 3.5; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: GREY;" +
                        "-fx-padding: 25, 25, 25, 25;" +
                        "-fx-label-padding: 5;"
        );
    }

    public GridPane getCustomerCard() {
        buildEditCustomerGridPane();
        return gridPane;
    }

    public GridPane getNewCustomerCardGridPane() {
        return buildNewCustomerGridPane();
    }
}
