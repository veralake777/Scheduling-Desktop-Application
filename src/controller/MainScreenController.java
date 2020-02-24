package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import utils.Internationalization;

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

    private void setCalendarTab() throws ParseException, SQLException, ClassNotFoundException, IOException {
        Tab calendar = new Tab();
        calendar.setText("Calendar");
        calendar.setContent(FXMLLoader.load(this.getClass().getResource("/view/CalendarPane.fxml")));
        mainTabPane.getTabs().add(calendar);
    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void initialize() throws Exception {
        Internationalization.setCurrentLocale();
        Internationalization.getCurrentLocale();
        setCalendarTab();
    }

    public void onActionCalender(ActionEvent actionEvent) {
//        Stage calendarStage = new Stage();
//
//        CalendarPane calendar = new CalendarPane();
//        calendar.setLocale(Locale.getDefault());
//        calendarStage.show();

        // populate labels for each cell

    }
}
