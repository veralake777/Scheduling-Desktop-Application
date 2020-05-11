package iluwatar;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddCustomer {
    private GridPane gridPane = new GridPane();

    /**
     * Popup: Title "Add Customer", Size 500 x 700
     * CustomerName: Label, TextField
     * Address Line 1: Label, TextField
     * Address Line 2: Label, TextField
     * City: Label, ComboBox
     * Postal Code: Label, TextField
     * Country: Label, ComboBox
     * Phone Number:  Label, TextField
     * Active: HBox(Radio Yes, Radio No)
     *
     * Button: HBox(Save, Cancel)
     */
    ComboBoxes cb = new ComboBoxes();
    private Label customerNameLbl = new Label("Customer Name");
    private TextField customerNameTxt = new TextField("customer name");
    private Label address1Lbl = new Label("Address Line 1");
    private TextField address1Txt = new TextField("address line 1");
    private Label address2Lbl = new Label("Address Line 2");
    private TextField address2Txt = new TextField("address line 2");
    private Label cityLbl = new Label("City");
    private ComboBox<String> cityComboBox = cb.getCities();
    private Label postalCodeLbl = new Label("Postal Code");
    private TextField postalCodeTxt = new TextField("postal code");
    private Label countryLbl = new Label("Country");
    private ComboBox<String> countryComboBox = cb.getCountries();
    private Label phoneNumberLbl = new Label("Phone Number");
    private TextField phoneNumberTxt = new TextField("phone number");
    private Label activeLbl = new Label("Active?");
    private RadioButton activeYesBtn = new RadioButton("Yes");
    private RadioButton activeNoBtn = new RadioButton("No");
    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");

    public AddCustomer() throws Exception {
    }

    // Set one constraint at a time
    private void buildGridPane() {
        //customerName
        GridPane.setRowIndex(customerNameLbl, 0);
        GridPane.setColumnIndex(customerNameLbl, 0);
        GridPane.setRowIndex(customerNameTxt, 0);
        GridPane.setColumnIndex(customerNameTxt, 1);

        //address1
        GridPane.setRowIndex(address1Lbl, 1);
        GridPane.setColumnIndex(address1Lbl, 0);
        GridPane.setRowIndex(address1Txt, 1);
        GridPane.setColumnIndex(address1Txt, 1);

        //address2
        GridPane.setRowIndex(address2Lbl, 2);
        GridPane.setColumnIndex(address2Lbl, 0);
        GridPane.setRowIndex(address2Txt, 2);
        GridPane.setColumnIndex(address2Txt, 1);

        //city
        GridPane.setRowIndex(cityLbl, 3);
        GridPane.setColumnIndex(cityLbl, 0);
        GridPane.setRowIndex(cityComboBox, 3);
        GridPane.setColumnIndex(cityComboBox, 1);

        //postal code
        GridPane.setRowIndex(postalCodeLbl, 4);
        GridPane.setColumnIndex(postalCodeTxt, 1);
        GridPane.setRowIndex(postalCodeTxt, 4);
        GridPane.setColumnIndex(postalCodeTxt, 1);

        //country
        GridPane.setRowIndex(countryLbl, 5);
        GridPane.setColumnIndex(countryLbl, 0);
        GridPane.setRowIndex(countryComboBox, 5);
        GridPane.setColumnIndex(countryComboBox, 1);

        //phone
        GridPane.setRowIndex(phoneNumberLbl, 6);
        GridPane.setColumnIndex(phoneNumberLbl, 0);
        GridPane.setRowIndex(phoneNumberTxt, 6);
        GridPane.setColumnIndex(phoneNumberTxt, 1);

        //active
        GridPane.setRowIndex(activeLbl, 7);
        GridPane.setColumnIndex(activeLbl, 0);

        // hbox for radio buttons
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(activeYesBtn, activeNoBtn);
        GridPane.setRowIndex(hBox, 7);
        GridPane.setColumnIndex(hBox, 1);

        // buttons
        HBox hBox2 = new HBox(10);
        hBox2.getChildren().addAll(saveBtn, cancelBtn);
        GridPane.setRowIndex(hBox2, 8);
        GridPane.setColumnIndex(hBox2, 1);

        gridPane.getChildren().addAll(customerNameLbl, customerNameTxt, address1Lbl, address1Txt, address2Lbl,
                address2Txt, cityLbl, cityComboBox, postalCodeLbl, postalCodeTxt, countryLbl, countryComboBox,
                phoneNumberLbl, phoneNumberTxt, activeLbl, hBox, hBox2);

        gridPane.setPrefSize(500, 500);
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);
    }

    public GridPane getAddCustomerGridPane() {
        buildGridPane();
        return gridPane;
    }
}
