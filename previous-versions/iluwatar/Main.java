package iluwatar;

import MVC.NextAppointment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Database.DBUtils;

import java.awt.*;

public class Main extends Application {
    private static GridPane gridPane;
    public static MainMenu mainMenu;

    static {
        try {
            mainMenu = new MainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WeekView weekView = new WeekView();

        gridPane = new GridPane();
        gridPane.addColumn(0);
        gridPane.addColumn(1);

        ColumnConstraints columnConstraintsMainMenu = new ColumnConstraints();
        columnConstraintsMainMenu.setPercentWidth(10);

        ColumnConstraints columnConstraintsDynamicView = new ColumnConstraints();
        columnConstraintsDynamicView.setPercentWidth(90);


        gridPane.getColumnConstraints().addAll(columnConstraintsMainMenu, columnConstraintsDynamicView);

//        WeekView weekView = new WeekView();

        // main menu
        gridPane.add(mainMenu.mainMenu, 0, 0, gridPane.getColumnCount(), 1);

        // dynamic view

        // onLoad calendar + nextAppointment + week
        GridPane onLoad = new GridPane();
        ColumnConstraints columnConstraintsCalendar = new ColumnConstraints();
        columnConstraintsCalendar.setPercentWidth(20);
        ColumnConstraints columnConstraintsWeek = new ColumnConstraints();
        columnConstraintsWeek.setPercentWidth(80);
//        RowConstraints rowConstraints = new RowConstraints(gridPane.getHeight());

        BorderPane calendar = FXMLLoader.load(getClass().getResource("previous-versions/MVC/view/calendarMonthView.fxml"));
        GridPane weekColumn = weekView.getWeekView();

        VBox monthColumn = new VBox(10);
        monthColumn.setFillWidth(true);
        monthColumn.getChildren().addAll(calendar, new NextAppointment().getNextAppointmentVBox());

        onLoad.getColumnConstraints().addAll(columnConstraintsCalendar, columnConstraintsWeek);
        onLoad.addColumn(0, monthColumn);
        onLoad.addColumn(1, weekColumn);
//        calendar.setTranslateY(-680);
//        onLoad.getChildren().get(0).setStyle("-fx-alignment: CENTER_TOP;");

        gridPane.add(onLoad, 0, 1);

        ScrollPane scroller = new ScrollPane(gridPane);
        scroller.setFitToWidth(true);
        Scene scene = new Scene(scroller);
//        scene.getStylesheets().add(getClass().getResource("calendar-view.css").toExternalForm());
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadDynamicView(Node view) {
        gridPane.getChildren().remove(1);
        gridPane.add(view, 1, 1);
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
