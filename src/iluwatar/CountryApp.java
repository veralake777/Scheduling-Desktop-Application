package iluwatar;

import iluwatar.DbDao.DbCountryDao;
import iluwatar.InMemory.InMemoryCountryDao;
import iluwatar.Interface.CountryDao;
import iluwatar.POJO.Country;
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

public class CountryApp {
    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_APPOINTMENTS = "countryDao.getAllCountrys(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
        final var inMemoryDao = new InMemoryCountryDao();
        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbCountryDao(dataSource);
        performOperationsUsing(dbDao);
        printTest();
//        deleteSchema(dataSource);

//        private static void deleteSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//                statement.execute(CountrySchemaSql.DELTE_SCHEMA_SQL);
//            }
//        }
//
//        private static void createSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//            statement.execute(CountrySchemaSql.CREATE_SCHEMA_SQL);
//            }
//        }
//    }
//
//    private static void deleteSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CountrySchemaSql.DELETE_SCHEMA_SQL);
//        }
//    }
//
//    private static void createSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CountrySchemaSql.CREATE_SCHEMA_SQL);
//        }
//    }
    }

    private static void printTest() throws Exception {
        DbCountryDao test = new DbCountryDao(createDataSource());
        Optional<Country> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getCountry());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

        private static void performOperationsUsing ( final CountryDao countryDao) throws Exception {
            addCountry(countryDao);
//            log.info(ALL_APPOINTMENTS);
//            try (var customerStream = countryDao.getAll()) {
//                customerStream.forEach((customer) -> log.info(customer.toString()));
//            }
//            log.info("countryDao.getByCountryId(2): " + countryDao.getById(2));
//            final var country = new Country(4, "Dan", "Danson");
//            countryDao.add(country);
//            log.info(ALL_APPOINTMENTS + countryDao.getAll());
//            country.setType("Daniel");
//            country.update(country);
//            log.info(ALL_APPOINTMENTS);
//            try (var countryStream = countryDao.getAll()) {
//                countryStream.forEach((addr) -> log.info(addr.toString()));
//            }
//            countryDao.delete(country);
//            log.info(ALL_APPOINTMENTS + countryDao.getAll());
        }

        private static void addCountry (CountryDao countryDao) throws Exception {
            for (var country : generateSampleCountrys()) {
                countryDao.add(country);
            }
        }

        /**
         * Generate countrys.
         *
         * @return list of countrys.
         */
        public static List<Country> generateSampleCountrys () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
            final var country1 = new Country(11, "TST",
                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
//            final var address1 = new Address(11, "test", "test", 1, "88888", "8888888",
//                    "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00", "test");
            return List.of(country1);
        }
    }
