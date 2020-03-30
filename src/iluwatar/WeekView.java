package iluwatar;

import iluwatar.POJO.Appointment;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * resource: https://gist.github.com/james-d/c4a2cf66efecbf3aa362
 **/
public class WeekView {

        private final LocalTime firstSlotStart = LocalTime.of(8, 0);
        private final Duration slotLength = Duration.ofMinutes(15);
        private final LocalTime lastSlotStart = LocalTime.of(18, 59);

        private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

        private final List<TimeSlot> timeSlots = new ArrayList<>();
        private TimeSlot currentSlot;
        private static List<Appointment> appointments = new ArrayList<>();

        public WeekView() {
        }
        public GridPane getWeekView(){
            GridPane weekView = new GridPane();
            weekView.getStyleClass().add("grid-pane");

            ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

            LocalDate today = LocalDate.now();
            LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1) ;
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            for (LocalDate date = startOfWeek; ! date.isAfter(endOfWeek); date = date.plusDays(1)) {
                int slotIndex = 1 ;

                for (LocalDateTime startTime = date.atTime(firstSlotStart);
                     ! startTime.isAfter(date.atTime(lastSlotStart));
                     startTime = startTime.plus(slotLength)) {


                    TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                    timeSlots.add(timeSlot);

                    registerDragHandlers(timeSlot, mouseAnchor);

                    weekView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
                    slotIndex++ ;
                }
            }

            //headers:

            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");

            for (LocalDate date = startOfWeek; ! date.isAfter(endOfWeek); date = date.plusDays(1)) {
                Label label = new Label(date.format(dayFormatter));
                label.setPadding(new Insets(1));
                label.setTextAlignment(TextAlignment.CENTER);
                label.getStyleClass().add("days");
                GridPane.setHalignment(label, HPos.CENTER);
                weekView.add(label, date.getDayOfWeek().getValue(), 0);
            }

            int slotIndex = 1 ;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
            for (LocalDateTime startTime = today.atTime(firstSlotStart);
                 ! startTime.isAfter(today.atTime(lastSlotStart));
                 startTime = startTime.plus(slotLength)) {
                Label label = new Label(startTime.format(timeFormatter));
                label.setPadding(new Insets(2));
                label.getStyleClass().add("times");
                GridPane.setHalignment(label, HPos.RIGHT);
                weekView.add(label, 0, slotIndex);
                slotIndex++ ;
            }
            showResult();
            weekView.getStylesheets().add("iluwatar/calendar-view.css");

        return weekView;
        }

        // Registers handlers on the time slot to manage selecting a range of slots in the grid.

        private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
            timeSlot.getView().setOnDragDetected(event -> {
                mouseAnchor.set(timeSlot);
                timeSlot.getView().startFullDrag();
                timeSlots.forEach(slot ->
                        slot.setSelected(slot == timeSlot));
            });

            timeSlot.getView().setOnMouseDragEntered(event -> {
                TimeSlot startSlot = mouseAnchor.get();
                timeSlots.forEach(slot ->{
                        slot.setSelected(isBetween(slot, startSlot, timeSlot));
                });
            });
            timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));

//            System.out.println(timeSlots.subList(timeSlot.isSelected()  return 1, !timeSlot.isSelected()));
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

            return daysBetween && timesBetween ;
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

            public TimeSlot(LocalDateTime start, Duration duration) {
                this.start = start ;
                this.duration = duration ;

                view = new Region();
                view.setMinSize(80, 20);
                view.getStyleClass().add("time-slot");

                selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                            view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected);
//                            appointments.add(new Appointment(10, 1, 1, "TITLE", "DESCRIP", "loc", "8888888", "type", "url", ""));
            });
            }

            public LocalDateTime getStart() {
                return start ;
            }

            public LocalTime getTime() {
                return start.toLocalTime() ;
            }

            public DayOfWeek getDayOfWeek() {
                return start.getDayOfWeek() ;
            }

            public Duration getDuration() {
                return duration ;
            }

            public Node getView() {
                return view;
            }

            public void showAlert() {
                // TODO getDayOfWeek() start and end getTime() -> POPUP Appointment add new
                //  alert is fucked up. won't close and keeps getting new days and times

                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText(getTime().toString());
                a.showAndWait();
            }
        }

        public void showResult(){
            System.out.println(timeSlots.get(1).view);
        }


//
//        public static void main(String[] args) {
//            launch(args);
//        }
    }
