package PresentationState.Appointment.UpdateAppointment;

import PresentationState.Appointment.JavaFxApplications;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

//import static org.svenehrke.javafxdemos.greetfxml.ApplicationConfig.*;
import static PresentationState.Appointment.UpdateAppointment.UpdateAppointmentConfig.*;

public class RunUpdateAppointment extends Application {

	private final Logger logger = Logger.getLogger(RunUpdateAppointment.class.getName());

	public static void main(String[] args) {
		Application.launch(RunUpdateAppointment.class, args);
	}

	@Override
	public void init() throws Exception {
	}

	@Override
	public void stop() throws Exception {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(JavaFxApplications.fxmlUrl(FXML_URL), JavaFxApplications.resources(RESOURCE_BUNDLE_NAME));
			loader.load();
			new UpdateAppointmentGUIBinder(loader.getController(), new UpdateAppointmentPresentationState()).bindAndInitialize();

			Scene scene = new Scene(loader.getRoot());
			scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(loader.getResources().getString("appointmentTitle"));
			primaryStage.show();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

}
