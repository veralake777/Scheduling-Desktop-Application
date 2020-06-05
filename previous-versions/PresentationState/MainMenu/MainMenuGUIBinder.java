package PresentationState.MainMenu;


//import org.svenehrke.javafxdemos.PresentationState.infra.JavaFxWidgetBindings;

import PresentationState.infra.JavaFxWidgetBindings;

import java.sql.SQLException;
import java.text.ParseException;

public class MainMenuGUIBinder {

	private final MainMenuController controller;
	private final MainMenuPresentationState presentationState;

	public MainMenuGUIBinder(MainMenuController controller, MainMenuPresentationState presentationState) {
		this.controller = controller;
		this.presentationState = presentationState;
	}

	void bindAndInitialize() throws ParseException, SQLException, ClassNotFoundException {
		presentationState.initBinding();
		initWidgetBinding();
		initActionHandlers();
		presentationState.initData(1);
	}

	private void initWidgetBinding() {
		// Next Appointment VBox
		JavaFxWidgetBindings.bindLabel(controller.nextAppointmentHeader, presentationState.nextAppointmentHeader);
		JavaFxWidgetBindings.bindLabel(controller.dateLabel, presentationState.date);
		JavaFxWidgetBindings.bindLabel(controller.timeLabel, presentationState.time);
		JavaFxWidgetBindings.bindLabel(controller.customerNameLabel, presentationState.customerName);
		JavaFxWidgetBindings.bindLabel(controller.customerPhoneLabel, presentationState.customerPhone);

	}

	private void initActionHandlers() {
		// TODO change presentation state to mainGridPane
		JavaFxWidgetBindings.bindButton(controller.appointmentsBtn, MainMenuActionHandlers.onClickLoadAppointmentsView(presentationState));
		JavaFxWidgetBindings.bindButton(controller.customersBtn, MainMenuActionHandlers.onClickLoadCustomersView(presentationState));
		JavaFxWidgetBindings.bindButton(controller.consultantsBtn, MainMenuActionHandlers.onClickLoadConsultantsView(presentationState));
		JavaFxWidgetBindings.bindButton(controller.myMonthBtn, MainMenuActionHandlers.onClickLoadMonthlyView(presentationState));
		JavaFxWidgetBindings.bindButton(controller.myWeekBtn, MainMenuActionHandlers.onClickLoadWeeklyView(presentationState));
		JavaFxWidgetBindings.bindButton(controller.reportsBtn, MainMenuActionHandlers.onClickLoadReportsView(presentationState));
	}
}
