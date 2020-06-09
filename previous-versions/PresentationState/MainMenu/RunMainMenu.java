package PresentationState.MainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import static PresentationState.MainMenu.MainMenuConfig.*;

//import static org.svenehrke.javafxdemos.greetfxml.ApplicationConfig.*;

public class RunMainMenu extends Application {

	private final Logger logger = Logger.getLogger(RunMainMenu.class.getName());

	public static void main(String[] args) {
		Application.launch(RunMainMenu.class, args);
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
			new MainMenuGUIBinder(loader.getController(), new MainMenuPresentationState()).bindAndInitialize();

			Scene scene = new Scene(loader.getRoot());
			scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(loader.getResources().getString("mainMenu"));
			primaryStage.show();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

}
