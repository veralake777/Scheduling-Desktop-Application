package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.CalendarPane;

public class Controller {
    @FXML
    public void initialize() throws Exception {
        Stage calendarStage = new Stage();
        CalendarPane calendar = new CalendarPane();
        calendarStage.show();

        // populate labels for each cell

    }
}
