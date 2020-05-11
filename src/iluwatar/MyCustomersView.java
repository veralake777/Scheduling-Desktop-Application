package iluwatar;

import iluwatar.DbDao.DbCustomerDao;
import iluwatar.DbDao.DbCustomerDetailsDao;
import iluwatar.POJO.Customer;
import iluwatar.POJO.CustomerDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        // style
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
//        pageUp.setPrefSize(50, 50);
//        pageDown.setPrefSize(50, 50);

        gridPane.add(titleLbl, 0, 0);
        gridPane.add(row2HBox, 0, 1);
        gridPane.add(customerCardsGridPane, 0, 2);
//        gridPane.add(pageUp, 1, 0);
//        gridPane.add(pageDown, 1, 1);
//        gridPane.getChildren().addAll(titleLbl, row2HBox, customerCardsGridPane, pageUp, pageDown);
    }

    // customerCards gridPane
    GridPane customerCardsGridPane = new GridPane();
    private void buildCustomerCardGridPane() throws Exception {
        // style
        customerCardsGridPane.setHgap(15);
        customerCardsGridPane.setVgap(15);
        customerCardsGridPane.setPadding(new Insets(20, 0, 0, 10));
        Stream<Customer> customerStream = new DbCustomerDao(DBUtils.getMySQLDataSource()).getAll();
        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();
        customerStream.forEach(c -> {
            try {
                CustomerCard card = new CustomerCard(c.getId());
                if(col.get() == 0 || col.get() == 1) {
                    customerCardsGridPane.add(card.getCustomerCard(), col.get(), row.get());
                    //set col to 1
                    col.getAndIncrement();
                    System.out.println(col.get());
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


        public CustomerCard(int customerId) throws Exception {

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

        protected GridPane getCustomerCard() {
            buildGridPane();
            return gridPane;
        }
    }
}
