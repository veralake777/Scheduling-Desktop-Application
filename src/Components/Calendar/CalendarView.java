package Components.Calendar;

import Components.Appointments.NextAppointment;
import Components.Main;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.YearMonth;

public class CalendarView {
    Main main;
    GridPane calendarView;
    Month month;
    Week week;
    NextAppointment nextAppointment;

    public CalendarView(Main main) throws Exception {
        this.main = main;
        this.month = new Month(main);
        this.week = new Week(main);
        this.nextAppointment = new NextAppointment();
    }

    private void build() throws Exception {
        calendarView = new GridPane();
        calendarView.setStyle("-fx-padding: 0 5;");
        ColumnConstraints columnConstraintsCalendar = new ColumnConstraints();
        columnConstraintsCalendar.setPercentWidth(20);
        ColumnConstraints columnConstraintsWeek = new ColumnConstraints();
        columnConstraintsWeek.setPercentWidth(80);
        Month month = new Month(main);
        VBox firstColumn = new VBox(10);
        GridPane week = null;
        firstColumn.setFillWidth(true);
        try {
            month.build(YearMonth.now());
            week = new Week(main).getView();
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
