package iluwatar;

import iluwatar.DbDao.DbUserDao;
import iluwatar.InMemory.InMemoryUserDao;
import iluwatar.Interface.UserDao;
import iluwatar.POJO.User;
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

public class UserApp {
    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_CUSTOMERS = "userDao.getAllUsers(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
        final var inMemoryDao = new InMemoryUserDao();
        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbUserDao(dataSource);
        performOperationsUsing(dbDao);
        printTest();
//        deleteSchema(dataSource);

//        private static void deleteSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//                statement.execute(UserSchemaSql.DELTE_SCHEMA_SQL);
//            }
//        }
//
//        private static void createSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//            statement.execute(UserSchemaSql.CREATE_SCHEMA_SQL);
//            }
//        }
//    }
//
//    private static void deleteSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(UserSchemaSql.DELETE_SCHEMA_SQL);
//        }
//    }
//
//    private static void createSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(UserSchemaSql.CREATE_SCHEMA_SQL);
//        }
//    }
    }

    private static void printTest() throws Exception {
        DbUserDao test = new DbUserDao(createDataSource());
        Optional<User> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getUserName());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

        private static void performOperationsUsing ( final UserDao userDao) throws Exception {
            addUser(userDao);
//            log.info(ALL_CUSTOMERS);
//            try (var userStream = userDao.getAll()) {
//                userStream.forEach((user) -> log.info(user.toString()));
//            }
//            log.info("userDao.getByUserId(2): " + userDao.getById(2));
//            final var user = new User(4, "Dan", "Danson");
//            userDao.add(user);
//            log.info(ALL_CUSTOMERS + userDao.getAll());
//            user.setType("Daniel");
//            user.update(user);
//            log.info(ALL_CUSTOMERS);
//            try (var userStream = userDao.getAll()) {
//                userStream.forEach((addr) -> log.info(addr.toString()));
//            }
//            userDao.delete(user);
//            log.info(ALL_CUSTOMERS + userDao.getAll());
        }

        private static void addUser (UserDao userDao) throws Exception {
            for (var user : generateSampleUsers()) {
                userDao.add(user);
            }
        }

        /**
         * Generate users.
         *
         * @return list of users.
         */
        public static List<User> generateSampleUsers () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
            final var user1 = new User(11, "testy", "test", true,
                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
//            final var address1 = new Address(11, "test", "test", 1, "88888", "8888888",
//                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
            return List.of(user1);
        }
    }
