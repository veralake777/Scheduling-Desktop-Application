package iluwatar;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {
    // main menu
    public MainMenu() {
        buildMainMenu();
    }
    public VBox vBox = new VBox(0);
    private boolean isShowing;

    private Text downArrow = new Text("v");


    // calendar section
    private VBox vBoxCalendar = new VBox(10);
    private Label lblCalendar = new Label("CALENDAR           v");
    private Label lblMonth = new Label("My Month");
    private Label lblWeek = new Label("My Week");

    // appointment section
    private VBox vBoxAppointments = new VBox(10);
    private Label lblAppointments = new Label("APPOINTMENTS  v");
    private Label lblManageAppointments = new Label("Manage Appointments");
    private Label lblAddNewAppointment = new Label("New Appointment");

    // customers section
    private VBox vBoxCustomers = new VBox(10);
    private Label lblCustomers = new Label("CUSTOMERS        v");
    private Label lblManageCustomers = new Label("Manage Customers");
    private Label lblAddNewCustomer = new Label("New Customer");

    // reports section
    private Label lblReports = new Label("REPORTS");


    //Label styling
    private String mainLblsStyle() {
        return "-fx-font-family: 'Roboto Bold'; " +
                "-fx-font-size: 18; " +
                "-fx-font-weight: BOLD;" +
                "-fx-padding: 25 20;" +
                "-fx-background-color:#ebaa5d;" +
                "-fx-pref-width: 210;" +
                "-fx-border-radius: 50;";
    };

    private String subLblListStyle(){
        return "-fx-font-family: 'Roboto Light'; " +
                "-fx-font-size: 16; " +
                "-fx-padding: 10 10";
    };

    private void buildMainMenu() {
        vBoxCalendar.getChildren().add(lblCalendar);
        vBoxAppointments.getChildren().add(lblAppointments);
        vBoxCustomers.getChildren().add(lblCustomers);

        vBoxCalendar.onMouseClickedProperty().set(event -> {
            Label[] lblList = new Label[]{
                    lblMonth,
                    lblWeek
            };
            onClickDropDown(vBoxCalendar, lblList);
        });

        vBoxAppointments.onMouseClickedProperty().set(event -> {
            Label[] lblList = new Label[]{
                    lblManageAppointments,
                    lblAddNewAppointment
            };
            onClickDropDown(vBoxAppointments, lblList);
        });

        vBoxCustomers.onMouseClickedProperty().set(event -> {
            Label[] lblList = new Label[]{
                    lblManageCustomers,
                    lblAddNewCustomer
            };
            onClickDropDown(vBoxCustomers, lblList);
        });

        // set styling
        lblCalendar.setStyle(mainLblsStyle());
        lblAppointments.setStyle(mainLblsStyle());
        lblCustomers.setStyle(mainLblsStyle());
        lblReports.setStyle(mainLblsStyle());

        vBox.getChildren().addAll(
                new Label("MAIN MENU"),
                vBoxCalendar,
                vBoxAppointments,
                vBoxCustomers,
                lblReports
        );
    };

    private void onClickDropDown(VBox vBox, Label[] lblList) {
        vBox.onMouseClickedProperty().set(event -> onClickHideList(vBox, lblList));
        vBox.getChildren().addAll(lblList);
    }

    private void onClickHideList(VBox vBox, Label[] lblList) {
        vBox.getChildren().removeAll(lblList);
        vBox.onMouseClickedProperty().set(event -> onClickDropDown(vBox, lblList));
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
