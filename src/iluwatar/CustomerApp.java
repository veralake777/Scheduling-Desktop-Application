package iluwatar;

import iluwatar.DbDao.DbCustomerDao;
import iluwatar.InMemory.InMemoryCustomerDao;
import iluwatar.Interface.CustomerDao;
import iluwatar.POJO.Customer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utils.Database.DBUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Data Access Object (DAO) is an object that provides an abstract interface to some type of
 * database or other persistence mechanism. By mapping application calls to the persistence layer,
 * DAO provide some specific data operations without exposing details of the database. This
 * isolation supports the Single responsibility principle. It separates what data accesses the
 * application needs, in terms of domain-specific objects and data types (the public interface of
 * the DAO), from how these needs can be satisfied with a specific DBMS.
 *
 * <p>With the DAO pattern, we can use various method calls to retrieve/add/delete/update data
 * without directly interacting with the data source. The below example demonstrates basic CRUD
 * operations: select, add, update, and delete.
 */

public class CustomerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // db stuff
        final var dataSource = createDataSource();
        final var dbDao = new DbCustomerDao(dataSource);

        // stage stuff
        primaryStage.setTitle("Customers");

        Label lblAdd = new Label("Add");
        TextField textFieldAdd = new TextField();

        GridPane gridPane = new GridPane();

        // buttons
        Button editButton = new Button("Add");
        editButton.setOnAction(actionEvent -> primaryStage.close());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(actionEvent -> primaryStage.close());

        // fetch customers
        Stream<Customer> all = dbDao.getAll();

        // build view
        for(int i = 1; i<=dbDao.getAll().count();i++){
            Optional<Customer> customer = dbDao.getById(i);
            if(customer.isPresent()){
//                // customer list with buttons
                HBox hbox = new HBox();
//
//                // customer
                Label label = new Label(customer.get().getId() +
                        "     " +
                        customer.get().getCustomerName());
                label.setOnMouseClicked(mouseEvent -> primaryStage.close());
                label.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 18;");

                hbox.setSpacing(20);
                hbox.getChildren().addAll(label, editButton, deleteButton);
                gridPane.add(hbox, 1, i, 1,1);


            };

        }

        // header
        Label header = new Label("Customers");
        header.setPrefWidth(250);
        header.setAlignment(Pos.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(header, 1, 0, gridPane.getColumnCount(), 1);
        gridPane.add(new MainMenu().vBox, 0, 0, 1, gridPane.getRowCount());


        // set scene
        Scene scene = new Scene(gridPane,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()-30);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
        Application.launch(args);
        final var inMemoryDao = new InMemoryCustomerDao();
        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbCustomerDao(dataSource);
        performOperationsUsing(dbDao);
        printTest();
//        deleteSchema(dataSource);

//        private static void deleteSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//                statement.execute(CustomerSchemaSql.DELTE_SCHEMA_SQL);
//            }
//        }
//
//        private static void createSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//            statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL);
//            }
//        }
//    }
//
//    private static void deleteSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL);
//        }
//    }
//
//    private static void createSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL);
//        }
//    }
    }

    private static void printTest() throws Exception {
        DbCustomerDao test = new DbCustomerDao(createDataSource());
        Optional<Customer> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getCustomerName());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

        private static void performOperationsUsing ( final CustomerDao customerDao) throws Exception {
            addCustomer(customerDao);
//            log.info(ALL_CUSTOMERS);
//            try (var customerStream = customerDao.getAll()) {
//                customerStream.forEach((customer) -> log.info(customer.toString()));
//            }
//            log.info("customerDao.getByCustomerId(2): " + customerDao.getById(2));
//            final var customer = new Customer(4, "Dan", "Danson");
//            customerDao.add(customer);
//            log.info(ALL_CUSTOMERS + customerDao.getAll());
//            customer.setType("Daniel");
//            customer.update(customer);
//            log.info(ALL_CUSTOMERS);
//            try (var customerStream = customerDao.getAll()) {
//                customerStream.forEach((addr) -> log.info(addr.toString()));
//            }
//            customerDao.delete(customer);
//            log.info(ALL_CUSTOMERS + customerDao.getAll());
        }

        private static void addCustomer (CustomerDao customerDao) throws Exception {
            for (var customer : generateSampleCustomers()) {
                customerDao.add(customer);
            }
        }

        /**
         * Generate customers.
         *
         * @return list of customers.
         */
        public static List<Customer> generateSampleCustomers () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
            final var customer1 = new Customer(11, "testy", 1, true,
                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
//            final var address1 = new Address(11, "test", "test", 1, "88888", "8888888",
//                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
            return List.of(customer1);
        }
    }
