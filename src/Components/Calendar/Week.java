package Components.Calendar;

import Components.Appointments.AppointmentCard;
import DbDao.DbAppointmentDao;
import POJO.Appointment;
import POJO.User;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
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
    private User user;
    private final LocalTime FIRST_SLOT_START = LocalTime.of(8, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime LAST_SLOT_END = LocalTime.of(18, 59);
    // Start week on Monday + date
    public LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    public Week(User user) {
        this.user = user;
    }

    GridPane weekView = new GridPane();

    public ScrollPane getView(LocalDate monday) throws Exception {
        // clear gridpane
        weekView.getChildren().clear();
        weekView.getColumnConstraints().clear();
        weekView.getRowConstraints().clear();

        // add col contraints
        ColumnConstraints col1 = new ColumnConstraints(55);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth((100.0 / 8) - col1.getPrefWidth());
        weekView.getColumnConstraints().addAll(col1, col2, col2, col2, col2, col2, col2, col2);

        // previous and next buttons
        Button previousWeek = new Button("<");
        previousWeek.setOnAction(e -> {
            try {
                previousWeek();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button nextWeek = new Button(">");
        nextWeek.setOnAction(e -> {
            try {
                nextWeek();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        previousWeek.setStyle("-fx-padding: 10 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 14;" +
                "-fx-background-color: #38909b;");
        nextWeek.setStyle("-fx-padding: 10 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 14;" +
                "-fx-background-color: #38909b;");

        // mouse anchor for dragging appointment
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

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

                timeSlot.getView().setOnMouseClicked(mouseEvent -> {
                    AppointmentCard appointmentCard = new AppointmentCard(user, timeSlot.getStart().toLocalDate(), timeSlot.getDuration());
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

        // DAY NAMES:
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE d");
        col = 1;
        for (LocalDate date = monday; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            label.getStyleClass().add("days");

            // highlight today
            if (LocalDate.now().equals(date)) {
                label.setStyle("-fx-background-color: #38909b;" +
                        "-fx-font-size: 22;");
            }

            GridPane.setHalignment(label, HPos.CENTER);
            if (col == 1) {
                // add buttons to row
                HBox hBox = new HBox(previousWeek, label);
                hBox.setSpacing(5);
                HBox.setHgrow(label, Priority.ALWAYS);
                HBox.setHgrow(nextWeek, Priority.ALWAYS);
                weekView.add(hBox, col, 0);
            } else if (col == 5) {
                HBox hBox = new HBox(label, nextWeek);
                hBox.setSpacing(5);
                HBox.setHgrow(label, Priority.ALWAYS);
                HBox.setHgrow(nextWeek, Priority.ALWAYS);
                weekView.add(hBox, col, 0);
            } else {
                weekView.add(label, col, 0);
            }
            col++;
        }

        // TIMES
        int slotIndex = 1;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = monday.atTime(FIRST_SLOT_START);
             !startTime.isAfter(monday.atTime(LAST_SLOT_END));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            label.getStyleClass().add("times");
            GridPane.setHalignment(label, HPos.RIGHT);
            weekView.add(label, 0, slotIndex);
            slotIndex++;
        }
        weekView.getStylesheets().add("CSS/calendarPane.css");
        weekView.setMaxWidth(Screen.getPrimary().getBounds().getWidth() * .56);
        setAppointments();
        ScrollPane scrollPane = new ScrollPane(weekView);
        scrollPane.pannableProperty().set(false);
        scrollPane.getStylesheets().add("CSS/tableView.css");
        return scrollPane;
    }

    public ScrollPane getView(User user, LocalDate monday) throws Exception {
        this.user = user;
        // clear gridpane
        weekView.getChildren().clear();
        weekView.getColumnConstraints().clear();
        weekView.getRowConstraints().clear();

        // add col contraints
        ColumnConstraints col1 = new ColumnConstraints(55);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth((100.0 / 8) - col1.getPrefWidth());
        weekView.getColumnConstraints().addAll(col1, col2, col2, col2, col2, col2, col2, col2);

        // previous and next buttons
        Button previousWeek = new Button("<");
        previousWeek.setOnAction(e -> {
            try {
                previousWeek();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button nextWeek = new Button(">");
        nextWeek.setOnAction(e -> {
            try {
                nextWeek();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        previousWeek.setStyle("-fx-padding: 10 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 14;" +
                "-fx-background-color: #38909b;");
        nextWeek.setStyle("-fx-padding: 10 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 14;" +
                "-fx-background-color: #38909b;");

        // mouse anchor for dragging appointment
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

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

                timeSlot.getView().setOnMouseClicked(mouseEvent -> {
                    AppointmentCard appointmentCard = new AppointmentCard(user, timeSlot.getStart().toLocalDate(), timeSlot.getDuration());
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

        // DAY NAMES:
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE d");
        col = 1;
        for (LocalDate date = monday; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            label.getStyleClass().add("days");

            // highlight today
            if (LocalDate.now().equals(date)) {
                label.setStyle("-fx-background-color: #38909b;" +
                        "-fx-font-size: 22;");
            }

            GridPane.setHalignment(label, HPos.CENTER);
            if (col == 1) {
                // add buttons to row
                HBox hBox = new HBox(previousWeek, label);
                hBox.setSpacing(5);
                HBox.setHgrow(label, Priority.ALWAYS);
                HBox.setHgrow(nextWeek, Priority.ALWAYS);
                weekView.add(hBox, col, 0);
            } else if (col == 5) {
                HBox hBox = new HBox(label, nextWeek);
                hBox.setSpacing(5);
                HBox.setHgrow(label, Priority.ALWAYS);
                HBox.setHgrow(nextWeek, Priority.ALWAYS);
                weekView.add(hBox, col, 0);
            } else {
                weekView.add(label, col, 0);
            }
            col++;
        }

        // TIMES
        int slotIndex = 1;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = monday.atTime(FIRST_SLOT_START);
             !startTime.isAfter(monday.atTime(LAST_SLOT_END));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            label.getStyleClass().add("times");
            GridPane.setHalignment(label, HPos.RIGHT);
            weekView.add(label, 0, slotIndex);
            slotIndex++;
        }
        weekView.getStylesheets().add("CSS/calendarPane.css");
        weekView.setMaxWidth(Screen.getPrimary().getBounds().getWidth() * .56);
        setAppointments();
        ScrollPane scrollPane = new ScrollPane(weekView);
        scrollPane.pannableProperty().set(false);
        scrollPane.getStylesheets().add("CSS/tableView.css");
        return scrollPane;
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousWeek() throws Exception {
        monday = monday.minusDays(7);
        getView(monday);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextWeek() throws Exception {
        monday = monday.plusDays(7);
        getView(monday);
    }

    private void setAppointments() throws Exception {
        Stream<Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentStream.forEach(appointmentObservableList::add);
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);

            // set existing appointments
            for (Appointment a : appointmentObservableList) {
                long minutes = a.getEnd().getMinutes() - a.getStart().getMinutes();
                Duration duration = Duration.ofMinutes(minutes);
                TimeSlot existingSlot = new TimeSlot(a.getStart().toLocalDateTime(), Duration.ofMinutes(a.getEnd().getTime() - a.getStart().getTime()));
                if (timeSlot.getStart().equals(existingSlot.getStart()) && a.getUserId() == user.getId()) {
                    timeSlot.duration = Duration.ofMinutes(duration.toMinutes());
                    Duration tempD = Duration.ofMinutes(timeSlot.duration.toMinutes());
                    int j = i;
                    while (tempD.toMinutes() >= 15) {
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
                appointmentCard = new AppointmentCard(a.getId(), timeSlot.getStart().toLocalDate(), this);
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
     * Provide the ability to add, update, and delete appointments, capturing the type of
     * appointment and a link to the specific customer record in the database.
     * *************************************************************************************
     * on mouse released
     * *  show popup with new appointment holding values:
     * *  from the timeSlot:
     * *  WeekDay(s), Duration, Start & End times
     * *  info to fill in:
     * * customerName, Title, description, location, contact info, type, url
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
            timeSlots.forEach(slot -> {
                slot.setSelected(isBetween(slot, startSlot, timeSlot));
            });
        });

        timeSlot.getView().setOnMouseReleased(event -> {
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
        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek()) * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) == 0;

        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) >= 0 && testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) <= 60;

        return daysBetween && timesBetween;
    }

    // Class representing a time interval, or "Time Slot", along with a view.
    // View is just represented by a region with minimum size, and style class.

    // Has a selected property just to represent selection.

    public static class TimeSlot {

        private final LocalDateTime start;
        private Duration duration;
        private final StackPane view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;

            view = new StackPane();
            view.setPrefWidth(50);
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