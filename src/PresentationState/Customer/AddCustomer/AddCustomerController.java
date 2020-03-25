package PresentationState.Customer.AddCustomer;

import DAO.POJO.City;
import DAO.POJO.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController {
	@FXML
	public TextField userIdTxt;
	@FXML
	public TextField idTxt;
	@FXML
	public TextField customerNameTxt;
	@FXML
	public TextField addressLine1Txt;
	@FXML
	public TextField addressLine2Txt;
	@FXML
	public TextField phoneTxt;
	@FXML
	public TextField postalCodeTxt;
	@FXML
	public Button cancelButton;
	@FXML
	public TextField urlTxt1;
	@FXML
	public Label header;
	@FXML
	public Button addCustomer;
	@FXML
    public ComboBox<City> cityComboBox;
	@FXML
	public ComboBox<Country> countryComboBox;

	public void onActionExit(ActionEvent actionEvent) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void onActionNewCustomer(ActionEvent actionEvent) {
	}
}
