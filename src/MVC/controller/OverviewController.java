package MVC.controller;

import MVC.view.AppointmentVBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import utils.DateTime.DateTimeUtils;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class OverviewController {
    // top level container
    @FXML
    public GridPane overview;

    // calendar view on the
    @FXML
    public BorderPane calendarPane;
    @FXML
    public Label monthAndYearLbl;
    @FXML
    public Button prevBtn;
    @FXML
    public Button nextBtn;

    @FXML
    public GridPane weeklyView;
    @FXML
    public TableColumn weekDayCol1;
    @FXML
    public TableColumn weekDayCol2;
    @FXML
    public TableColumn weekDayCol3;
    @FXML
    public TableColumn weekDayCol4;
    @FXML
    public TableColumn weekDayCol5;
    @FXML
    public TableColumn weekDayCol6;
    @FXML
    public TableColumn weekDayCol7;
    @FXML
    public BorderPane calendarMonthView;
    public ButtonBar prevNextBtnBar;

    @FXML
    public ColumnConstraints hours;
    @FXML
    public ColumnConstraints day1;
    @FXML
    public ColumnConstraints day2;
    @FXML
    public ColumnConstraints day3;
    @FXML
    public ColumnConstraints day4;
    @FXML
    public ColumnConstraints day5;
    @FXML
    public ColumnConstraints day6;
    @FXML
    public ColumnConstraints day7;



    public OverviewController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        setOverview();
    }

    CalendarMonthController calendarController = new CalendarMonthController();


    public GridPane getOverview() {
        return overview;
    }

    public void setOverview() throws IOException, ParseException, SQLException, ClassNotFoundException {
        overview.getChildren().get(0).maxWidth(100);
        overview.getChildren().get(1).translateXProperty().setValue(-75);
        calendarMonthView.setMaxWidth(200);
        calendarMonthView.prefHeightProperty().bind(calendarPane.heightProperty());
        calendarMonthView.prefWidthProperty().bind(calendarPane.widthProperty());

        /**
         * Appointment View
         */
//        AppointmentVBox appointmentBox = new AppointmentVBox();
//        appointmentBox.getAppointmentBox();
//        appointmentBox.getChildren().add(appointmentBox.getChildren().size(), new Button("Add Appointment"));
//        calendarMonthView.setBottom(appointmentBox);

        /**
         * Week View
         */

        hours.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.09));
        day1.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day3.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day4.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day5.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day6.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day7.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        addCurrentHours();


        int numCols = weeklyView.getColumnCount(); //offset hours col
        int numRows = weeklyView.getRowCount();

        for (int i = 1 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            weeklyView.getColumnConstraints().add(colConstraints);
        }

        for (int i = 2 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            weeklyView.getRowConstraints().add(rowConstraints);
        }

        for (int i = 1 ; i < numCols ; i++) {
            for (int j = 2; j < numRows; j++) {
                VBox addBox = new VBox();
                addBox.getChildren().addAll(new Label("test"));
                addBox.setMinSize(150, 85);
                addBox.setAlignment(Pos.CENTER);
                BorderStroke leftBorder = new BorderStroke(Color.WHITE, Color.LIGHTGRAY, Color.LIGHTGRAY, Color.WHITE,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY);
                BorderStroke rightBorder = new BorderStroke(Color.WHITE, Color.WHITE, Color.LIGHTGRAY, Color.WHITE,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY);
                if(i == numCols -1) {
                    addBox.setBorder(new Border(rightBorder));
                } else {
                    addBox.setBorder(new Border(leftBorder));
                }

                addBox.setOnMouseClicked(mouseEvent -> onMouseClickedEmptyAppointmentPopup(mouseEvent, addBox));
                weeklyView.add(addBox, i, j);
            }
        }

        // create day labels
        CalendarMonthController calendarController = new CalendarMonthController();
        int today = calendarController.c.getToday();
        String currentName;
//        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());

        for (int i = 1; i < 8; i++) {
            // date label
            int firstDayOfWeek = calendarController.c.getCalendar().getFirstDayOfWeek();
            // set day names
            String[] dayNames = DateTimeUtils.getDaysOfWeek(firstDayOfWeek);
            Text dateText = new Text(String.valueOf(firstDayOfWeek + i));
            dateText.setFill(Color.WHITE);
            dateText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            Circle dateCircle = new Circle(30);
            Color orange = Color.web("#ebaa5d",1 );
//            -fx-background-color: radial-gradient(radius 75%, #ebaa5d, #38909b);
            Color blue = Color.web("#38909b");
            dateCircle.setFill(blue);
            StackPane dateStack = new StackPane();
            dateStack.getChildren().addAll(dateCircle, dateText);
            dateStack.alignmentProperty().setValue(Pos.CENTER);
            dateStack.translateXProperty().setValue(-15);
            dateStack.paddingProperty().setValue(new Insets(20, 10, 20, 10));

            // weekday name label
            Label nameLbl = new Label(dayNames[i]);
            nameLbl.alignmentProperty().setValue(Pos.CENTER);
            nameLbl.translateXProperty().setValue(50);
            nameLbl.setFont(new Font(20));
            nameLbl.paddingProperty().setValue(new Insets(0, 0, 10, 0));

            // add both
            weeklyView.add(dateStack, i, 0);
            weeklyView.add(nameLbl, i, 1);

//            weeklyView.add(addAppointment(), 5, 3);
        }

        // create separator
//        Separator horSep = new Separator();
//        horSep.setMaxWidth(day1.getPrefWidth() + 100);
//        horSep.setHalignment(HPos.CENTER);
//        horSep.setPadding(new Insets(10, 10, 10, 10));
//        weeklyView.add(horSep, 0, 2);
//
//        // Next Appointment
//        VBox apptBox2 = new VBox(5);
//        Label custTest = new Label(CustomerDao.getAllCustomers().get(1).getAddress());
//        weeklyView.add(custTest, 0, 2);
//        weeklyView.add(new AppointmentVBox().getAppointmentBox(), 1, 2);

    }

    private void addCurrentHours() {
        // get current time - use for onLoad
        Calendar calendar = calendarController.c.getCalendar();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int rowIndex = 2;
        boolean switchTime = false;
        // go til 11:59PM
        int i = 6; // excludes 1am - 5am
        while(i <= 13) {
            // AM or PM
            String AM_PM;
            // if i == 12 then PM
            if(i == 13) {
                i =1;
            }

            if(i==12 && switchTime) {
                break;
            }

            if(i ==12) {
                switchTime = true;
            }

            if(switchTime){
                AM_PM = " PM";
            } else {
                AM_PM = " AM";
            }

            // create Label
            Label currentHourLbl = new Label(i + AM_PM);
            currentHourLbl.setStyle("-font-color: GREY");
            currentHourLbl.setPadding(new Insets(50, 10, 0, 50));
            weeklyView.add(currentHourLbl, 0, rowIndex);
            rowIndex++;
            i++;
        }
    }

    private VBox addAppointment() throws ParseException, SQLException, ClassNotFoundException {
//        AppointmentDao.addAppointment(20, 1, 1, "Vbox Test", "test", "", "555-5555", "Video Call", "...", "NOW()", "NOW()", "test", "NOW()", "test");
        //        weeklyView.add(new AppointmentVBox().getAppointmentById(1), 1, 4);

        // popup
        final Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);


        // appointment
        AppointmentVBox appointment = new AppointmentVBox();
        appointment.getAppointmentById(1);
        popup.getContent().addAll(appointment);
        appointment.setOnMouseClicked(mouseEvent -> onMouseClickedAppointmentPopup(appointment));
        return appointment;
    }

    // popups
    public static void onMouseClickedEmptyAppointmentPopup(Event event, VBox vbox) {
        // use for getting current date and time
        Node source = (Node)event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add Appointment");

        AppointmentVBox appt = new AppointmentVBox();


        Button closeWindow = new Button("Close this pop up window");

        // TODO edit appointment and update database
        // type
        Label typeLbl = new Label("Type");
        TextField typeTxt = new TextField("What type of appointment is this?");
        // TODO add to appointment
        HBox typeHBox = new HBox();
        typeHBox.getChildren().addAll(typeLbl, typeTxt);

        // Contact
        Label contactLbl = new Label("Contact");
        TextField contactTxt = new TextField("Who is this appointment for?");
        HBox contactHBox = new HBox();
        typeHBox.getChildren().addAll(contactLbl, contactTxt);

        // Date and Time
        // TODO get MM-DD-YYYY based on grid pane location
        Label startDateLbl = new Label("Start Time");
        TextField startDateTxt = new TextField("What time does this appointment start?");
        HBox startDateHBox = new HBox();
        typeHBox.getChildren().addAll(startDateLbl, startDateTxt);



        closeWindow.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox();


        layout.getChildren().addAll(typeHBox, contactHBox, startDateHBox, closeWindow);
        layout.setFillWidth(false);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 750, 750);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }
    public static void onMouseClickedAppointmentPopup(AppointmentVBox vbox) {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Edit Appointment");



        Button closeWindow = new Button("Close this pop up window");

        // TODO edit appointment and update database
        // type
        Label typeLbl = new Label("Type");
        TextField typeTxt = new TextField(vbox.getTypeTxt().getText());
        HBox typeHBox = new HBox();
        typeHBox.getChildren().addAll(typeLbl, typeTxt);

        // Contact
        Label contactLbl = new Label("Contact");
        TextField contactTxt = new TextField(vbox.getContactTxt().getText());
        HBox contactHBox = new HBox();
        typeHBox.getChildren().addAll(contactLbl, contactTxt);

        // Date and Time
        Label startDateLbl = new Label("Start Date");
        TextField startDateTxt = new TextField(vbox.getStartTimeLbl().getText());
        HBox startDateHBox = new HBox();
        typeHBox.getChildren().addAll(startDateLbl, startDateTxt);



        closeWindow.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox();


        layout.getChildren().addAll(typeHBox, contactHBox, startDateHBox, closeWindow);
        layout.setFillWidth(false);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 750, 750);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();
    }
        public void handleOnMouseClicked(MouseEvent event) {
//        VBox fxmlContainer  = new VBox();
//        fxmlContainer.setStyle("-fx-background-color: ANTIQUEWHITE");
//        fxmlContainer.getChildren().add(new Label("Click Anywhere!!"));
//        fxmlContainer.setAlignment(Pos.CENTER);

//        StackPane stackPane = new StackPane(fxmlContainer);

//        fxmlContainer.setOnMouseClicked(event -> System.out.println("You have clicked on VBox"));
//        stackPane.setOnMouseClicked(event -> System.out.println("You have clicked on StackPane"));
//
//        Scene scene = new Scene(stackPane, 150, 150);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void onActionGetPrevMonth(ActionEvent actionEvent) {
    }

    public void onActionGetNextMonth(ActionEvent actionEvent) {
    }
}
