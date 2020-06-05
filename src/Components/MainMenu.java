package Components;

import Components.Appointments.AppointmentsTable;
import Components.Calendar.CalendarView;
import Components.Customer.CustomersTable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.awt.*;

public class MainMenu {
    Main main;
    GridPane mainView;

    // main menu
    public MainMenu(Main main, GridPane mainView) throws Exception {
        this.main = main;
        this.mainView = mainView;
        buildMainMenu();
    }

    private HBox mainMenu = new HBox(0);

    // calendar section
    private VBox vBoxCalendar = new VBox(0);
    private Label lblCalendar = new Label("CALENDAR");
    private Label lblMonth = new Label("My Month");
    private Label lblWeek = new Label("My Week");

    // appointment section
    private VBox vBoxAppointments = new VBox(0);
    private Label lblAppointments = new Label("APPOINTMENTS");
    private Label lblManageAppointments = new Label("Manage Components.Appointments");
    private Label lblAddNewAppointment = new Label("New Appointment");

    // customers section
    private VBox vBoxCustomers = new VBox(0);
    private VBox customerMenu;
    private Label lblCustomers = new Label("CUSTOMERS");
    private Label lblManageCustomers = new Label("Manage Customers");
    private Label lblAddNewCustomer = new Label("New Customer");

    // reports section
    private VBox vBoxReports = new VBox(0);
    private Label lblReports = new Label("REPORTS");
    Label reportOne = new Label("Report One");
    Label reportTwo = new Label("Report Two");
    Label reportThree = new Label("Report Three");


    //Label styling
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int twentyFivePercentScreenWidth = (screenSize.width/4)-2;
    private String mainLblsStyle() {
        return "-fx-font-family: 'Roboto Bold'; " +
                "-fx-font-size: 18; " +
                "-fx-alignment: center;" +
                "-fx-font-weight: BOLD;" +
                "-fx-padding: 25 20;" +
                "-fx-background-color:#ebaa5d;" +
                "-fx-pref-width: "+ twentyFivePercentScreenWidth + ";" +
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
                "-fx-alignment: CENTER;" +
                "-fx-pref-width: 300;" +
                "-fx-pref-height: 300;" +
                "-fx-padding: 25 5;";
    };

    private void buildMainMenu() throws Exception {
        mainMenu.getStylesheets().add("CSS/mainMenu.css");
        mainMenu.getStyleClass().add("hbox");


        // CALENDAR
        lblCalendar.setStyle(mainLblsStyle());
        lblCalendar.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new CalendarView(main).getView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // APPOINTMENTS
        lblAppointments.setStyle(mainLblsStyle());
        lblAppointments.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new AppointmentsTable(main).getAnchorPane());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // REPORTS
        lblReports.setStyle(mainLblsStyle());


        // CUSTOMERS
        lblCustomers.setStyle(mainLblsStyle());
        lblCustomers.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new CustomersTable().getView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mainMenu.getChildren().addAll(
                lblCalendar,
                lblAppointments,
                lblCustomers,
                lblReports
        );
    };

    public HBox getView() {
        return mainMenu;
    }

    private void updateMainView(Node node) {
        mainView.getChildren().remove(1);
        mainView.add(node, 0, 1);
    }
}
