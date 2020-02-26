package iluwatar;

import iluwatar.DbDao.DbAppointmentDao;
import iluwatar.InMemory.InMemoryAppointmentDao;
import iluwatar.Interface.AppointmentDao;
import iluwatar.POJO.Appointment;
import utils.DBUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;


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

public class ApptApp {
    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_APPOINTMENTS = "appointmentDao.getAllAppointments(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
        final var inMemoryDao = new InMemoryAppointmentDao();
        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbAppointmentDao(dataSource);
        performOperationsUsing(dbDao);
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
        DbAppointmentDao test = new DbAppointmentDao(createDataSource());
        Optional<Appointment> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getType());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

        private static void performOperationsUsing ( final AppointmentDao appointmentDao) throws Exception {
            addAppointment(appointmentDao);
//            log.info(ALL_APPOINTMENTS);
//            try (var customerStream = appointmentDao.getAll()) {
//                customerStream.forEach((customer) -> log.info(customer.toString()));
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
        }

        private static void addAppointment (AppointmentDao appointmentDao) throws Exception {
            for (var appointment : generateSampleAppointments()) {
                appointmentDao.add(appointment);
            }
        }

        /**
         * Generate appointments.
         *
         * @return list of appointments.
         */
        public static List<Appointment> generateSampleAppointments () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
            final var appointment1 = new Appointment(11, 1, 1, "title", "descrip",
                    "here", "9999999", "new", "url", "2020-02-26 00:00:00",
                    "2020-02-26 00:00:00", "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00",
                    "test");
//            final var address1 = new Address(11, "test", "test", 1, "88888", "8888888",
//                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
            return List.of(appointment1);
        }
    }
