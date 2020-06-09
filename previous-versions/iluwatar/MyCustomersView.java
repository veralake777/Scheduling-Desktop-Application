package iluwatar;

import iluwatar.DbDao.DbCustomerDao;
import iluwatar.DbDao.DbCustomerDetailsDao;
import iluwatar.POJO.Customer;
import iluwatar.POJO.CustomerDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.Database.DBUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MyCustomersView {
    public MyCustomersView() throws Exception {
    }

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

    // main gridPane
    GridPane gridPane = new GridPane();
    javafx.scene.control.Label titleLbl = new javafx.scene.control.Label("My Customers");

    // row 2
    HBox row2HBox = new HBox(25);
    javafx.scene.control.Label searchLbl = new javafx.scene.control.Label("Search");
    javafx.scene.control.TextField searchTxt = new javafx.scene.control.TextField("By Name, Address, Phone");
    javafx.scene.control.Button newCustomerBtn = new javafx.scene.control.Button("New Customer");
//    javafx.scene.control.Button pageUp = new javafx.scene.control.Button("Page Up");
//    javafx.scene.control.Button pageDown = new javafx.scene.control.Button("Page Down");

    public GridPane getGridPane() throws Exception {
        buildGridPane();
        return gridPane;
    }
    private void buildGridPane() throws Exception {
        // style components
        gridPane.setPadding(new Insets(5, 0, 0, 25));

        titleLbl.setPadding(new Insets(15, 10, 10, 15));
        titleLbl.setAlignment(Pos.CENTER);
        titleLbl.setFont(Font.font("Roboto", FontWeight.BOLD, 35));

        searchLbl.setPadding(new Insets(15, 10, 10, 15));
        searchLbl.setAlignment(Pos.CENTER_LEFT);
        searchLbl.setFont(Font.font("Roboto", FontWeight.MEDIUM, 20));

        searchTxt.setPadding(new Insets(15, 10, 15, 15));
        searchTxt.setAlignment(Pos.CENTER_LEFT);
        searchTxt.setFont(Font.font("Roboto", FontWeight.MEDIUM, 20));

        newCustomerBtn.setPadding(new Insets(15, 10, 15, 15));
        newCustomerBtn.setAlignment(Pos.CENTER_LEFT);
        newCustomerBtn.setFont(Font.font("Roboto", FontWeight.MEDIUM, 20));
        newCustomerBtn.setStyle("-fx-border-radius: 5; -fx-border-width: 2.5; -fx-border-color: GREY; -fx-background-color: white;");

        // build customer cards grid pane
        // title
        RowConstraints row1 = new RowConstraints();
        // search and add new
        RowConstraints row2 = new RowConstraints();
        // customer cards
        RowConstraints row3 = new RowConstraints();
        //row4
        RowConstraints row4 = new RowConstraints();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(85);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(15);


        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);
        gridPane.getColumnConstraints().addAll(col1, col2);

        // build row2 hbox
        row2HBox.getChildren().addAll(searchLbl, searchTxt, newCustomerBtn);


        buildCustomerCardScrollPane();
        gridPane.add(titleLbl, 0, 0);
        gridPane.add(row2HBox, 0, 1);
        gridPane.add(customerCardsGridPane, 0, 2);
    }

    // customerCards gridPane
    GridPane customerCardsGridPane = new GridPane();
    private void buildCustomerCardScrollPane() throws Exception {
        // style customerCardGridPane - used in each card
        customerCardsGridPane.setHgap(15);
        customerCardsGridPane.setVgap(15);
        customerCardsGridPane.setPadding(new Insets(20, 0, 0, 10));

        // Get Customers
        Stream<Customer> customerStream = new DbCustomerDao(DBUtils.getMySQLDataSource()).getAll();

        // row & column counters
        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();

        // for each customer build a new customer card based on customerDetails view from database
        customerStream.forEach(c -> {
            try {
                // create new customer card
                CustomerCard card = new CustomerCard(c.getId());
                // build 2 x 2 grid of customer cards
                if(col.get() == 0 || col.get() == 1) {
                    customerCardsGridPane.add(card.getCustomerCard(), col.get(), row.get());
                    // set col to 1
                    col.getAndIncrement();
                } else {
                    customerCardsGridPane.add(card.getCustomerCard(), col.get(), row.get());
                    // set row to next row
                    row.getAndIncrement();
                    // set col back to 0
                    col.getAndSet(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(customerCardsGridPane);
    };

    private static class CustomerCard {
        GridPane gridPane = new GridPane();
        HBox hBox = new HBox(25);
        javafx.scene.control.Button editBtn = new javafx.scene.control.Button("Edit");
        // Labels for card view; textFields for onActionEditButton
        Label customerNameLbl = new Label();
        TextField customerNameTxtFld = new TextField();

        Label phoneLbl = new Label();
        TextField phoneTxtFld = new TextField();

        Label addressLine1Lbl = new Label();
        TextField addressLine1TxtFld = new TextField();

        Label addressLine2Lbl = new Label();
        TextField addressLine2TxtFld = new TextField();

        Label postalCodeLbl = new Label();
        TextField postalCodeTxtFld = new TextField();

        Label cityLbl = new Label();
        TextField cityTxtFld = new TextField();

        Label countryLbl = new Label();
        TextField countryTxtLbl = new TextField();

        // Dumb CustomerCard - used for new AddNewCustomer
        private CustomerCard() {
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
            cityTxtFld.setText("enter customer city");

            postalCodeLbl.setFont(labelFont);
            postalCodeLbl.setText("Postal Code");
            postalCodeTxtFld.setText("enter customer postal code");

            countryLbl.setFont(labelFont);
            countryLbl.setText("Country");
            countryTxtLbl.setText("enter customer country");
        }

        // CustomerCard Constructor by customerId - used for editCustomer button
        private CustomerCard(int customerId) throws Exception {
            DbCustomerDetailsDao dbCustomerDetailsDao = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource());
            Optional<CustomerDetails> customer = dbCustomerDetailsDao.getById(customerId);
            Font labelFont = Font.font("Roboto", FontWeight.NORMAL, 14);
            customer.ifPresent(c -> {
                customerNameLbl.setFont(labelFont);
                customerNameLbl.setText(c.getName());
                customerNameTxtFld.setText(c.getName());

                phoneLbl.setFont(labelFont);
                phoneLbl.setText(c.getPhone());
                phoneTxtFld.setText(c.getPhone());

                addressLine1Lbl.setFont(labelFont);
                addressLine1Lbl.setText(c.getAddress1());
                addressLine1TxtFld.setText(c.getAddress1());

                addressLine2Lbl.setFont(labelFont);
                addressLine2Lbl.setText(c.getAddress2());
                addressLine2TxtFld.setText(c.getAddress2());

                cityLbl.setFont(labelFont);
                cityLbl.setText(c.getCity());
                cityTxtFld.setText(c.getCity());

                postalCodeLbl.setFont(labelFont);
                postalCodeLbl.setText(c.getPostalCode());
                postalCodeTxtFld.setText(c.getPostalCode());

                countryLbl.setFont(labelFont);
                countryLbl.setText(c.getCountry());
                countryTxtLbl.setText(c.getCountry());
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
            cityTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            cityHBox.getChildren().addAll(cityLbl, cityTxtFld);
            gridPane.add(cityHBox, 0, 4);

            HBox postalCodeHBox = new HBox(50);
            postalCodeLbl.setMinSize(minWidth, 25);
            postalCodeTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            postalCodeHBox.getChildren().addAll(postalCodeLbl, postalCodeTxtFld);
            gridPane.add(postalCodeHBox, 0, 5);

            HBox countryHBox = new HBox(50);
            countryLbl.setMinSize(minWidth, 25);
            countryTxtLbl.setMinSize(columnConstraints1.getPrefWidth(), 25);
            countryHBox.getChildren().addAll(countryLbl, countryTxtLbl);
            gridPane.add(countryHBox, 0, 6);
            //END LABELS AND INPUTS

            // START BUTTONS
            HBox buttonsHBox = new HBox(20);
            Button addBtn = new Button("Add");
            addBtn.onMouseClickedProperty().set(e-> {
                try {
                    DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                    if(customerDao.maxId() != -1) {
                        customerDao.add(new CustomerDetails(customerDao.maxId()+1, customerNameTxtFld.getText(), phoneTxtFld.getText(), addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityTxtFld.getText(), postalCodeTxtFld.getText(), countryTxtLbl.getText()));
                        stage.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Customer successfully added to the database.");
                    } else {
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
            buttonsHBox.setAlignment(Pos.CENTER);
            buttonsHBox.setPadding(new Insets(10));
            buttonsHBox.getChildren().addAll(addBtn, cancelBtn);
            // END BUTTONS

            gridPane.add(buttonsHBox, 1, 7);
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
            customerNameTxtFld.setText(customer.getName());
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
            addressLine1TxtFld.setText(customer.getAddress1());
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
            cityTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            cityTxtFld.setText(customer.getCity());
            cityHBox.getChildren().addAll(cityLbl, cityTxtFld);
            gridPane.add(cityHBox, 0, 4);

            HBox postalCodeHBox = new HBox(50);
            postalCodeLbl.setMinSize(minWidth, 25);
            postalCodeTxtFld.setMinSize(columnConstraints1.getPrefWidth(), 25);
            postalCodeTxtFld.setText(customer.getPostalCode());
            postalCodeHBox.getChildren().addAll(postalCodeLbl, postalCodeTxtFld);
            gridPane.add(postalCodeHBox, 0, 5);

            HBox countryHBox = new HBox(50);
            countryLbl.setMinSize(minWidth, 25);
            countryTxtLbl.setMinSize(columnConstraints1.getPrefWidth(), 25);
            countryTxtLbl.setText(customer.getCountry());
            countryHBox.getChildren().addAll(countryLbl, countryTxtLbl);
            gridPane.add(countryHBox, 0, 6);
            //END LABELS AND INPUTS

            // START BUTTONS
            HBox buttonsHBox = new HBox(20);
            Button updateBtn = new Button("Update");
            updateBtn.onMouseClickedProperty().set(e-> {
                try {
                    DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                    if(customerDao.maxId() != -1) {
                        customerDao.update(new CustomerDetails(customerDao.maxId()+1, customerNameTxtFld.getText(), phoneTxtFld.getText(), addressLine1TxtFld.getText(), addressLine2TxtFld.getText(), cityTxtFld.getText(), postalCodeTxtFld.getText(), countryTxtLbl.getText()));
                        stage.close();
                        Main.mainMenu.updateCustomerMenu();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Customer successfully added to the database.");
                    } else {
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
            buttonsHBox.setAlignment(Pos.CENTER);
            buttonsHBox.setPadding(new Insets(10));
            buttonsHBox.getChildren().addAll(updateBtn, cancelBtn);
            // END BUTTONS

            gridPane.add(buttonsHBox, 1, 7);
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

        private Stage getNewCustomerScene() throws Exception {
            return buildNewCustomerStage();
        }
    }

    public Stage getNewCustomerCardScene() throws Exception {
        CustomerCard customerCard = new CustomerCard();
        return customerCard.getNewCustomerScene();
    }

    public Stage getEditCustomerCardStage(CustomerDetails customer) throws IOException {
        CustomerCard customerCard = new CustomerCard();
        return customerCard.buildEditCustomerStage(customer);
    }

}
