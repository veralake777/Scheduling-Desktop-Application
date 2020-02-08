package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        Font.loadFont(getClass().getResourceAsStream("../CSS/Fonts/LeagueSpartan-Bold.otf"), 35);


        Parent root = FXMLLoader.load(getClass().getResource("../View/loginScreen.fxml"));
        primaryStage.setTitle("ACMECo Scheduling System");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}