package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.CalendarPane;
import utils.Internationalization;

public class MainScreenController {

    public TabPane mainTabPane;
    public BorderPane testBorderPane;
    public GridPane mainGridPane;

    private void createCalendar() {

    }

    public MainScreenController() throws Exception {
    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void initialize() throws Exception {
        Internationalization.setCurrentLocale();
        Internationalization.getCurrentLocale();

        Tab calendar = new Tab();
        calendar.setText("Calendar");
        calendar.setContent(new CalendarPane());
        mainTabPane.getTabs().add(calendar);
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
