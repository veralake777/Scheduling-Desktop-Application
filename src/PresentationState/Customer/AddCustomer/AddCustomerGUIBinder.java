package PresentationState.Customer.AddCustomer;


//import org.svenehrke.javafxdemos.PresentationState.infra.JavaFxWidgetBindings;

import PresentationState.infra.JavaFxWidgetBindings;

import java.sql.SQLException;
import java.text.ParseException;

public class AddCustomerGUIBinder {

	private final AddCustomerController controller;
	private final AddCustomerPresentationState presentationState;

	public AddCustomerGUIBinder(AddCustomerController controller, AddCustomerPresentationState addCustomerPresentationState) {
		this.controller = controller;
		this.presentationState = addCustomerPresentationState;
	}

	void bindAndInitialize() throws ParseException, SQLException, ClassNotFoundException {
		presentationState.initBinding();
		initWidgetBinding();
		initActionHandlers();
		presentationState.initData();
	}

	private void initWidgetBinding() throws ParseException, SQLException, ClassNotFoundException {
		JavaFxWidgetBindings.bindTextField(controller.idTxt, presentationState.id);
		JavaFxWidgetBindings.bindTextField(controller.customerNameTxt, presentationState.name);
		JavaFxWidgetBindings.bindTextField(controller.phoneTxt, presentationState.phone);
		JavaFxWidgetBindings.bindTextField(controller.addressLine1Txt, presentationState.address);
		JavaFxWidgetBindings.bindTextField(controller.addressLine2Txt, presentationState.address2);
		JavaFxWidgetBindings.bindTextField(controller.postalCodeTxt, presentationState.postalCode);
		JavaFxWidgetBindings.populateCityComboBox(controller.cityComboBox, presentationState.cityTypeList);
		JavaFxWidgetBindings.populateCountryComboBox(controller.countryComboBox, presentationState.countryTypeList);
	}

	private void initActionHandlers() {
		JavaFxWidgetBindings.bindButton(controller.addCustomer, AddCustomerActionHandlers.onActionAddCustomer(presentationState));
	}
}
