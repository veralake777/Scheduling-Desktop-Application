package iluwatar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainMenu {
    // main menu
    public MainMenu() throws IOException {
        buildMainMenu();
    }

    public VBox vBox = new VBox(0);

    // calendar section
    private VBox vBoxCalendar = new VBox(0);
    private Label lblCalendar = new Label("CALENDAR");
    private Label lblMonth = new Label("My Month");
    private Label lblWeek = new Label("My Week");

    // appointment section
    private VBox vBoxAppointments = new VBox(0);
    private Label lblAppointments = new Label("APPOINTMENTS");
    private Label lblManageAppointments = new Label("Manage Appointments");
    private Label lblAddNewAppointment = new Label("New Appointment");

    // customers section
    private VBox vBoxCustomers = new VBox(0);
    private Label lblCustomers = new Label("CUSTOMERS");
    private Label lblManageCustomers = new Label("Manage Customers");
    private Label lblAddNewCustomer = new Label("New Customer");

    // reports section
    private Label lblReports = new Label("REPORTS");


    //Label styling
    private String mainLblsStyle() {
        return "-fx-font-family: 'Roboto Bold'; " +
                "-fx-font-size: 18; " +
                "-fx-alignment: center;" +
                "-fx-font-weight: BOLD;" +
                "-fx-padding: 25 20;" +
                "-fx-background-color:#ebaa5d;" +
                "-fx-pref-width: 300;" +
                "-fx-border-radius: 50;" +
                "-fx-border-style: none none solid none;" +
                "-fx-border-width: 10;" +
                "-fx-border-color: WHITE;";
    };

    private String subLblListStyle(){
        return "-fx-background-color: rgba(235, 170, 93, .25);" +
                "-fx-font-family: 'Roboto Bold'; " +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 16; " +
                "-fx-alignment: center;" +
                "-fx-pref-width: 300;" +
                "-fx-padding: 25 0;";
    };

    private void buildMainMenu() throws IOException {
//        vBox.setStyle("-fx-background-color: #ebaa5d;" +
//                "-fx-opacity: .5;");
        vBox.setFillWidth(true);
//        vBoxCalendar.getChildren().add(lblCalendar);
//        vBoxAppointments.getChildren().add(lblAppointments);
//        vBoxCustomers.getChildren().add(lblCustomers);

//        vBoxCalendar.onMouseClickedProperty().set(event -> {
//            Label[] lblList = new Label[]{
//                    lblMonth,
//                    lblWeek
//            };
//            onClickDropDown(vBoxCalendar, lblList);
//        });
//
//        vBoxAppointments.onMouseClickedProperty().set(event -> {
//            Label[] lblList = new Label[]{
//                    lblManageAppointments,
//                    lblAddNewAppointment
//            };
//            onClickDropDown(vBoxAppointments, lblList);
//        });
//
//        vBoxCustomers.onMouseClickedProperty().set(event -> {
//            Label[] lblList = new Label[]{
//                    lblManageCustomers,
//                    lblAddNewCustomer
//            };
//            onClickDropDown(vBoxCustomers, lblList);
//        });

        // set styling
//        Parent root = FXMLLoader.load(getClass().getResource("../view/loginScreenView.fxml"));
//        root.getStylesheets().addAll("/CSS/calendarPane.css", "/CSS/loginStyle.css", "/resources/app.css");
        lblCalendar.setStyle(mainLblsStyle());
        lblMonth.setStyle(subLblListStyle());
        lblMonth.onMouseClickedProperty().set(event -> {
            try {
                BorderPane calendarMonth = FXMLLoader.load(getClass().getResource("../MVC/view/calendarMonthView.fxml"));
                onClickLoadView(calendarMonth);
            } catch (IOException e) {
                System.out.println("CLASS CALENDAR VIEW IOEXCEPTION");
                e.printStackTrace();
            }
        });
        lblWeek.setStyle(subLblListStyle());
        lblWeek.onMouseClickedProperty().set(event -> {
            try {
                ScrollPane week = FXMLLoader.load(getClass().getResource("../MVC/view/weekView.fxml"));
                onClickLoadView(week);
            } catch (IOException e) {
                System.out.println("CLASS WEEK VIEW IOEXCEPTION");
                e.printStackTrace();
            }
        });

//        lblWeek.onMouseClickedProperty().set(event -> onClickLoadView(new WeekView().getWeekView()));

        lblAppointments.setStyle(mainLblsStyle());
        lblManageAppointments.setStyle(subLblListStyle());
        lblManageAppointments.onMouseClickedProperty().set(event -> onClickLoadView(new Text("MANAGE APPTS VIEW")));
        lblAddNewAppointment.setStyle(subLblListStyle());
        lblAddNewAppointment.onMouseClickedProperty().set(event -> onClickLoadView(new Text("ADD APPTS VIEW")));

        lblCustomers.setStyle(mainLblsStyle());
        lblManageCustomers.setStyle(subLblListStyle());
        lblManageCustomers.onMouseClickedProperty().set(event -> onClickLoadView(new Text("MANAGE CUSTOMERS VIEW")));
        lblAddNewCustomer.setStyle(subLblListStyle());
        lblAddNewCustomer.onMouseClickedProperty().set(event -> onClickLoadView(new Text("ADD CUSTOMERS VIEW")));

        vBoxCalendar.getChildren().addAll(lblCalendar, lblMonth, lblWeek);
        vBoxAppointments.getChildren().addAll(lblAppointments, lblManageAppointments, lblAddNewAppointment);
        vBoxCustomers.getChildren().addAll(lblCustomers, lblManageCustomers, lblAddNewCustomer);

        lblReports.setStyle(mainLblsStyle());
        Label lblTitle = new Label("MAIN MENU");
        lblTitle.setStyle(mainLblsStyle());

        vBox.getChildren().addAll(
                vBoxCalendar,
                vBoxAppointments,
                vBoxCustomers,
                lblReports
        );

    };

//    private void onClickDropDown(VBox vBox, Label[] lblList) {
//        vBox.onMouseClickedProperty().set(event -> onClickHideList(vBox, lblList));
//        vBox.getChildren().addAll(lblList);
//    }
//
//    private void onClickHideList(VBox vBox, Label[] lblList) {
//        vBox.getChildren().removeAll(lblList);
//        vBox.onMouseClickedProperty().set(event -> onClickDropDown(vBox, lblList));
//    }

    private void onClickLoadView(Node view) {
        Main.loadView(view);
    }

    // Calendar - onClick dropDown
        //month - onClick loadMonthView
        //week - onClick loadWeekView

    //Appointments - onClick dropDown
        //manage - onClick loadModifyAppointment
        //add new - onClick loadAddNewAppointment

    //Customers
        //modify - onClick loadModifyCustomers
        //add new - onClick loadAddCustomer

    //Reports - onClick loadReports
        // number of appointment types by month
        // the schedule for each consultant
        // one additional report of your choice

}
