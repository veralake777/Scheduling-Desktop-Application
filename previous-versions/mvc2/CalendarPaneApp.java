package mvc2;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Database.DBUtils;

public class CalendarPaneApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) throws ClassNotFoundException {
        // connect to Database
        try {
            DBUtils.startConnection();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // launch app
        launch(args);

        // close Database connection
        DBUtils.closeConnection();
    }
}
