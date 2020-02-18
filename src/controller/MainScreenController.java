package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import utils.Internationalization;
import view.CalendarPane2;

import java.sql.SQLException;
import java.text.ParseException;

public class MainScreenController {

    public TabPane mainTabPane;

    public MainScreenController() throws Exception {
    }

    private void setCalendarTab() throws ParseException, SQLException, ClassNotFoundException {
        Tab calendar = new Tab();
        calendar.setText("Calendar");
        calendar.setContent(new CalendarPane2());
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
