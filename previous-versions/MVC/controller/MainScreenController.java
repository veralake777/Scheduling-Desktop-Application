package MVC.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class MainScreenController {
    // Stage & Scene
    Stage stage;
    Parent scene;

    // Event Handlers

    public TabPane mainTabPane;

    public MainScreenController() throws Exception {
    }

    private void setTabs() throws ParseException, SQLException, ClassNotFoundException, IOException {
        //overview view
        Tab overview = new Tab("Overview");
        overview.setContent(FXMLLoader.load(this.getClass().getResource("/previous-versions/MVC/view/weekView.fxml")));

        //calendar view
        Tab calendar = new Tab("Calendar");
        calendar.setContent(FXMLLoader.load(this.getClass().getResource("/previous-versions/MVC/view/calendarMonthView.fxml")));

        // Weekly View on My Week Tab
        Tab weekly = new Tab("My Week");
        weekly.setContent(FXMLLoader.load(this.getClass().getResource("/previous-versions/MVC/view/weeklyView.fxml")));

        // Customer View on Customers Tab
        Tab customers = new Tab("Customers");
        customers.setContent(FXMLLoader.load(this.getClass().getResource("/previous-versions/MVC/view/customerView.fxml")));

//        mainTabPane.getTabs().addAll(overview, calendar, weekly, customers);
        mainTabPane.getTabs().setAll(overview, calendar, customers);
    }

//    @FXML
//    private void onActionExit(ActionEvent actionEvent) {
//        System.exit(0);
//    }

    @FXML
    public void initialize() throws Exception {
        setTabs();
    }
}
