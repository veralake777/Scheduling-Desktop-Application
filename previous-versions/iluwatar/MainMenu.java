package iluwatar;

import MVC.NextAppointment;
import iluwatar.DbDao.DbCustomerDao;
import iluwatar.DbDao.DbCustomerDetailsDao;
import iluwatar.POJO.Customer;
import iluwatar.POJO.CustomerDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Database.DBUtils;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class MainMenu {
    // main menu
    public MainMenu() throws Exception {
        buildMainMenu();
    }

    public HBox mainMenu = new HBox(0);

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
    private VBox customerMenu;
    private Label lblCustomers = new Label("CUSTOMERS");
    private Label lblManageCustomers = new Label("Manage Customers");
    private Label lblAddNewCustomer = new Label("New Customer");

    // reports section
    private VBox vBoxReports = new VBox(0);
    private Label lblReports = new Label("Reports");
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
        lblCalendar.setStyle(mainLblsStyle());
        lblCalendar.onMouseClickedProperty().set(mouseEvent -> {
            GridPane onLoad = new GridPane();
            ColumnConstraints columnConstraintsCalendar = new ColumnConstraints();
            columnConstraintsCalendar.setPercentWidth(20);
            ColumnConstraints columnConstraintsWeek = new ColumnConstraints();
            columnConstraintsWeek.setPercentWidth(80);
            BorderPane calendar = null;
            VBox firstColumn = new VBox(10);
            GridPane week = null;
            firstColumn.setFillWidth(true);
            try {
                calendar = FXMLLoader.load(getClass().getResource("previous-versions/MVC/view/calendarMonthView.fxml"));
                week = new WeekView().getWeekView();
                firstColumn.getChildren().addAll(calendar, new NextAppointment().getNextAppointmentVBox());
            } catch (Exception e) {
                e.printStackTrace();
            }

            onLoad.getColumnConstraints().addAll(columnConstraintsCalendar, columnConstraintsWeek);
            onLoad.addColumn(0, firstColumn);
            onLoad.addColumn(1, week);

            Main.loadDynamicView(onLoad);
        });

        lblAppointments.setStyle(mainLblsStyle());
        lblManageAppointments.setStyle(subLblListStyle());
        lblManageAppointments.onMouseClickedProperty().set(event -> onClickLoadView(new Text("MANAGE APPTS VIEW")));
        lblAddNewAppointment.setStyle(subLblListStyle());
        lblAddNewAppointment.onMouseClickedProperty().set(event -> onClickLoadView(new Text("ADD APPTS VIEW")));

        lblReports.setStyle(mainLblsStyle());
        reportOne.setStyle(subLblListStyle());
        reportTwo.setStyle(subLblListStyle());
        reportThree.setStyle(subLblListStyle());


        // slide out menu
        ObservableList<CustomerDetails> customers = FXCollections.observableArrayList();
        Stream<CustomerDetails> customerStream = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource()).getAll();

        // add all customers as objects to a list
        customerStream.forEach(customers::add);

        // vbox for slide out menu
        customerMenu = new VBox(5);
        customerMenu.setId("Customers");
        customerMenu.setPrefWidth(lblCustomers.getWidth());
        customerMenu.setStyle(subLblListStyle());

        HBox searchBar = new HBox(5);
        javafx.scene.control.Button newCustomer = new Button("NEW");
        newCustomer.onMouseClickedProperty().set(e-> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Could not connect to database.");
        alert.showAndWait();
        try {
            Stage newCustomerStage = new MyCustomersView().getNewCustomerCardScene();
            newCustomerStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
//        newCustomer.addEventHandler(e->System.out.println("new customer"));
        searchBar.getChildren().addAll(new Label("Search"), new TextField("by name, phone, address"), newCustomer);

        // add customer names to
        customers.forEach(customer -> {
            HBox customerInfo = new HBox(5);
            // EDIT CUSTOMER BUTTON
            Button editCustomer = new Button("Edit");
            editCustomer.onMouseClickedProperty().set(e->{
                int id = customer.getId();
                try {
                    DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                    Optional<Customer> customerToEdit = customerDao.getById(id);
                    if(customerToEdit.isPresent()) {
                        // Todo popup stage with edit customer info
                        Stage stage = new MyCustomersView().getEditCustomerCardStage(customer);
                        stage.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // DELETE CUSTOMER BUTTON
            Button deleteCustomer = new Button("Delete");
            deleteCustomer.onMouseClickedProperty().set(e->{
                int id = customer.getId();
                try {
                    DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
                    Optional<Customer> customerToEdit = customerDao.getById(id);
                    if(customerToEdit.isPresent()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Are you sure you want to PERMANENTLY delete the customer from the database?");
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> {
                                    try {
                                        customerDao.delete(customerToEdit.get());
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            customerInfo.setStyle(subLblListStyle());
            Label name = new Label(customer.getName());
            Label phone = new Label(customer.getPhone());
            customerInfo.getChildren().addAll(name, phone, editCustomer, deleteCustomer);
            customerMenu.getChildren().add(customerInfo);
        });
        customerStream.close();
        customerMenu.getChildren().add(searchBar);


        lblCustomers.setStyle(mainLblsStyle());
        lblManageCustomers.setStyle(subLblListStyle());
        lblManageCustomers.onMouseClickedProperty().set(event -> {
            try {
                onClickLoadView(new MyCustomersView().getGridPane());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        lblAddNewCustomer.setStyle(subLblListStyle());
        lblAddNewCustomer.onMouseClickedProperty().set(event -> {
            try {
                onClickLoadView(new AddCustomer().getAddCustomerGridPane());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Adding event Filter
//        lblCustomers.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEvent -> vBoxCustomers.getChildren().add(customerMenu));
        VBox appointmentSubMenu = new VBox(5);
        appointmentSubMenu.getChildren().addAll(lblAddNewAppointment, lblManageAppointments);
        appointmentSubMenu.setStyle(subLblListStyle());

        VBox calendarSubMenu = new VBox(5);
        calendarSubMenu.getChildren().addAll(lblCalendar, lblMonth, lblWeek);
        calendarSubMenu.setStyle(subLblListStyle());

        VBox reportsSubMenu = new VBox(5);
        reportsSubMenu.getChildren().addAll(reportOne, reportTwo, reportThree);
        reportsSubMenu.setStyle(subLblListStyle());

        AtomicBoolean isShowing = new AtomicBoolean(false);
        mainMenu.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if( !isShowing.get() ) {
                vBoxCalendar.getChildren().add(calendarSubMenu);
                vBoxAppointments.getChildren().add(appointmentSubMenu);
                vBoxCustomers.getChildren().add(customerMenu);
                vBoxReports.getChildren().add(reportsSubMenu);

                isShowing.set(true);
            }
            else {
                vBoxCalendar.getChildren().remove(calendarSubMenu);
                vBoxAppointments.getChildren().remove(appointmentSubMenu);
                vBoxCustomers.getChildren().remove(customerMenu);
                vBoxReports.getChildren().remove(reportsSubMenu);
                isShowing.set(false);
            }
        });

        vBoxCalendar.getChildren().addAll(lblCalendar);
        vBoxAppointments.getChildren().addAll(lblAppointments);
        vBoxCustomers.getChildren().addAll(lblCustomers);
        vBoxReports.getChildren().addAll(lblReports);

        Label lblTitle = new Label("MAIN MENU");
        lblTitle.setStyle(mainLblsStyle());

        mainMenu.getChildren().addAll(
                vBoxCalendar,
                vBoxAppointments,
                vBoxCustomers,
                vBoxReports
        );
    };

    private void onClickLoadView(Node view) {
        Main.loadDynamicView(view);
    }

    // Used in onUpdateCustomer button
    public void updateCustomerMenu() throws Exception {
        buildMainMenu();
//        this.customerMenu.getChildren().clear();
//        System.out.println("UPDATED BTN CLICKED");
//        Stream<CustomerDetails> customerStream = new DbCustomerDetailsDao(DBUtils.getMySQLDataSource()).getAll();
//        int customerIndex = 1;
//        customerStream.forEach(customer->{
//        HBox customerInfo = new HBox(5);
//        // EDIT CUSTOMER BUTTON
//        Button editCustomer = new Button("Edit");
//        editCustomer.onMouseClickedProperty().set(e->{
//            int id = customer.getId();
//            try {
//                DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
//                Optional<Customer> customerToEdit = customerDao.getById(id);
//                if(customerToEdit.isPresent()) {
//                    // Todo popup stage with edit customer info
//                    Stage stage = new MyCustomersView().getEditCustomerCardStage(customer);
//                    stage.showAndWait();
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.showAndWait()
//                            .filter(response -> response == ButtonType.OK)
//                            .ifPresent(response -> alert.close());
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        // DELETE CUSTOMER BUTTON
//        Button deleteCustomer = new Button("Delete");
//        deleteCustomer.onMouseClickedProperty().set(e->{
//            int id = customer.getId();
//            try {
//                DbCustomerDao customerDao = new DbCustomerDao(DBUtils.getMySQLDataSource());
//                Optional<Customer> customerToEdit = customerDao.getById(id);
//                if(customerToEdit.isPresent()) {
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setContentText("Are you sure you want to PERMANENTLY delete the customer from the database?");
//                    alert.showAndWait()
//                            .filter(response -> response == ButtonType.OK)
//                            .ifPresent(response -> {
//                                try {
//                                    customerDao.delete(customerToEdit.get());
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
//                            });
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.showAndWait()
//                            .filter(response -> response == ButtonType.OK)
//                            .ifPresent(response -> alert.close());
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
//        customerInfo.setStyle(subLblListStyle());
//        Label name = new Label(customer.getName());
//        Label phone = new Label(customer.getPhone());
//        customerInfo.getChildren().addAll(name, phone, editCustomer, deleteCustomer);
//        this.customerMenu.getChildren().add(customerInfo);
//    });
//        customerStream.close();
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
