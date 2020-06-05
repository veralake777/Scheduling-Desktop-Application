package mvc2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CalendarPaneController {
    @FXML
    public GridPane calendarPane;
    public DatePicker datePicker;

    private CalendarPane model;
    private FXMLLoader view;

    public CalendarPaneController(CalendarPane model, FXMLLoader view) {
        this.model = model;
        this.view = view;
    }

    public GridPane getCalendarPane() {
        return calendarPane;
    }

    public void setCalendarPane(GridPane calendarPane) {
        this.calendarPane = calendarPane;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public void updateView() throws IOException {
        datePicker.setShowWeekNumbers(true);
        view.load();
    }
}
