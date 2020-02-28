package MVC.controller;

import DAO.AppointmentDao;
import DAO.POJO.Appointment;
import MVC.model.CalendarMonthModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import utils.DateTime.Internationalization;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * CalendarMonthController can update the CalendarMonthModel data and redraw it using the view.
 */

public class CalendarMonthController {
    @FXML
    private BorderPane calendarMonthView;

    public CalendarMonthController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        createAndConfigureCalendar();
    }

//    private CalendarMonthModel c;
//    private FXMLLoader view = FXMLLoader.load(this.getClass().getResource("../view/calendarMonthView.fxml"));
//
//    // constructor
//    public CalendarMonthController(CalendarMonthModel calendar, FXMLLoader view) throws IOException {
//        this.c = calendar;
//        this.view = view;
//    }

    // calendar parts
    Label[] dayLabels = new Label[49];

    // appointment parts
    Color circleColor = new Color(.941, .502, .502, .5);
    Circle isAppointmentCircle = new Circle(10, 10, 15, circleColor);

    Label[] appointmentLabels = new Label[49];

    Internationalization getLocale = new Internationalization(Locale.getDefault());
    Locale locale  = getLocale.getCurrentLocale();
    GregorianCalendar calendar = new GregorianCalendar();
    int currentMonth = calendar.get(Calendar.MONTH);
    int currentYear = calendar.get(Calendar.YEAR);
    int today = calendar.get(Calendar.DAY_OF_MONTH);;
    String currentMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
    Label monthYearHeader = new Label(currentMonthString + " " + String.valueOf(currentYear));
    int totalDaysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    CalendarMonthModel c = new CalendarMonthModel(locale, calendar, currentMonth, currentYear, totalDaysInCurrentMonth,
            today, currentMonthString, monthYearHeader, dayLabels, appointmentLabels,
            false, null);


    public void createAndConfigureCalendar() throws ParseException, SQLException, ClassNotFoundException {
        calendarMonthView.getStyleClass().add("borderPane");

        // header
        HBox header = new HBox(5);
        c.getMonthYearHeader().setFont(new Font(14));
        header.setPrefHeight(20);
//        header.setMaxSize(200, 20);
        header.getChildren().addAll(c.getMonthYearHeader(), getPrevNextBtnBar());
        calendarMonthView.setTop(header);
        BorderPane.setAlignment(c.getMonthYearHeader(), Pos.BASELINE_LEFT);

        // center - calendar MVC.view
        calendarMonthView.setCenter(getDayPane());

        // bottom prev next btnBar
//        calendarMonthView.setBottom(getPrevNextBtnBar());
        BorderPane.setAlignment(getPrevNextBtnBar(), Pos.BASELINE_RIGHT);
        calendarMonthView.setMaxHeight(200);
        calendarMonthView.setMaxHeight(200);
    }

    private ButtonBar getPrevNextBtnBar() {
        // create button bar instance
        ButtonBar prevNextBtnBar = new ButtonBar();
        Image btnImg = new Image(String.valueOf(new File("/images/arrow-btn.PNG")),15, 15, true, false);

        // style next button
        ImageView nextBtn = new ImageView(btnImg);
        nextBtn.setStyle("-fx-padding: 0;");
        Button nextMonthBtn = new Button();
        nextMonthBtn.setPadding(Insets.EMPTY);

        nextMonthBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-translate-x: -30;");


        ButtonBar.setButtonData(nextMonthBtn, ButtonBar.ButtonData.NEXT_FORWARD);
        nextMonthBtn.setGraphic(nextBtn);
        nextMonthBtn.setAlignment(Pos.CENTER);
        nextMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO change getters and setters to access month from Date string
                try {
                    updateMonth(1);
                } catch (ParseException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        Button prevMonthBtn = new Button();

        // style and reverse prevBtn
        ImageView prevBtn = new ImageView(btnImg);
        prevBtn.setScaleX(-1);
        prevMonthBtn.setGraphic(prevBtn);
        prevMonthBtn.setPadding(Insets.EMPTY);
        prevMonthBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-translate-x: 30;");

        prevMonthBtn.setPrefSize(btnImg.getRequestedWidth(), btnImg.getRequestedHeight());



        ButtonBar.setButtonData(prevMonthBtn, ButtonBar.ButtonData.BACK_PREVIOUS);
        prevMonthBtn.setAlignment(Pos.CENTER);
        prevMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO change getters and setters to access month from Date string
                try {
                    updateMonth(-1);
                } catch (ParseException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // add buttons to button bar
        prevNextBtnBar.getButtons().addAll(nextMonthBtn, prevMonthBtn);
        prevNextBtnBar.setPrefSize(btnImg.getWidth(), btnImg.getHeight());
        prevNextBtnBar.setPadding(Insets.EMPTY);
        prevNextBtnBar.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        return prevNextBtnBar;
    }

    private GridPane getDayPane() throws ParseException, SQLException, ClassNotFoundException {
        // build lists
        for (int i = 0; i < 49; i++) {
            dayLabels[i] = new Label();
            dayLabels[i].setTextAlignment(TextAlignment.LEFT);

            appointmentLabels[i] = new Label();
            appointmentLabels[i].setTextAlignment(TextAlignment.RIGHT);
        }

        // set lists
        c.setDayLabels(dayLabels);
        c.setAppointments(appointmentLabels);

        // Create DayPane
        GridPane dayPane = new GridPane();
        dayPane.setAlignment(Pos.CENTER);
        dayPane.getStyleClass().addAll("days");

        // 7 columns
        ColumnConstraints column = new ColumnConstraints(40);
        column.setHgrow(Priority.ALWAYS);
        dayPane.getColumnConstraints().addAll(column, column, column, column, column, column, column);

        // 5 rows
        RowConstraints row1 = new RowConstraints(40);
        row1.setValignment(VPos.CENTER);
        RowConstraints row = new RowConstraints(column.getPrefWidth());
        dayPane.getRowConstraints().addAll( row1, row, row, row, row, row, row);
//        dayPane.setGridLinesVisible(true);

        // LIST dayPane
        Label[] d = c.getDayLabels();
        // LIST appointments
        Label[] a = c.getAppointments();

        // build dayPane
        for (int i = 0; i < 49; i++) {
            dayPane.add(d[i], i % 7, i / 7);
            dayPane.add(a[i], i % 7, i / 7);
        }
        showDays();
        showDaysOfWeek();

        return dayPane;
    }

    private void showDaysOfWeek() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        String[] dayNames = dfs.getWeekdays();


        // jlblDay[0], jlblDay[1], ..., jlblDay[6] for day names
        for (int i = 0; i < 7; i++) {
//            dayLabels[i].setTextAlignment(TextAlignment.CENTER);
            String name = String.valueOf(dayNames[i + 1].charAt(0));
            dayLabels[i].setText(name);

            // Center text horizontally and vertically
            GridPane.setConstraints(dayLabels[i],  i,  0,  1,  1, HPos.CENTER, VPos.CENTER);
        }
    }

    public void showDays() throws ParseException, SQLException {
        int startingDayOfMonth = c.getCalendar().get(Calendar.DAY_OF_WEEK);
        ObservableList<Appointment> appts = AppointmentDao.getAppointmentsWithinMonth(c);

        // Display days of this month
        int daysInCurrentMonth = c.getCalendar().getActualMaximum(
                Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInCurrentMonth; i++) {
            int index = i + 3 + startingDayOfMonth + 7;
            // offset with starting day of month
            dayLabels[index].setText(i + "");
//            dayLabels[index].setPadding(new Insets(-55, 0, 0, 10));

            // map appointments to correct dayNumber
            assert appts != null;
            for(Appointment a : appts){
                int startDate = a.getStart().get(Calendar.DAY_OF_MONTH);
//                appointments[index].setText(a.getTitle());
                if(startDate == i) {
                    c.setAppointment(true);
                    // TODO color differences based on type
                    c.setIsAppointmentCircle(isAppointmentCircle);
                    appointmentLabels[index].setGraphic(c.getIsAppointmentCircle());
//                    appointmentLabels[index].setPadding(new Insets( -52.5, 0, 0, 6));
                    c.setAppointment(false);
                }
            }
        }
    }

    private void updateMonth(int i) throws ParseException, SQLException, ClassNotFoundException {
        // update month
        c.getCalendar().add(Calendar.MONTH, i);
        c.setCurrentMonth(Calendar.MONTH);
        c.setCurrentMonthString(c.getCalendar().getDisplayName(Calendar.MONTH, Calendar.LONG, c.getLocale()));

        // update header
        c.getMonthYearHeader().setText(c.getCurrentMonthString() + " " + c.getCurrentYear());
        c.setMonthYearHeader(c.getMonthYearHeader());

        // update days
        getDayPane();

        // update calendar
        createAndConfigureCalendar();
    }
}
