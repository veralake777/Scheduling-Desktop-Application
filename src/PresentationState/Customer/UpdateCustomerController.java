package PresentationState.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class UpdateCustomerController {
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
	public TextField cityTxt;
	@FXML
	public TextField postalCodeTxt;
	@FXML
	public Button updateButton;
	@FXML
	public Button cancelButton;
	@FXML
	public TextField urlTxt1;
	@FXML
	public Label header;
	@FXML
	public TextField countryTxt;
	@FXML
	public Button newCustomer;

	public void onActionUpdate(ActionEvent actionEvent) {
		// See UpdateCustomerActionHandlers
	}

	public void onActionExit(ActionEvent actionEvent) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void onActionNewCustomer(ActionEvent actionEvent) {
	}
}
