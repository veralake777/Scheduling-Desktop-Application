package iluwatar;

import javafx.scene.Node;
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
    private VBox vBoxCalendar = new VBox(0);
    private Label lblCalendar = new Label("CALENDAR           v");
    private Label lblMonth = new Label("My Month");
    private Label lblWeek = new Label("My Week");

    // appointment section
    private VBox vBoxAppointments = new VBox(0);
    private Label lblAppointments = new Label("APPOINTMENTS  v");
    private Label lblManageAppointments = new Label("Manage Appointments");
    private Label lblAddNewAppointment = new Label("New Appointment");

    // customers section
    private VBox vBoxCustomers = new VBox(0);
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
        return "-fx-background-color: rgba(235, 170, 93, .25);" +
                "-fx-font-family: 'Roboto Light'; " +
                "-fx-font-size: 16; " +
                "-fx-padding: 10 10;" +
                "-fx-pref-width: 210;" +
                "-fx-padding: 25 20;";
    };

    private void buildMainMenu() {
//        vBox.setStyle("-fx-background-color: #ebaa5d;" +
//                "-fx-opacity: .5;");
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
        lblMonth.setStyle(subLblListStyle());
        lblMonth.onMouseClickedProperty().set(event -> onClickLoadView(new Text("MONTH VIEW")));
        lblWeek.setStyle(subLblListStyle());
        lblWeek.onMouseClickedProperty().set(event -> onClickLoadView(new WeekView().getWeekView()));

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

    private void onClickLoadView(Node view) {
        Main.loadView(view);
//        EventTarget target = event.getTarget();
//        if (!(target instanceof Node)) return;
//
//        final Node node = (Node) target;
//
//        if (!(node instanceof Text)) {
//            return;
//        }
//
//        final Parent label = node.getParent();
//
//        if (!(label instanceof Label)) {
//            return;
//        }
//
//        final Parent mainMenuVBox = label.getParent();
//
//        if (!(mainMenuVBox instanceof VBox)) {
//            return;
//        }
//
//        final Parent next = mainMenuVBox.getParent();
//
//        final Parent next2 = next.getParent();
//
////        if (!gridPane.getStyleClass().contains("tab-container")) {
////            return;
////        }
////
////        if (isChangingTab()) {
////            setChangingTab(false);
////            return;
////        }
////
////        processExpandOrCollapse();
//        System.out.println(next2.getParent().getChildrenUnmodifiable().set(1, new Text("text")));
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
