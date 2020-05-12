package MVC.controller;

import DAO.POJO.Appointment;
import MVC.view.AppointmentVBox;
import PresentationState.Appointment.AddAppointment.AddAppointmentController;
import PresentationState.Appointment.AddAppointment.AddAppointmentGUIBinder;
import PresentationState.Appointment.AddAppointment.AddAppointmentPresentationState;
import PresentationState.Appointment.JavaFxApplications;
import iluwatar.DbDao.DbAppointmentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import utils.Database.DBUtils;
import utils.DateTime.DateTimeUtils;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Optional;

import static PresentationState.Appointment.AddAppointment.AddAppointmentConfig.*;

public class WeekViewController {
    @FXML
    public GridPane gridPaneWeekView;
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
    public ScrollPane scrollPaneWeekView;


    public WeekViewController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        buildWeekView();
    }

    CalendarMonthController calendarController = new CalendarMonthController();

    public void buildWeekView() throws Exception {
        /**
         * ScrollPane
         */
//        scrollPaneWeekView.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*85);
        scrollPaneWeekView.setFitToHeight(true);
        /**
         * Grid Pane
         */
        hours.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.09));
        day1.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day2.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day3.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day4.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day5.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day6.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        day7.prefWidthProperty().bind(gridPaneWeekView.widthProperty().multiply(.135));
        addCurrentHours();


        int numCols = gridPaneWeekView.getColumnCount(); //offset hours col
        int numRows = gridPaneWeekView.getRowCount();

        for (int i = 1 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            gridPaneWeekView.getColumnConstraints().add(colConstraints);
        }

        for (int i = 2 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPaneWeekView.getRowConstraints().add(rowConstraints);
        }

        for (int i = 1 ; i < numCols ; i++) {
            for (int j = 2; j < numRows; j++) {
                VBox addBox = new VBox();
                Label addLabel = new Label("Add");

                addBox.getChildren().addAll(addLabel);
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

//                addBox.setOnMouseClicked(mouseEvent -> onMouseClickedEmptyAppointmentPopup(mouseEvent, addBox));
                addBox.onMouseClickedProperty().set(mouseEvent -> {
                    addBox.getChildren().clear();
                    Stage popup=new Stage();

                    popup.initModality(Modality.APPLICATION_MODAL);
                    popup.setTitle("Add Appointment");


                    Label label1= new Label("Add new appointment.");
                    Button closeBtn= new Button("Close this pop up window");
                    Button addBtn = new Button("Add");
                    closeBtn.setOnAction(e -> popup.close());


                    VBox layout= new VBox(10);
                    layout.getChildren().addAll(label1, closeBtn, addBtn);
                    layout.setAlignment(Pos.CENTER);


                    FXMLLoader loader = new FXMLLoader(JavaFxApplications.fxmlUrl(FXML_URL), JavaFxApplications.resources(RESOURCE_BUNDLE_NAME));
                    FXMLLoader loader1 = new FXMLLoader(JavaFxApplications.fxmlUrl(FXML_URL), JavaFxApplications.resources(RESOURCE_BUNDLE_NAME));
                    try {
                        loader.load();
                        new AddAppointmentGUIBinder(loader.getController(), new AddAppointmentPresentationState()).bindAndInitialize();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(loader.getRoot());
                    scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());

//                    Scene scene1= new Scene(PresentationState.Appointment., 300, 250);
                    popup.setScene(scene);
                    popup.setTitle(loader.getResources().getString("appointmentTitle"));
                    popup.showAndWait();
                });
                gridPaneWeekView.add(addBox, i, j);
            }

            /**
             * Appointment View
             */
            Optional<iluwatar.POJO.Appointment> appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(1);

            VBox apptBox = new VBox();
            apptBox.getChildren().addAll(new Label(appointment.get().getType()));
            apptBox.setId(String.valueOf(appointment.get().getId()));
            apptBox.setOnMouseClicked(mouseEvent -> {
//                try {
//                    UpdateAppointmentController updateAppointmentController = new UpdateAppointmentController();
//                    updateAppointmentController.setAppointment(appointment.get());
//                    updateAppointmentController.openUpdateAppointment(mouseEvent, appointment);

//                } catch (IOException | ClassNotFoundException | SQLException | ParseException e) {
//                    e.printStackTrace();
//                }
            });

            gridPaneWeekView.add(apptBox, 3, 5 );
        }

        // create day labels
//        CalendarMonthController calendarController = new CalendarMonthController();
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
//            dateStack.translateXProperty().setValue();
            dateStack.paddingProperty().setValue(new Insets(20, 10, 20, 20));

            // weekday name label
            Label nameLbl = new Label(dayNames[i]);
            nameLbl.alignmentProperty().setValue(Pos.CENTER);
            nameLbl.translateXProperty().setValue(50);
            nameLbl.setFont(new Font(20));
            nameLbl.paddingProperty().setValue(new Insets(10, 10, 10, 10));

            // add both
            gridPaneWeekView.add(dateStack, i, 0);
            gridPaneWeekView.add(nameLbl, i, 1);

//            weeklyView.add(addAppointment(), 5, 3);
        }
    }

    private void addCurrentHours() throws ParseException, SQLException, ClassNotFoundException, IOException {
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
            currentHourLbl.setPadding(new Insets(50, 10, 0, 25));
            gridPaneWeekView.add(currentHourLbl, 0, rowIndex);
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
//        appointment.setOnMouseClicked(mouseEvent -> onMouseClickedAppointmentPopup());
        return appointment;
    }

    // popups
    public static void onMouseClickedEmptyAppointmentPopup(MouseEvent event, Appointment appointment) throws IOException, ParseException, SQLException, ClassNotFoundException {
        // use for getting current date and time
//        Node source = (Node)event.getSource();
//        Integer colIndex = GridPane.getColumnIndex(source);
//        Integer rowIndex = GridPane.getRowIndex(source);

//        FXMLLoader loader = new FXMLLoader();
//        URL location = OverviewController.class.getResource("../view/updateAppointment.fxml");
//        loader.setLocation(location);
//        loader.load();
//
//
//        UpdateAppointmentController updateAppointmentController = loader.getController();
//        updateAppointmentController.recieveAppointment(appointment);

        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((VBox)event.getSource()).getScene().getWindow();

        // load add part view
        FXMLLoader loader = FXMLLoader.load(WeekViewController.class.getResource("../view/addAppointment.fxml"));
//        UpdateAppointmentController controller = loader.getController();
//        controller.receiveAppointment(appointment);
        loader.getController();
        scene = loader.load();

        // add scene to stage
        stage.setScene(new Scene(scene));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Appointment");

        Button closeWindow = new Button("Close this pop up window");

//        // TODO edit appointment and update database
//        // type
//        Label typeLbl = new Label("Type");
//        TextField typeTxt = new TextField("What type of appointment is this?");
//        // TODO add to appointment
//        HBox typeHBox = new HBox();
//        typeHBox.getChildren().addAll(typeLbl, typeTxt);
//
//        // Contact
//        Label contactLbl = new Label("Contact");
//        TextField contactTxt = new TextField("Who is this appointment for?");
//        HBox contactHBox = new HBox();
//        typeHBox.getChildren().addAll(contactLbl, contactTxt);
//
//        // Date and Time
//        // TODO get MM-DD-YYYY based on grid pane location
//        Label startDateLbl = new Label("Start Time");
//        TextField startDateTxt = new TextField("What time does this appointment start?");
//        HBox startDateHBox = new HBox();
//        typeHBox.getChildren().addAll(startDateLbl, startDateTxt);



        closeWindow.setOnAction(e -> stage.close());


//        VBox layout = new VBox();
//
//
//
//        layout.getChildren().addAll(typeHBox, contactHBox, startDateHBox, closeWindow);
//        layout.setFillWidth(false);
//        layout.setAlignment(Pos.CENTER);

//        Scene scene1 = new Scene(updateAppointmentController.getScene(), 750, 750);

//        popupwindow.setScene(scene1);

        stage.showAndWait();

    }

    public void onMouseClickedAppointmentPopup(Appointment a) throws IOException, ParseException, SQLException, ClassNotFoundException {
        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/updateAppointment.fxml"));
        // get controller
        AddAppointmentController controller = new AddAppointmentController();
        // set appointment details


        // set appointment details
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle("Add Appointment");
        stage.resizableProperty().setValue(false);
//        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
//        if (updateAppointmentController.isSuccess())
//        {
//            Account newAccount = accountControl.getAccount();
//            return newAccount;
//        } else
//            throw new Exception("User quit.");

        //        Stage popupWindow = new Stage();
//        popupWindow.initModality(Modality.APPLICATION_MODAL);
//        popupWindow.setTitle("Edit Appointment");
//
//        Button closeWindow = new Button("Close this pop up window");
//
//        Stage stage;
//        Parent scene;
//        // return to main menu
//        // build stage
//        stage = (Stage)((VBox)event.getSource()).getScene().getWindow();
//        GridPane test = new GridPane();
//        Label type = new Label(a.getType());
//        test.getChildren().addAll(type);
//
//
//        // load add part view
////        FXMLLoader loader = FXMLLoader.load(getClass().getResource("../view/updateAppointment.fxml"));
////        UpdateAppointmentController controller = loader.getController();
////        controller.recieveAppointment(a);
////        scene = loader.getRoot();
//
//        // add scene to stage
//        stage.setScene(new Scene(test));
////        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Add Appointment");
//
//        closeWindow.setOnAction(e -> popupWindow.close());
//
////        VBox layout = new VBox();
////
////
////        layout.getChildren().addAll(closeWindow);
////        layout.setFillWidth(false);
////        layout.setAlignment(Pos.CENTER);
//
//        stage.showAndWait();
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
