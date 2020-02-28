package MVC.controller;

import DAO.AppointmentDao;
import DAO.POJO.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

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
    public GridPane weeklyView;
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
    public ButtonBar prevNextBtnBar;
    @FXML
    public ColumnConstraints day1;
    @FXML
    public ColumnConstraints day2;
    @FXML
    public ColumnConstraints day3;
    @FXML
    public ColumnConstraints day4;
    @FXML
    public ColumnConstraints day5;
    @FXML
    public ColumnConstraints day6;
    @FXML
    public ColumnConstraints day7;





    public OverviewController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        setOverview();
    }


    public GridPane getOverview() {
        return overview;
    }

    public void setOverview() throws IOException, ParseException, SQLException, ClassNotFoundException {
        calendarMonthView.setMaxWidth(200);
        calendarMonthView.prefHeightProperty().bind(calendarPane.heightProperty());
        calendarMonthView.prefWidthProperty().bind(calendarPane.widthProperty());
//
//        weekDayCol1.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol3.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol4.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol5.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol6.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//        weekDayCol7.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
//
//        weeklyView.getColumns().set(0, AppointmentDao.getAppointment(1));
//        weekDayCol1.setCellValueFactory(
//                new PropertyValueFactory<Appointment,String>("firstName")
//        );
        // set column widths
        day1.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day3.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day4.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day5.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day6.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day7.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));

        // create day labels
        Label test = new Label("DAY1");
        test.setAlignment(Pos.CENTER);
        test.setFont(new Font(20));
        Label test2 = new Label("DAY2");
        test2.setAlignment(Pos.CENTER);
        test2.setFont(new Font(20));

        // add labels
        weeklyView.add(test, 0, 0);
        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.14));
        weeklyView.add(test2, 1,0);

        // create appointment
        Appointment appt = AppointmentDao.getAppointment(1);
        Appointment appt2 = AppointmentDao.getAppointment(2);
        VBox apptBox = new VBox(5);
//        apptBox.setStyle("-fx-border-style: none none solid none; -fx-border-width: 0 0 1 0; -fx-border-color: LIGHTCORAL; -fx-padding: 10;");
        apptBox.getChildren().addAll(
                new Label(appt.getContact()),
                new Label(String.valueOf(appt.getStart().get(Calendar.HOUR) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM))),
                new Label(String.valueOf(appt.getEnd().get(Calendar.HOUR_OF_DAY) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM)))
        );

        // create separator
        Separator horSep = new Separator();
        horSep.setMaxWidth(day1.getPrefWidth() + 100);
        horSep.setHalignment(HPos.CENTER);
        horSep.setPadding(new Insets(10, 10, 10, 10));
        weeklyView.add(horSep, 0, 2);
        VBox apptBox2 = new VBox(5);
//        apptBox2.setStyle("-fx-border-style: none none solid none; -fx-border-width: 0 0 1 0; -fx-border-color: LIGHTCORAL; -fx-padding: 10; -fx-start-margin: 10; -fx-end-margin: 10;");
        apptBox2.getChildren().addAll(
                new Label(appt.getContact()),
                new Label(String.valueOf(appt.getStart().get(Calendar.HOUR) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM))),
                new Label(String.valueOf(appt.getEnd().get(Calendar.HOUR_OF_DAY) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM)))
        );
        weeklyView.add(apptBox, 0, 1);
        weeklyView.add(apptBox2, 1, 1);
//        weeklyView.add(horSep, 1, 2);
//        weeklyView.add(test, 2, 0);
//        weeklyView.add(test, 3, 0);
//        weeklyView.add(test, 4, 0);
//        weeklyView.add(test, 5, 0);
//        weeklyView.add(test, 6, 0);


    }

    public void onActionGetPrevMonth(ActionEvent actionEvent) {
    }

    public void onActionGetNextMonth(ActionEvent actionEvent) {
    }
}
