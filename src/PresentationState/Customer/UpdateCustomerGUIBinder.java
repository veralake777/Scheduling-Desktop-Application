package PresentationState.Customer;


//import org.svenehrke.javafxdemos.PresentationState.infra.JavaFxWidgetBindings;

import PresentationState.infra.JavaFxWidgetBindings;

import java.sql.SQLException;
import java.text.ParseException;

public class UpdateCustomerGUIBinder {

	private final UpdateCustomerController controller;
	private final UpdateCustomerPresentationState presentationState;

	public UpdateCustomerGUIBinder(UpdateCustomerController controller, UpdateCustomerPresentationState updateCustomerPresentationState) {
		this.controller = controller;
		this.presentationState = updateCustomerPresentationState;
	}

	void bindAndInitialize() throws ParseException, SQLException, ClassNotFoundException {
		presentationState.initBinding();
		initWidgetBinding();
		initActionHandlers();
		presentationState.initData(1);
	}

	private void initWidgetBinding() {

		JavaFxWidgetBindings.bindTextField(controller.idTxt, presentationState.id);
		JavaFxWidgetBindings.bindTextField(controller.customerNameTxt, presentationState.name);
		JavaFxWidgetBindings.bindTextField(controller.phoneTxt, presentationState.phone);
		JavaFxWidgetBindings.bindTextField(controller.addressLine1Txt, presentationState.address);
		JavaFxWidgetBindings.bindTextField(controller.addressLine2Txt, presentationState.address2);
		JavaFxWidgetBindings.bindTextField(controller.postalCodeTxt, presentationState.postalCode);
		JavaFxWidgetBindings.bindTextField(controller.cityTxt, presentationState.city);
		JavaFxWidgetBindings.bindTextField(controller.countryTxt, presentationState.country);
	}

	private void initActionHandlers() {
		JavaFxWidgetBindings.bindButton(controller.updateButton, UpdateCustomerActionHandlers.updateCustomerHandler(presentationState));
	}
}
