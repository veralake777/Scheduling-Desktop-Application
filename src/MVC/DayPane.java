package MVC;

import DAO.POJO.Appointment;
import javafx.scene.layout.GridPane;

/**
 * Labels for days
 */
public class DayPane {
    private GridPane dayPane;
    private int dateNumber;
    private Appointment appointment;


    public DayPane(GridPane dayPane, int dateNumber, Appointment appointment) {
        /* GridPane with dateNumber and Appointment data for days with appointments */
        this.dayPane = dayPane;
        this.dateNumber = dateNumber;
        this.appointment = appointment;
    }

    public DayPane(GridPane dayPane, int dateNumber) {
        /* GridPane with just the dateNumber for days without appointments*/
        this.dayPane = dayPane;
        this.dateNumber = dateNumber;
    }



}
