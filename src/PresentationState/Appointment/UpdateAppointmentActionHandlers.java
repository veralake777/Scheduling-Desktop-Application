package PresentationState.Appointment;

import javafx.beans.InvalidationListener;

public class UpdateAppointmentActionHandlers {
	public static InvalidationListener updateButtonHandler(UpdateAppointmentPresentationState ps) {
		// update view and save to database
		return observable -> ps.id.setValue("Hello " + ps.id.getValue());
	}
}
