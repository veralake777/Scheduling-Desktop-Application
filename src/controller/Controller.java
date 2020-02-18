package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.CalendarPane;

import java.util.Locale;

public class Controller {
    @FXML
    public void initialize() throws Exception {
        Stage calendarStage = new Stage();
        CalendarPane calendar = new CalendarPane();
        calendar.setLocale(Locale.getDefault());
        calendarStage.show();

        // populate labels for each cell

    }
}
