package iluwatar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.Database.DBUtils;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Font.loadFont(getClass().getResourceAsStream("../CSS/Fonts/LeagueSpartan-Bold.otf"), 35);
//        Parent root = FXMLLoader.load(getClass().getResource(""));
//        root.getStylesheets().addAll("/CSS/calendarPane.css", "/CSS/loginStyle.css", "/resources/app.css");
        GridPane gridPane = new GridPane();
        MainMenu mainMenu = new MainMenu();
        WeekView weekView = new WeekView();

        gridPane.add(mainMenu.vBox, 0, 0);
        gridPane.add(weekView.getWeekView(), 1, 0);

        ScrollPane scroller = new ScrollPane(gridPane);
        scroller.setMaxHeight(500);
        scroller.setFitToWidth(true);
        Scene scene = new Scene(scroller);
//        scene.getStylesheets().add(getClass().getResource("calendar-view.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
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
