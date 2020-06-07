package Components.Calendar;

import Components.Appointments.AppointmentCard;
import Components.Main;
import DbDao.DbAppointmentDao;
import POJO.Appointment;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utils.DBUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * resource: https://gist.github.com/james-d/c4a2cf66efecbf3aa362
 **/
public class Week {
    private final Main main;
    private final LocalTime firstSlotStart = LocalTime.of(8, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime lastSlotStart = LocalTime.of(18, 59);

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    public Week(Main main) {
        this.main = main;
    }

    public GridPane getView() throws Exception {
        GridPane weekView = new GridPane();
        weekView.getStyleClass().add("grid-pane");

        Stream<Appointment> appointmentStream =  new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentStream.forEach(appointmentObservableList::add);
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        // Start week on Monday + date
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        // go through end of week
        LocalDate endOfWeek = monday.plusDays(4);
        int col = 1;
        for (LocalDate date = monday; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            int slotIndex = 1;
            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                 !startTime.isAfter(date.atTime(lastSlotStart));
                 startTime = startTime.plus(slotLength)) {


                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);

                // set existing appointments
                for (Appointment a : appointmentObservableList) {
                    long minutes = a.getEnd().getMinutes() - a.getStart().getMinutes();
                    Duration duration = Duration.ofMinutes(minutes);
                    TimeSlot existingSlot = new TimeSlot(a.getStart().toLocalDateTime(), duration);
                    if (timeSlot.getStart().equals(existingSlot.getStart())) {
                        timeSlot.setSelected(true);
                        // if selected then show AppointmentCard.getEditAppointmentStage()
                        timeSlot.registerOnClick(main, a.getId());
                        // drag handler
                        registerDragHandlers(timeSlot, mouseAnchor);
                        weekView.add(timeSlot.getView(), col, slotIndex);
                        weekView.add( new Label(a.getType()), col, slotIndex);
                    }
                }

                // if not selected then show AppointmentCard.getNewAppointmentStage()
                if(!timeSlot.isSelected()) {
                    timeSlot.registerOnClick(main, -1);
                    // drag handler
                    registerDragHandlers(timeSlot, mouseAnchor);
                    weekView.add(timeSlot.getView(), col, slotIndex);
                }
                slotIndex++;
            }
            col++;
        }

        //headers:
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE d");
        col = 1;
        for (LocalDate date = monday; !date.isAfter(endOfWeek); date = date.plusDays(1)) {

            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            label.getStyleClass().add("days");

            // highlight today
            if(LocalDate.now().equals(date)) {
                label.setStyle("-fx-background-color: #38909b;" +
                        "-fx-font-size: 22;");
            };

            GridPane.setHalignment(label, HPos.CENTER);
            weekView.add(label, col, 0);
            col++;
        }

        // TIMES
        int slotIndex = 1 ;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = monday.atTime(firstSlotStart);
             ! startTime.isAfter(monday.atTime(lastSlotStart));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            label.getStyleClass().add("times");
            GridPane.setHalignment(label, HPos.RIGHT);
            weekView.add(label, 0, slotIndex);
            slotIndex++ ;
        }
        weekView.getStylesheets().add("CSS/calendarPane.css");
        return weekView;
    }


    // Registers handlers on the time slot to manage selecting a range of slots in the grid.

    /**
     * *************************************************************************************
     *   Provide the ability to add, update, and delete appointments, capturing the type of
     *   appointment and a link to the specific customer record in the database.
     * *************************************************************************************
     * on mouse released
     * *  show popup with new appointment holding values:
     *      *  from the timeSlot:
     *          *  WeekDay(s), Duration, Start & End times
     *      *  info to fill in:
     *          * customerName, Title, description, location, contact info, type, url
     *
     * @param timeSlot
     * @param mouseAnchor
     **/
    private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
        timeSlot.getView().setOnDragDetected(event -> {
            mouseAnchor.set(timeSlot);
            timeSlot.getView().startFullDrag();
            timeSlots.forEach(slot ->
                    slot.setSelected(slot == timeSlot));
        });

        // setOnMouseDragEntered:
        timeSlot.getView().setOnMouseDragEntered(event -> {
            TimeSlot startSlot = mouseAnchor.get();
            timeSlots.forEach(slot ->{
                slot.setSelected(isBetween(slot, startSlot, timeSlot));
            });
        });

        timeSlot.getView().setOnMouseReleased(event -> {
            // New Appointment pop up with appointment date and time information auto filled
            AppointmentCard appointmentCard = new AppointmentCard(main);
            Stage popup = appointmentCard.getNewAppointmentStage(timeSlot.start, Duration.ofMinutes(lastSlotStart.getMinute() - firstSlotStart.getMinute()));
            popup.showAndWait();
            if(!popup.isShowing()) {
                try {
                    this.getView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // set mouse anchor to null
            mouseAnchor.set(null);
        });
    }

    // Utility method that checks if testSlot is "between" startSlot and endSlot
    // Here "between" means in the visual sense in the grid: i.e. does the time slot
    // lie in the smallest rectangle in the grid containing startSlot and endSlot
    //
    // Note that start slot may be "after" end slot (either in terms of day, or time, or both).

    // The strategy is to test if the day for testSlot is between that for startSlot and endSlot,
    // and to test if the time for testSlot is between that for startSlot and endSlot,
    // and return true if and only if both of those hold.

    // Finally we note that x <= y <= z or z <= y <= x if and only if (y-x)*(z-y) >= 0.

    private boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {
        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek())
                * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) >= 0 ;

        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) >= 0 ;

        return daysBetween && timesBetween;
    }

    // Class representing a time interval, or "Time Slot", along with a view.
    // View is just represented by a region with minimum size, and style class.

    // Has a selected property just to represent selection.

    public static class TimeSlot {

        private final LocalDateTime start ;
        private final Duration duration ;
        private final Region view ;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected ;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public void registerOnClick(Main main, int appointmentId) {
            if(appointmentId == -1) {
                this.view.setOnMouseClicked(mouseEvent -> {
                    AppointmentCard appointmentCard = new AppointmentCard(main);
                    Stage popup = appointmentCard.getNewAppointmentStage(this.start, this.duration);
                    popup.showAndWait();
                });
            } else {
                this.view.setOnMouseClicked(mouseEvent -> {
                    AppointmentCard appointmentCard = null;
                    try {
                        appointmentCard = new AppointmentCard(appointmentId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        assert appointmentCard != null;
                        Stage popup = appointmentCard.getEditAppointmentStage();
                        popup.showAndWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            Duration duration1 ;
            this.start = start ;
            duration1 = duration ;

            view = new Region() ;

            int minHeight = 20 ;
            while(duration1.toMinutes() >= 15) {
                minHeight = minHeight + 20 ;
                duration1 = duration1.minusMinutes(15) ;
            }


            this.duration = duration1;
            view.setMinSize(80, minHeight);
            view.getStyleClass().addAll("time-slot", "time-slot:selected");

            selectedProperty().addListener((obs, wasSelected, isSelected) -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalTime getTime() {
            return start.toLocalTime();
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public Node getView() {
            return view;
        }
    }
}
