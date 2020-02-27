package MVC.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class OverviewController {
    // top level container
    @FXML
    public GridPane overview;

    // calendar view on the
    @FXML
    public BorderPane calendarPane;
    @FXML
    public Label monthAndYearLbl;
    @FXML
    public Button prevBtn;
    @FXML
    public Button nextBtn;

    @FXML
    public TableView weeklyView;
    @FXML
    public TableColumn weekDayCol1;
    @FXML
    public TableColumn weekDayCol2;
    @FXML
    public TableColumn weekDayCol3;
    @FXML
    public TableColumn weekDayCol4;
    @FXML
    public TableColumn weekDayCol5;
    @FXML
    public TableColumn weekDayCol6;
    @FXML
    public TableColumn weekDayCol7;
    @FXML
    public BorderPane calendarMonthView;


    public OverviewController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        setOverview();
    }


    public GridPane getOverview() {
        return overview;
    }

    public void setOverview() throws IOException {
        monthAndYearLbl.setText("MONTH YEAR");
    }

    public void onActionGetPrevMonth(ActionEvent actionEvent) {
    }

    public void onActionGetNextMonth(ActionEvent actionEvent) {
    }
}
