package Components.Appointments;

import DbDao.DbAppointmentDao;
import POJO.Appointment;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.DBUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class NextAppointment {
    VBox vBox;
    Appointment nextAppointment;

    public NextAppointment() {
        buildAppointment();
    }

    public VBox getNextAppointmentVBox() {
        return vBox;
    }

    private void buildAppointment() {
        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setStyle("-fx-background-color: WHITE;" +
                "-fx-padding: 15;" +
                "-fx-border-color: GREY;" +
                "-fx-border-width: 2;");
        // db stuff
        try {
            final var dataSource = DBUtils.getMySQLDataSource();
            final var dbDao = new DbAppointmentDao(dataSource);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String today = dateFormat.format(cal.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            nextAppointment = dbDao.getAll()
                    .filter(appointment -> today.equals(sdf.format(appointment.getStart()))) // filter by today's date
                    .findFirst()                                                      // find first instance for date
                    .orElse(null);                                              // else nextAppointment is null

            // header
            Text header = new Text("NEXT APPOINTMENT");
            header.setStyle("-fx-underline: true;");
            header.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
            header.setWrappingWidth(250);
            if (nextAppointment != null) {
                // appointment data
                Label type = new Label("TYPE:    " + nextAppointment.getType());
                Label startTime = new Label(String.format(String.format("TIME:    " +
                        nextAppointment.getStart().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")))));
                Label date = new Label("DATE:    " + nextAppointment.getStart().toLocalDateTime().toLocalDate().toString());
                type.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
                startTime.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
                date.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
                vBox.getChildren().addAll(
                        header,
                        type,
                        date,
                        startTime
                );
            } else {
                header.setText("NO UPCOMING APPOINTMENTS");
                vBox.getChildren().addAll(header);
            }

        } catch (Exception e) {
            System.out.println("ERROR IN NEXT APPOINTMENT DAO");
            e.printStackTrace();
        }
    }
}
