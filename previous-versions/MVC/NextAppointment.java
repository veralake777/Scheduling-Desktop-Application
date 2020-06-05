package MVC;

import iluwatar.DbDao.DbAppointmentDao;
import iluwatar.POJO.Appointment;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Database.DBUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NextAppointment {
    VBox vBox;
    Appointment nextAppointment;

    public NextAppointment() throws Exception {
        buildAppointment();
    }

    public VBox getNextAppointmentVBox() throws Exception {
        return vBox;
    }

    private void buildAppointment() throws Exception {
        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: GREY;");
        // db stuff
        try {
            final var dataSource = DBUtils.getMySQLDataSource();
            final var dbDao = new DbAppointmentDao(dataSource);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String today = dateFormat.format(cal.getTime());
            nextAppointment = dbDao.getAll()
            .filter(appointment-> today.equals(appointment.getStart())) // filter by today's date
            .findFirst()                                                      // find first instance for date
            .orElse(null);                                              // else nextAppointment is null

            // header
            Text header = new Text("NEXT APPOINTMENT");
            header.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
            header.setWrappingWidth(250);
            if(nextAppointment != null) {
                // appointment data
                Text nextAppointmentString = new Text(nextAppointment.toString());
                nextAppointmentString.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
                vBox.getChildren().addAll(
                        header,
                        nextAppointmentString
                );
            } else {
                header.setText("NO UPCOMING APPOINTMENTS");
                vBox.getChildren().addAll(header);
            }

        } catch (Exception e) {
            System.out.println("ERROR IN NEXT APPT DAO");
            e.printStackTrace();
        }
    }
}
