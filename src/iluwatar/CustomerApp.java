package iluwatar;

import iluwatar.DbDao.DbCustomerDao;
import iluwatar.InMemory.InMemoryCustomerDao;
import iluwatar.Interface.CustomerDao;
import iluwatar.POJO.Customer;
import utils.Database.DBUtils;

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

public class CustomerApp {
    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_CUSTOMERS = "customerDao.getAllCustomers(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
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
