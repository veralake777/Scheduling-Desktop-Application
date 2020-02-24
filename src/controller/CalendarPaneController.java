package controller;

import DAO.AppointmentDao;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Appointment;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class CalendarPaneController {
    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() throws Exception {
        createAndConfigureCalendar();
    }

    // calendar parts
    Label[] dayLabels = new Label[49];

    // appointment parts
    Circle isAppointmentCircle = new Circle(10);
    Label[] appointmentLabels = new Label[49];

    // todo return this at the end of everything
    model.CalendarPane c = new model.CalendarPane(Locale.getDefault(), dayLabels, appointmentLabels);

    public void updateMonth(int i) throws ParseException, SQLException, ClassNotFoundException {
        // update month
        c.getCalendar().add(Calendar.MONTH, i);
        c.setCurrentMonth(Calendar.MONTH);
        c.setCurrentMonthString(c.getCalendar().getDisplayName(Calendar.MONTH, Calendar.LONG, c.getLocale()));

        // update header
        c.getMonthYearHeader().setText(c.getCurrentMonthString() + " " + c.getCurrentYear());
        c.setMonthYearHeader(c.getMonthYearHeader());

        // update days
        getDayPane();
        c.setDayLabels(dayLabels);

        // update calendar
        createAndConfigureCalendar();
    }

    private ButtonBar getPrevNextBtnBar() {
        // create button bar instance
        ButtonBar prevNextBtnBar = new ButtonBar();

        // create the buttons to go into ButtonBar
        Button nextMonthBtn = new Button("NEXT MONTH");
        ButtonBar.setButtonData(nextMonthBtn, ButtonBar.ButtonData.NEXT_FORWARD);
        nextMonthBtn.setAlignment(Pos.CENTER);
        nextMonthBtn.setFont(new Font(18));
        nextMonthBtn.setPrefSize(250, 10);
        nextMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO change getters and setters to access month from Date string
                try {
                    updateMonth(1);
                } catch (ParseException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                borderPane.setTop(c.getMonthYearHeader());
            }
        });


        Button prevMonthBtn = new Button("LAST MONTH");
        ButtonBar.setButtonData(prevMonthBtn, ButtonBar.ButtonData.BACK_PREVIOUS);
        prevMonthBtn.setAlignment(Pos.CENTER);
        prevMonthBtn.setFont(new Font(18));
        prevMonthBtn.setPrefSize(250, 10);
        prevMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO change getters and setters to access month from Date string
                try {
                    updateMonth(-1);
                } catch (ParseException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                borderPane.setTop(c.getMonthYearHeader());
            }
        });

        // add buttons to button bar
        prevNextBtnBar.getButtons().addAll(nextMonthBtn, prevMonthBtn);
        prevNextBtnBar.setTranslateX(-460);

        return prevNextBtnBar;
    }

    private void createAndConfigureCalendar() throws ParseException, SQLException, ClassNotFoundException {
        borderPane.getStyleClass().add("borderPane");

        // header
        borderPane.setTop(c.getMonthYearHeader());
        BorderPane.setAlignment(c.getMonthYearHeader(), Pos.CENTER);

        // center - calendar view
        borderPane.setCenter(getDayPane());

        // bottom prev next btnBar
        borderPane.setBottom(getPrevNextBtnBar());
        BorderPane.setAlignment(getPrevNextBtnBar(), Pos.CENTER);

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
        ColumnConstraints column = new ColumnConstraints(130);
        column.setHgrow(Priority.ALWAYS);
        dayPane.getColumnConstraints().addAll(column, column, column, column, column, column, column);

        // 5 rows
        RowConstraints row1 = new RowConstraints(30);
        row1.setValignment(VPos.CENTER);
        RowConstraints row = new RowConstraints(column.getPrefWidth() -40);
        dayPane.getRowConstraints().addAll( row1, row, row, row, row, row, row);
        dayPane.setGridLinesVisible(true);

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
            dayLabels[i].setText(dayNames[i + 1]);

            // Center text horizontally and vertically
            GridPane.setConstraints(dayLabels[i],  i,  0,  1,  1, HPos.CENTER, VPos.CENTER);
        }
    }

    public void showDays() throws ParseException, SQLException, ClassNotFoundException {
        int startingDayOfMonth = c.getCalendar().get(Calendar.DAY_OF_WEEK);
        ObservableList<Appointment> appts = null;

        // get appointments for current month
        try {
            appts = AppointmentDao.getAppointmentsWithinMonth(c.getCurrentMonth());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
        int appointmentDate;

        // Display days of this month
        int daysInCurrentMonth = c.getCalendar().getActualMaximum(
                Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInCurrentMonth; i++) {
            int index = i + 3 + startingDayOfMonth + 7;
            // offset with starting day of month
            dayLabels[index].setText(i + "");
            dayLabels[index].setPadding(new Insets(-60, 0, 0, 10));

            // map appointments to correct dayNumber
            assert appts != null;
            for(Appointment a : appts){
                int startDate = a.getStart().get(Calendar.DAY_OF_MONTH);
//                appointments[index].setText(a.getTitle());
                if(startDate == i) {
                    c.setAppointment(true);
                    // TODO color differences based on type
                    c.setIsAppointmentCircle(isAppointmentCircle, Color.LIGHTCORAL);
                    appointmentLabels[index].setGraphic(c.getIsAppointmentCircle());
                    appointmentLabels[index].setPadding(new Insets( -60, 0, 0, 25));
                    c.setAppointment(false);
                }
            }
        }
    }
}
