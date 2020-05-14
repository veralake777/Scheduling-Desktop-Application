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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
            LocalDate endOfWeek = today.plusDays(6);
            int col = 1;
            for (LocalDate date = today; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
                int slotIndex = 1;
                for (LocalDateTime startTime = date.atTime(firstSlotStart);
                     !startTime.isAfter(date.atTime(lastSlotStart));
                     startTime = startTime.plus(slotLength)) {


                    TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                    timeSlots.add(timeSlot);

                    registerDragHandlers(timeSlot, mouseAnchor);

                    weekView.add(timeSlot.getView(), col, slotIndex);
                    slotIndex++;
                }
                col++;
            }

            //headers:

            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");
            col = 1;
            for (LocalDate date = today; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
                Label label = new Label(date.format(dayFormatter));
                label.setPadding(new Insets(1));
                label.setTextAlignment(TextAlignment.CENTER);
                label.getStyleClass().add("days");
                GridPane.setHalignment(label, HPos.CENTER);
                weekView.add(label, col, 0);
                col++;
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
            weekView.getStylesheets().add("iluwatar/calendar-view.css");

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
     *      *  from the timeslot:
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

            timeSlot.getView().setOnMouseDragEntered(event -> {
                TimeSlot startSlot = mouseAnchor.get();
                timeSlots.forEach(slot ->{
                        slot.setSelected(isBetween(slot, startSlot, timeSlot));
                });
            });

            timeSlot.getView().setOnMouseReleased(event -> {mouseAnchor.set(null); appointmentPopup(timeSlot);});
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

            public TimeSlot(LocalDateTime start, Duration duration) {
                this.start = start;
                this.duration = duration ;

                view = new Region();
                view.setMinSize(80, 20);
                view.getStyleClass().add("time-slot");

                selectedProperty().addListener((obs, wasSelected, isSelected) -> { view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected); }
                    );
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

        private void appointmentPopup(TimeSlot timeSlot) {
            Stage popup = new Stage();
            popup.setX(300);
            popup.setY(200);

            VBox appointmentVBox = new VBox();
            appointmentVBox.getStylesheets().add("./CSS/modifyWindows.css");

            HBox contactHBox = new HBox(10);
            Label contactLbl = new Label("Customer Name");
            TextField contactTF = new TextField("enter the customer's name");
            contactTF.onMouseClickedProperty().set(e->{contactTF.clear();});
            contactHBox.getChildren().addAll(contactLbl, contactTF);

            HBox titleHBox = new HBox(10);
            Label titleLbl = new Label("Title");
            TextField titleTF = new TextField("enter the title");
            titleTF.onMouseClickedProperty().set(e->{titleTF.clear();});
            titleHBox.getChildren().addAll(titleLbl, titleTF);

            HBox descriptionHBox = new HBox(10);
            Label descriptionLbl = new Label("Description");
            TextField descriptionTF = new TextField("enter the description");
            descriptionTF.onMouseClickedProperty().set(e->{descriptionTF.clear();});
            descriptionHBox.getChildren().addAll(descriptionLbl, descriptionTF);

            HBox locationHBox = new HBox(10);
            Label locationLbl = new Label("Location");
            TextField locationTF = new TextField("enter the location");
            locationTF.onMouseClickedProperty().set(e->{locationTF.clear();});
            locationHBox.getChildren().addAll(locationLbl, locationTF);

            HBox typeHBox = new HBox(10);
            Label typeLbl = new Label("Type");
            TextField typeTF = new TextField("enter the type");
            typeTF.onMouseClickedProperty().set(e->{typeTF.clear();});
            typeHBox.getChildren().addAll(typeLbl, typeTF);

            HBox startHBox = new HBox(10);
            Label startLbl = new Label("Start");
            TextField startTF = new TextField(timeSlot.getStart().toString());
            startTF.onMouseClickedProperty().set(e->{startTF.clear();});
            startHBox.getChildren().addAll(startLbl, startTF);

            HBox endHBox = new HBox(10);
            Label endLbl = new Label("End");
            TextField endTF = new TextField(timeSlot.getDuration().toString());
            endTF.onMouseClickedProperty().set(e->{endTF.clear();});
            endHBox.getChildren().addAll(endLbl, endTF);

            HBox buttons = new HBox(10);
            Button show = new Button("Add");
            show.setOnAction(event -> System.out.println("Add new appt."));
            Button hide = new Button("Hide");
            hide.setOnAction(event -> popup.hide());
            buttons.getChildren().addAll(show, hide);

            appointmentVBox.getChildren().addAll(contactHBox, titleHBox, descriptionHBox, locationHBox, typeHBox, startHBox, endHBox, buttons);
            popup.setTitle("Add Appointment");
            popup.setScene(new Scene(appointmentVBox));
            popup.showAndWait();
        }

        public void showResult(){
            for (TimeSlot timeSlot : timeSlots) {
                System.out.println("show result:" + timeSlot.getDayOfWeek());
            }
        }
    }
