package PresentationState.Customer.AddCustomer;

import PresentationState.Customer.JavaFxApplications;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import static PresentationState.Customer.AddCustomer.AddCustomerApplicationConfig.*;


public class RunAddCustomer extends Application {

	private final Logger logger = Logger.getLogger(RunAddCustomer.class.getName());

	public static void main(String[] args) {
		Application.launch(RunAddCustomer.class, args);
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
			new AddCustomerGUIBinder(loader.getController(), new AddCustomerPresentationState()).bindAndInitialize();

			Scene scene = new Scene(loader.getRoot());
			scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(loader.getResources().getString("customerTitle"));
			primaryStage.show();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

}
