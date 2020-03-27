package iluwatar;

import iluwatar.DbDao.DbAddressDao;
import iluwatar.InMemory.InMemoryAddressDao;
import iluwatar.Interface.AddressDao;
import iluwatar.POJO.Address;
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

public class AddressApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // db stuff
        final var dataSource = createDataSource();
        final var dbDao = new DbAddressDao(dataSource);

        // stage stuff
        primaryStage.setTitle("Address");

        Label addAddressLbl = new Label("Add");
        TextField addAddressTxt = new TextField();

        GridPane gridPane = new GridPane();

        // buttons
        Button editButton = new Button("Add");
        editButton.setOnAction(actionEvent -> primaryStage.close());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(actionEvent -> primaryStage.close());

        // fetch addresses
        Stream<Address> all = dbDao.getAll();

        // build view
        for(int i = 1; i<=dbDao.getAll().count();i++){
            Optional<Address> address = dbDao.getById(i);
            if(address.isPresent()){
                // address list with buttons
                HBox hbox = new HBox();

                // address
                Label label = new Label(address.get().getAddress() +
                        "     " +
                        address.get().getPhone());
                label.setOnMouseClicked(mouseEvent -> primaryStage.close());
                label.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 18;");

                hbox.setSpacing(20);
                hbox.getChildren().addAll(label, editButton, deleteButton);
                gridPane.add(hbox, 0, i, 1,1);
            };
        }

        // header
        Label header = new Label("Addresses");
        header.setPrefWidth(250);
        header.setAlignment(Pos.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(header, 0, 0, gridPane.getColumnCount(), 1);

        // set scene
        Scene scene = new Scene(gridPane,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()-30);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_ADDRESSES = "addressDao.getAllAddresses(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */
    public static void main(String[] args) throws Exception {
        Application.launch(args);
        final var inMemoryDao = new InMemoryAddressDao();
//        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbAddressDao(dataSource);
//        dbDao.getAll();
//        performOperationsUsing(dbDao);
        printTest();
//        deleteSchema(dataSource);

//        private static void deleteSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//                statement.execute(AppointmentSchemaSql.DELTE_SCHEMA_SQL);
//            }
//        }
//
//        private static void createSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//            statement.execute(AppointmentSchemaSql.CREATE_SCHEMA_SQL);
//            }
//        }
//    }
//
//    private static void deleteSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(AppointmentSchemaSql.DELETE_SCHEMA_SQL);
//        }
//    }
//
//    private static void createSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(AppointmentSchemaSql.CREATE_SCHEMA_SQL);
//        }
//    }
    }

    private static void printTest() throws Exception {
        DbAddressDao test = new DbAddressDao(createDataSource());
        Optional<Address> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getAddress());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

//        private static void performOperationsUsing ( final AddressDao addressDao) throws Exception {
//            addAddress(addressDao);
//            log.info(ALL_APPOINTMENTS);
//            try (var addressStream = addressDao.getAll()) {
//                addressStream.forEach((address) -> System.out.println(address.toString()));
//            }
//            log.info("appointmentDao.getByAppointmentId(2): " + appointmentDao.getById(2));
//            final var appointment = new Appointment(4, "Dan", "Danson");
//            appointmentDao.add(appointment);
//            log.info(ALL_APPOINTMENTS + appointmentDao.getAll());
//            appointment.setType("Daniel");
//            appointment.update(appointment);
//            log.info(ALL_APPOINTMENTS);
//            try (var appointmentStream = appointmentDao.getAll()) {
//                appointmentStream.forEach((addr) -> log.info(addr.toString()));
//            }
//            appointmentDao.delete(appointment);
//            log.info(ALL_APPOINTMENTS + appointmentDao.getAll());
//        }

        private static void addAddress (AddressDao addressDao) throws Exception {
            for (var address : generateSampleAddresses()) {
                addressDao.add(address);
            }
        }

        /**
         * Generate appointments.
         *
         * @return list of appointments.
         */
        public static List<Address> generateSampleAddresses () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
//            final var appointment1 = new Appointment(11, 1, 1, "title", "descrip",
//                    "here", "9999999", "new", "url", "2020-02-25 00:00:00",
//                    "2020-02-25 00:00:00", "2020-02-25 00:00:00", "test", "2020-02-25 00:00:00",
//                    "test");
            final var address1 = new Address(11, "test", "test", 1, "88888", "8888888",
                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
            return List.of(address1);
        }
    }
