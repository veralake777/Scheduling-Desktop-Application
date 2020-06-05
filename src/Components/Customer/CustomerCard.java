package Components.Customer;

import Components.ComboBoxes;
import DbDao.DbAddressDao;
import DbDao.DbCustomerDao;
import DbDao.DbCustomerDetailsDao;
import POJO.Address;
import POJO.Customer;
import POJO.CustomerDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.DBUtils;


import java.io.IOException;
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


        GridPane gridPane = new GridPane();
        HBox hBox = new HBox(25);
        Button editBtn = new Button("Edit");
        // Labels for card view; textFields for onActionEditButton
        Label customerNameLbl = new Label("Customer");
        TextField customerNameTxtFld = new TextField();

        Label phoneLbl = new Label("Phone");
        TextField phoneTxtFld = new TextField();

        Label addressLine1Lbl = new Label("Address");
        TextField addressLine1TxtFld = new TextField();

        Label addressLine2Lbl = new Label("Address 2");
        TextField addressLine2TxtFld = new TextField();

        Label cityLbl = new Label("City");
        ComboBox<String> cityComboBox = new ComboBoxes().getCities();

        Label postalCodeLbl = new Label("Postal Code");
        TextField postalCodeTxtFld = new TextField();

        Label countryLbl = new Label("Country");
        ComboBox<String> countryComboBox = new ComboBoxes().getCountries();

    public CustomerCard() throws Exception {
    }

    // Dumb CustomerCard - used for new AddNewCustomer
        private void CustomerCard() throws Exception {
            Font labelFont = Font.font("Roboto", FontWeight.NORMAL, 14);
            customerNameLbl.setFont(labelFont);
            customerNameLbl.setText("Name");
            customerNameTxtFld.setText("enter customer name");

            phoneLbl.setFont(labelFont);
            phoneLbl.setText("Phone");
            phoneTxtFld.setText("enter customer phone");

            addressLine1Lbl.setFont(labelFont);
            addressLine1Lbl.setText("Address");
            addressLine1TxtFld.setText("enter customer street address");

            addressLine2Lbl.setFont(labelFont);
            addressLine2Lbl.setText("Address 2");
            addressLine2TxtFld.setText("apt. number, etc.");

            cityLbl.setFont(labelFont);
            cityLbl.setText("City");
//            cityComboBox.getCities();

            postalCodeLbl.setFont(labelFont);
            postalCodeLbl.setText("Postal Code");
            postalCodeTxtFld.setText("enter customer postal code");

            countryLbl.setFont(labelFont);
            countryLbl.setText("Country");
//            countryComboBox.getCountries();
        }

        // CustomerCard Constructor by customerId - used for editCustomer button
        private void CustomerCard(int customerId) throws Exception {
            DbCustomerDetailsDao dbCustomerDetailsDao = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource());
            Optional<CustomerDetails> customer = dbCustomerDetailsDao.getById(customerId);
            Font labelFont = Font.font("Roboto", FontWeight.NORMAL, 14);
            customer.ifPresent(c -> {
                customerNameLbl.setFont(labelFont);
                customerNameLbl.setText("Name");
                customerNameTxtFld.setText(c.getCustomerName());

                phoneLbl.setFont(labelFont);
                phoneLbl.setText("Phone");
                phoneTxtFld.setText(c.getPhone());

                addressLine1Lbl.setFont(labelFont);
                addressLine1Lbl.setText("Address");
                addressLine1TxtFld.setText(c.getAddress2());

                addressLine2Lbl.setFont(labelFont);
                addressLine2Lbl.setText("Address2");
                addressLine2TxtFld.setText(c.getAddress2());

                cityLbl.setFont(labelFont);
                cityLbl.setText("City");
                try {
//                    cityComboBox.getCities();
                    cityComboBox.getSelectionModel().select(c.getCity());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                postalCodeLbl.setFont(labelFont);
                postalCodeLbl.setText("Postal Code");
                postalCodeTxtFld.setText(c.getPostalCode());

                countryLbl.setFont(labelFont);
                countryLbl.setText(c.getCountry());
                try {
//                    countryComboBox.getCountries();
                    countryComboBox.getSelectionModel().select(c.getCountry());
                } catch (Exception e) {
//                    LOGGER.log(Level.SEVERE, e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        private Stage buildNewCustomerStage() {
            // INIT STAGE AND GRIDPANE
            Stage stage = new Stage();
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
            customerNameTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            nameHBox.getChildren().addAll(customerNameLbl, customerNameTxtFld);
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
                    if(customerDao.maxId() != -1) {
                        customerDao.add(new Customer(customerDao.maxId()+1, customerNameTxtFld.getText(), address.get().getId()));
                        stage.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Customer successfully added to the database.");
                    } else {
//                        LOGGER.log(Level.SEVERE, "FROM: CustomerCards.buildNewCustomerStage.addBtn \n REASON: Could not add customer to the database.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("There was an error with the database.");
                        alert.showAndWait();
                    }
                } catch (Exception ex) {
//                    LOGGER.log(Level.SEVERE, "------\n FROM: CustomerCards.buildNewCustomerStage.addBtn.catch \n " +
//                            "REASON: Could not add customer to the database. \n ERROR MESSAGE: " + ex.getMessage() + "-----");
                    ex.printStackTrace();
                }
            });
            Button cancelBtn = new Button("Cancel");
            cancelBtn.onMouseClickedProperty().set(e->stage.close());
            ButtonBar.setButtonData(addBtn, ButtonBar.ButtonData.OK_DONE);
            ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
            buttonBar.getButtons().addAll(addBtn, cancelBtn);
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
            stage.setScene(new Scene(gridPane));
            return stage;
        }

        private Stage buildEditCustomerStage(CustomerDetails customer) throws IOException {
            // INIT CUSTOMER, STAGE AND GRIDPANE
            Stage stage = new Stage();
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
            customerNameTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            customerNameTxtFld.setText(customer.getCustomerName());
            nameHBox.getChildren().addAll(customerNameLbl, customerNameTxtFld);
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
            cityComboBox.getSelectionModel().select(customer.getCity());
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
            countryComboBox.getSelectionModel().select(customer.getCountry());
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
                    if(stage.isShowing()) {
                        if(address.isPresent()){
                            // update current address
                            addressDao.update(new Address(address.get().getId(), addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityComboBox.getSelectionModel().getSelectedItem(), postalCodeTxtFld.getText(), phoneTxtFld.getText()));
                        } else {
                            // add new address to address table
                            // get maxId and increment by 1 for unique addressId (PK)
                            int newId = addressDao.maxId()+1;
                            addressDao.add(new Address(newId, addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityComboBox.getSelectionModel().getSelectedItem(), postalCodeTxtFld.getText(), phoneTxtFld.getText()));
                            // set address to new address
                            address = addressDao.getById(newId);
                        }
                        // update customer table
                        customerDao.update(new Customer(customer.getCustomerId(), customerNameTxtFld.getText(), address.get().getId()));

                        stage.close();

                        // Alert for success
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Customer successfully updated.");
                        alert.showAndWait();
                    } else {
                        // LOG failure
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("There was an error with the database.");
                        alert.showAndWait();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            Button cancelBtn = new Button("Cancel");
            cancelBtn.onMouseClickedProperty().set(e->stage.close());
            ButtonBar.setButtonData(updateBtn, ButtonBar.ButtonData.OK_DONE);
            ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
            buttonBar.getButtons().addAll(updateBtn, cancelBtn);
            // END BUTTONS

            gridPane.add(buttonBar, 1, 7);
            gridPane.setStyle(
                    "-fx-border-width: 3.5; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-color: GREY;" +
                    "-fx-padding: 25, 25, 25, 25;" +
                    "-fx-label-padding: 5;"
            );
            stage.setScene(new Scene(gridPane));
            return stage;
        }

        private void buildGridPane() {
            ColumnConstraints columnConstraints = new ColumnConstraints(200);
            gridPane.getColumnConstraints().add(columnConstraints);

            //row 1: BOLD customerName, Edit button
            hBox.getChildren().addAll(customerNameLbl, editBtn);
            gridPane.add(hBox, 0, 0);
            gridPane.add(phoneLbl, 0, 1);
            gridPane.add(addressLine1Lbl, 0, 2);

            if(addressLine2Lbl.getText().isEmpty()) {
                gridPane.add(cityLbl, 0, 3);
                gridPane.add(postalCodeLbl, 0, 4);
                gridPane.add(countryLbl, 0, 5);

            } else {
                gridPane.add(addressLine2Lbl, 0, 3);
                gridPane.add(cityLbl, 0, 4);
                gridPane.add(postalCodeLbl, 0, 5);
                gridPane.add(countryLbl, 0, 6);
            }


            gridPane.setStyle(
                    "-fx-border-width: 3.5; " +
                            "-fx-border-radius: 10; " +
                            "-fx-border-color: GREY;" +
                            "-fx-padding: 25, 25, 25, 25;" +
                            "-fx-label-padding: 5;"
            );
        }

        private GridPane getCustomerCard() {
            buildGridPane();
            return gridPane;
        }

        private Stage getNewCustomerStage() throws Exception {
            return buildNewCustomerStage();
        }

    public Stage getNewCustomerCardStage() throws Exception {
        CustomerCard customerCard = new CustomerCard();
        return customerCard.getNewCustomerStage();
    }

    public Stage getEditCustomerCardStage(CustomerDetails customer) throws Exception {
        CustomerCard customerCard = new CustomerCard();
        return customerCard.buildEditCustomerStage(customer);
    }
}
