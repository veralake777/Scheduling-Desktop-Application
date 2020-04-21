package PresentationState.Appointment.UpdateAppointment;


//import org.svenehrke.javafxdemos.PresentationState.infra.JavaFxWidgetBindings;

import PresentationState.infra.JavaFxWidgetBindings;

import java.sql.SQLException;
import java.text.ParseException;

public class UpdateAppointmentGUIBinder {

	private final UpdateAppointmentController controller;
	private final UpdateAppointmentPresentationState presentationState;

	public UpdateAppointmentGUIBinder(UpdateAppointmentController controller, UpdateAppointmentPresentationState presentationState) {
		this.controller = controller;
		this.presentationState = presentationState;
	}

	public void bindAndInitialize() throws Exception {
		presentationState.initBinding();
		initWidgetBinding();
		initActionHandlers();
		presentationState.initData(1);
	}

	private void initWidgetBinding() throws ParseException, SQLException, ClassNotFoundException {
//		JavaFxWidgetBindings.bindTextField(controller.idTxt, presentationState.id);
//		JavaFxWidgetBindings.bindTextField(controller.customerTxt, presentationState.customer);
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
		JavaFxWidgetBindings.bindListToComboBox(controller.idComboBox, presentationState.ids);
		JavaFxWidgetBindings.bindListToComboBox(controller.customerComboBox, presentationState.customerNames);

//		JavaFxWidgetBindings.bindLabel(controller.greetingLabel, presentationState.greeting);
	}

	private void initActionHandlers() {
		// BUTTONS
		JavaFxWidgetBindings.bindButton(controller.updateButton, UpdateAppointmentActionHandlers.updateButtonHandler(presentationState));
		JavaFxWidgetBindings.bindButton(controller.cancelButton, UpdateAppointmentActionHandlers.exitProgram(controller));

		// COMBO BOXES
		JavaFxWidgetBindings.bindComboBox(controller.idComboBox, UpdateAppointmentActionHandlers.onIdComboBoxChange(presentationState, controller));
		JavaFxWidgetBindings.bindComboBox(controller.customerComboBox, UpdateAppointmentActionHandlers.onCustomerComboBoxChange(presentationState, controller));

	}
}
