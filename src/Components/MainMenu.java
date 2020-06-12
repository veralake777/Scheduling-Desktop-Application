package Components;

import Components.Appointments.AppointmentsTable;
import Components.Calendar.CalendarView;
import Components.Customer.CustomersTable;
import Components.Reports.Reports;
import POJO.User;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.awt.*;

public class MainMenu {
    User user;
    GridPane mainView;

    // main menu
    public MainMenu(User user, GridPane mainView) throws Exception {
        this.user = user;
        this.mainView = mainView;
        buildMainMenu();
    }

    private HBox mainMenu = new HBox(0);

    // calendar section
    private Label lblCalendar = new Label("CALENDAR");

    // appointment section
    private Label lblAppointments = new Label("APPOINTMENTS");

    // customers section
    private Label lblCustomers = new Label("CUSTOMERS");

    // reports section
    private Label lblReports = new Label("REPORTS");


    //Label styling
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int twentyFivePercentScreenWidth = (screenSize.width / 4) - 2;

    private String mainLabelsStyle() {
        return "-fx-font-family: 'Roboto Bold'; " +
                "-fx-font-size: 18; " +
                "-fx-alignment: center;" +
                "-fx-font-weight: BOLD;" +
                "-fx-padding: 25 20;" +
                "-fx-background-color:#ebaa5d;" +
                "-fx-pref-width: " + twentyFivePercentScreenWidth + ";" +
                "-fx-border-radius: 50;" +
                "-fx-border-style: none none solid none;" +
                "-fx-border-width: 10;" +
                "-fx-border-color: WHITE;";
    }

    private void buildMainMenu() {
        mainMenu.getStylesheets().add("CSS/mainMenu.css");
        mainMenu.getStyleClass().add("hbox");
        mainMenu.setMaxWidth(Screen.getPrimary().getBounds().getWidth() - 15);


        // CALENDAR
        lblCalendar.setStyle(mainLabelsStyle());
        lblCalendar.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new CalendarView(user).getView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // APPOINTMENTS
        lblAppointments.setStyle(mainLabelsStyle());
        lblAppointments.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new AppointmentsTable(user).getGridPane());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // REPORTS
        lblReports.setStyle(mainLabelsStyle());
        lblReports.onMouseClickedProperty().set(mouseEvent -> {
            try {
                updateMainView(new Reports(user).getView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // CUSTOMERS
        lblCustomers.setStyle(mainLabelsStyle());
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
    }

    public HBox getView() {
        return mainMenu;
    }

    private void updateMainView(Node node) {
        double maxHeight = Screen.getPrimary().getVisualBounds().getHeight() - 175;
        mainView.getChildren().remove(1);
        node.setStyle("-fx-pref-width: 450;" +
                "-fx-max-height: " + maxHeight + ";");
        mainView.add(node, 0, 1, 2, 1);
    }
}
