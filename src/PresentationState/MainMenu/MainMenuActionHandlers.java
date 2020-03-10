package PresentationState.MainMenu;

import javafx.beans.InvalidationListener;

public class MainMenuActionHandlers {
    public static InvalidationListener onClickLoadAppointmentsView(MainMenuPresentationState ps) {
		// TODO change presentation state to mainGridPane
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());
    }

	public static InvalidationListener onClickLoadCustomersView(MainMenuPresentationState ps) {
		// TODO change presentation state to mainGridPane
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());
	}

	public static InvalidationListener onClickLoadConsultantsView(MainMenuPresentationState ps) {
		// TODO change presentation state to mainGridPane
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());
	}

	public static InvalidationListener onClickLoadMonthlyView(MainMenuPresentationState ps) {
		// TODO change presentation state to mainGridPane
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());

	}

	public static InvalidationListener onClickLoadWeeklyView(MainMenuPresentationState ps) {
		// TODO change presentation state to mainGridPane
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());

	}

	public static InvalidationListener onClickLoadReportsView(MainMenuPresentationState ps) {
		// TODO change presentationState to reports view
		return observable -> ps.nextAppointmentHeader.setValue("Hello " + ps.nextAppointmentHeader.getValue());

	}
}
