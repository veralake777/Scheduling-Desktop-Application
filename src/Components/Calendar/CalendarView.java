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
        this.month = new Month(user);
        this.week = new Week(user);
        this.nextAppointment = new NextAppointment();
    }

    private void build() throws Exception {
        calendarView = new GridPane();
        calendarView.setStyle("-fx-padding: 0 5;");
        ColumnConstraints columnConstraintsCalendar = new ColumnConstraints();
        columnConstraintsCalendar.setPercentWidth(20);
        ColumnConstraints columnConstraintsWeek = new ColumnConstraints();
        columnConstraintsWeek.setPercentWidth(80);
        Month month = new Month(user);
        VBox firstColumn = new VBox(10);
        ScrollPane week = null;
        try {
            month.build(YearMonth.now());
            week = new Week(user).getView(LocalDate.now().with(DayOfWeek.MONDAY));
            week.getStylesheets().add("CSS/tableView.css");
            week.setMaxWidth(Screen.getPrimary().getBounds().getWidth() * .6 - 15);
            week.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight() - 175);
            firstColumn.getChildren().addAll(month.getView(), new NextAppointment().getNextAppointmentVBox());
        } catch (Exception e) {
            e.printStackTrace();
        }

        calendarView.getColumnConstraints().addAll(columnConstraintsCalendar, columnConstraintsWeek);
        calendarView.addColumn(0, firstColumn);
        calendarView.addColumn(1, week);
    }

    public GridPane getView() throws Exception {
        build();
        return calendarView;
    }
}
