package iluwatar;

import iluwatar.DbDao.DbCustomerDao;
import iluwatar.DbDao.DbCustomerDetailsDao;
import iluwatar.POJO.Customer;
import iluwatar.POJO.CustomerDetails;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import utils.Database.DBUtils;

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

    private class CustomerCard {
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


        public CustomerCard(int customerId) throws Exception {

            DbCustomerDetailsDao dbCustomerDetailsDao = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource());
            Optional<CustomerDetails> customer = dbCustomerDetailsDao.getById(customerId);
            customer.ifPresent(c -> {
                customerNameLbl.setText(c.getName());
                customerNameTxtFld.setText(c.getName());

                phoneLbl.setText(c.getPhone());
                phoneTxtFld.setText(c.getPhone());

                addressLine1Lbl.setText(c.getAddress1());
                addressLine1TxtFld.setText(c.getAddress1());

                addressLine2Lbl.setText(c.getAddress2());
                addressLine2TxtFld.setText(c.getAddress2());

                cityLbl.setText(c.getCity());
                cityTxtFld.setText(c.getCity());

                postalCodeLbl.setText(c.getPostalCode());
                postalCodeTxtFld.setText(c.getPostalCode());

                countryLbl.setText(c.getCountry());
                countryTxtLbl.setText(c.getCountry());
            });
        }

        private void buildGridPane() {
//            // set row constraints
//            int rows = 6;
//
//            for(int i=0; i < rows; i++) {
//                RowConstraints row = new RowConstraints(100);
//                gridPane.getRowConstraints().add(row);
//            }
//
//            // set column constraint
//            ColumnConstraints columnConstraints = new ColumnConstraints(100);
//            gridPane.getColumnConstraints().add(columnConstraints);

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

        protected GridPane getCustomerCard() {
            buildGridPane();
            return gridPane;
        }
    }

    // main gridPane
    GridPane gridPane = new GridPane();
    javafx.scene.control.Label titleLbl = new javafx.scene.control.Label("My Customers");

    // row 2
    HBox row2HBox = new HBox(25);
    javafx.scene.control.Label searchLbl = new javafx.scene.control.Label("Search");
    javafx.scene.control.TextField searchTxt = new javafx.scene.control.TextField("By Name, Address, Phone");
    javafx.scene.control.Button newCustomerBtn = new javafx.scene.control.Button("New Customer");
    javafx.scene.control.Button pageUp = new javafx.scene.control.Button("Page Up");
    javafx.scene.control.Button pageDown = new javafx.scene.control.Button("Page Down");

    public GridPane getGridPane() throws Exception {
        buildGridPane();
        return gridPane;
    }
    private void buildGridPane() throws Exception {
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

        // build customer cards grid pane
        buildCustomerCardGridPane();

        // button styling
        pageUp.setPrefSize(50, 50);
        pageDown.setPrefSize(50, 50);

        gridPane.add(titleLbl, 0, 0);
        gridPane.add(row2HBox, 0, 1);
        gridPane.add(customerCardsGridPane, 0, 2);
        gridPane.add(pageUp, 1, 0);
        gridPane.add(pageDown, 1, 1);
//        gridPane.getChildren().addAll(titleLbl, row2HBox, customerCardsGridPane, pageUp, pageDown);
    }

    // customerCards gridPane
    GridPane customerCardsGridPane = new GridPane();
    private void buildCustomerCardGridPane() throws Exception {
        Stream<Customer> customerStream = new DbCustomerDao(DBUtils.getMySQLDataSource()).getAll();
        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();
        customerStream.forEach(c -> {
            try {
                CustomerCard card = new CustomerCard(c.getId());
                if(col.get() == 0) {
                    customerCardsGridPane.add(card.getCustomerCard(), row.get(), col.get());
                    //set col to 1
                    col.getAndIncrement();
                } else {
                    customerCardsGridPane.add(card.getCustomerCard(), row.get(), col.get());
                    // set row to next row
                    row.getAndIncrement();
                    // set col back to 0
                    col.getAndDecrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(customerCardsGridPane);
    };
}
