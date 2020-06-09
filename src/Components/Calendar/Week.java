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
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
    private final LocalTime FIRST_SLOT_START = LocalTime.of(8, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime LAST_SLOT_END = LocalTime.of(18, 59);

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    public Week(Main main) {
        this.main = main;
    }

    public GridPane getView() throws Exception {
        GridPane weekView = new GridPane();
        weekView.getStyleClass().add("grid-pane");

        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        // Start week on Monday + date
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        // go through end of week
        LocalDate endOfWeek = monday.plusDays(4);
        int col = 1;
        for (LocalDate date = monday; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            int slotIndex = 1;
            for (LocalDateTime startTime = date.atTime(FIRST_SLOT_START);
                 !startTime.isAfter(date.atTime(LAST_SLOT_END));
                 startTime = startTime.plus(slotLength)) {

                // this is the time slot cell
                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);

                timeSlot.getView().setOnMouseClicked(mouseEvent-> {
                    AppointmentCard appointmentCard = new AppointmentCard(main);
                    Stage popup = appointmentCard.getNewAppointmentStage(timeSlot.getStart(), slotLength);
                    popup.showAndWait();
                });

                // drag handler
                registerDragHandlers(timeSlot, mouseAnchor);
                weekView.add(timeSlot.getView(), col, slotIndex);

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
        for (LocalDateTime startTime = monday.atTime(FIRST_SLOT_START);
             ! startTime.isAfter(monday.atTime(LAST_SLOT_END));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            label.getStyleClass().add("times");
            GridPane.setHalignment(label, HPos.RIGHT);
            weekView.add(label, 0, slotIndex);
            slotIndex++ ;
        }
        weekView.getStylesheets().add("CSS/calendarPane.css");
        setAppointments();
        return weekView;
    }

    private void setAppointments() throws Exception {
        Stream<Appointment> appointmentStream =  new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentStream.forEach(appointmentObservableList::add);
        for (int i = 0; i<timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);

            // set existing appointments
            for (Appointment a : appointmentObservableList) {
                long minutes = a.getEnd().getMinutes() - a.getStart().getMinutes();
                Duration duration = Duration.ofMinutes(minutes);
                TimeSlot existingSlot = new TimeSlot(a.getStart().toLocalDateTime(), Duration.ofMinutes(30));
                if (timeSlot.getStart().equals(existingSlot.getStart())) {
                    timeSlot.duration = Duration.ofMinutes(duration.toMinutes());
                    Duration tempD = Duration.ofMinutes(timeSlot.duration.toMinutes());
                    int j = i;
                    while(tempD.toMinutes() >= 15) {
                        TimeSlot currentTimeSlot = timeSlots.get(j);
                        tempD = tempD.minusMinutes(15);
                        currentTimeSlot.setSelected(true);
                        // clear current mouse event from current time slot from getView()
                        registerOnMouseClicked(currentTimeSlot, a);
                        j++;
                    }
                    timeSlots.get(i).setSelected(true);

                    // add label for appointment to start time slot only
                    timeSlot.addLabel(new Label(a.getType()));
                    registerOnMouseClicked(timeSlot, a);
                    break;
                }
            }
        }
    }

    private void registerOnMouseClicked(TimeSlot timeSlot, Appointment a) {
        timeSlot.getView().removeEventHandler(EventType.ROOT, Event::consume);
        timeSlot.getView().setOnMouseClicked(mouseEvent -> {
            AppointmentCard appointmentCard = null;
            try {
                appointmentCard = new AppointmentCard(a.getId());
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
//            AppointmentCard appointmentCard = new AppointmentCard(main);
//            Stage popup = appointmentCard.getNewAppointmentStage(timeSlot.start, Duration.ofMinutes(LAST_SLOT_END.getMinute() - FIRST_SLOT_START.getMinute()));
//            popup.showAndWait();
//            if(!popup.isShowing()) {
//                try {
//                    this.getView();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
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
        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek()) * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) == 0 ;

        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) >= 0 && testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) <= 60;

        return daysBetween && timesBetween;
    }

    // Class representing a time interval, or "Time Slot", along with a view.
    // View is just represented by a region with minimum size, and style class.

    // Has a selected property just to represent selection.

    public static class TimeSlot {

        private final LocalDateTime start ;
        private Duration duration ;
        private final StackPane view ;

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

//        public void registerOnClick(Main main, int appointmentId) {
//            this.view.setOnMouseClicked(null);
//            if(appointmentId == -1) {
//                this.view.setOnMouseClicked(mouseEvent -> {
//                    AppointmentCard appointmentCard = new AppointmentCard(main);
//                    Stage popup = appointmentCard.getNewAppointmentStage(this.start, this.duration);
//                    popup.showAndWait();
//                });
//            } else {
//                this.view.setOnMouseClicked(mouseEvent -> {
//                    AppointmentCard appointmentCard = null;
//                    try {
//                        appointmentCard = new AppointmentCard(appointmentId);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        assert appointmentCard != null;
//                        Stage popup = appointmentCard.getEditAppointmentStage();
//                        popup.showAndWait();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//
//        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start ;

            view = new StackPane() ;
            view.setMinSize(80, 20);
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

        public StackPane getView() {
            return view;
        }

        public void addLabel(Label label) {
            view.getChildren().add(label);
        }
    }
}
