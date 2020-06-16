package Components.Calendar;

import Components.Appointments.NextAppointment;
import POJO.User;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarView {
    User user;
    GridPane calendarView;
    Month month;
    Week week;
    NextAppointment nextAppointment;

    public CalendarView(User user) throws Exception {
        this.user = user;
        this.week = new Week(user);
        this.month = new Month(user, week);
        this.nextAppointment = new NextAppointment();
    }

    private void build() throws Exception {
        calendarView = new GridPane();
        calendarView.setStyle("-fx-padding: 0 5;");
        ColumnConstraints columnConstraintsCalendar = new ColumnConstraints();
        columnConstraintsCalendar.setPercentWidth(20);
        ColumnConstraints columnConstraintsWeek = new ColumnConstraints();
        columnConstraintsWeek.setPercentWidth(80);
        Month month = new Month(user, week);
        VBox firstColumn = new VBox(10);
        ScrollPane weekSP = null;
        try {
            month.build(YearMonth.now());
            weekSP = week.getView(LocalDate.now().with(DayOfWeek.MONDAY));
            weekSP.getStylesheets().add("CSS/tableView.css");
            weekSP.setMaxWidth(Screen.getPrimary().getBounds().getWidth() * .6 - 15);
            weekSP.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight() - 175);
            firstColumn.getChildren().addAll(month.getView(), new NextAppointment().getNextAppointmentVBox());
        } catch (Exception e) {
            e.printStackTrace();
        }

        calendarView.getColumnConstraints().addAll(columnConstraintsCalendar, columnConstraintsWeek);
        calendarView.addColumn(0, firstColumn);
        calendarView.addColumn(1, weekSP);
    }

    public GridPane getView() throws Exception {
        build();
        return calendarView;
    }
}
