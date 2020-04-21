package PresentationState.Appointment.AddAppointment;


//import org.svenehrke.javafxdemos.PresentationState.infra.JavaFxWidgetBindings;

import PresentationState.infra.JavaFxWidgetBindings;

public class AddAppointmentGUIBinder {

	private final AddAppointmentController controller;
	private final AddAppointmentPresentationState presentationState;

	public AddAppointmentGUIBinder(AddAppointmentController controller, AddAppointmentPresentationState presentationState) {
		this.controller = controller;
		this.presentationState = presentationState;
	}

	public void bindAndInitialize() throws Exception {
		presentationState.initBinding();
		initWidgetBinding();
		initActionHandlers();
		presentationState.initData(1);
	}

	private void initWidgetBinding() {
		JavaFxWidgetBindings.bindTextField(controller.userIdTxt, presentationState.userId);
		JavaFxWidgetBindings.bindTextField(controller.titleTxt, presentationState.title);
		JavaFxWidgetBindings.bindTextField(controller.descriptionTxt, presentationState.description);
		JavaFxWidgetBindings.bindTextField(controller.locationTxt, presentationState.location);
		JavaFxWidgetBindings.bindTextField(controller.contactTxt, presentationState.customerContact);
		JavaFxWidgetBindings.bindTextField(controller.typeTxt, presentationState.type);
		JavaFxWidgetBindings.bindTextField(controller.urlTxt, presentationState.url);
		JavaFxWidgetBindings.bindDatePicker(controller.startDatePicker, presentationState.startDate);
		JavaFxWidgetBindings.bindTextField(controller.startTimeTxt, presentationState.startTime);
		JavaFxWidgetBindings.bindDatePicker(controller.endDatePicker, presentationState.endDate);
		JavaFxWidgetBindings.bindTextField(controller.endTimeTxt, presentationState.endTime);
		JavaFxWidgetBindings.bindListToComboBox(controller.customerComboBox, presentationState.customerNames);
	}

	private void initActionHandlers() {
		// BUTTONS
//		JavaFxWidgetBindings.bindButton(controller.addButton, AddAppointmentActionHandlers.addButtonHandler(presentationState));
		JavaFxWidgetBindings.bindButton(controller.cancelButton, AddAppointmentActionHandlers.exitProgram(controller));

		// COMBO BOXES
		JavaFxWidgetBindings.bindComboBox(controller.customerComboBox, AddAppointmentActionHandlers.onCustomerComboBoxChange(presentationState, controller));
	}
}
