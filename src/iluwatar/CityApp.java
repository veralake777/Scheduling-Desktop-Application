package iluwatar;

import iluwatar.DbDao.DbCityDao;
import iluwatar.InMemory.InMemoryCityDao;
import iluwatar.Interface.CityDao;
import iluwatar.POJO.City;
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

public class CityApp {
    private static final String DB_URL = "";
    // TODO fix logger
//    private static Logger log = LoggerFactory.getLogger(App.class);
    private static final String ALL_CITIES = "cityDao.getAllCities(): ";

    /**
     * Program entry point
     *
     * @param args command line args
     * @throws Exception if any error occurs
     */

    public static void main(final String[] args) throws Exception {
        final var inMemoryDao = new InMemoryCityDao();
        performOperationsUsing(inMemoryDao);

        final var dataSource = createDataSource();
//        createSchema(dataSource);
        final var dbDao = new DbCityDao(dataSource);
        performOperationsUsing(dbDao);
        printTest();
//        deleteSchema(dataSource);

//        private static void deleteSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//                statement.execute(CitySchemaSql.DELETE_SCHEMA_SQL);
//            }
//        }
//
//        private static void createSchema(DataSource dataSource) throws SQLException {
//            try (var connection = dataSource.getConnection();
//            var statement = connection.createStatement()) {
//            statement.execute(CitySchemaSql.CREATE_SCHEMA_SQL);
//            }
//        }
//    }
//
//    private static void deleteSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CitySchemaSql.DELETE_SCHEMA_SQL);
//        }
//    }
//
//    private static void createSchema(DataSource dataSource) throws SQLException {
//        try (var connection = dataSource.getConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(CitySchemaSql.CREATE_SCHEMA_SQL);
//        }
//    }
    }

    private static void printTest() throws Exception {
        DbCityDao test = new DbCityDao(createDataSource());
        Optional<City> test1 = test.getById(1);
        System.out.println("printTest  " + test1.get().getCity());
    }

    private static DataSource createDataSource () throws ClassNotFoundException, IOException {
        return DBUtils.getMySQLDataSource();
    }

        private static void performOperationsUsing ( final CityDao cityDao) throws Exception {
            addCity(cityDao);
//            log.info(ALL_CITIES);
//            try (var customerStream = cityDao.getAll()) {
//                customerStream.forEach((customer) -> log.info(customer.toString()));
//            }
//            log.info("cityDao.getByCityId(2): " + cityDao.getById(2));
//            final var city = new City(4, "Dan", "Danson");
//            cityDao.add(city);
//            log.info(ALL_CITIES + cityDao.getAll());
//            city.setType("Daniel");
//            city.update(city);
//            log.info(ALL_CITIES);
//            try (var cityStream = cityDao.getAll()) {
//                cityStream.forEach((addr) -> log.info(addr.toString()));
//            }
//            cityDao.delete(city);
//            log.info(ALL_CITIES + cityDao.getAll());
        }

        private static void addCity (CityDao cityDao) throws Exception {
            for (var city : generateSampleCities()) {
                cityDao.add(city);
            }
        }

        /**
         * Generate appointments.
         *
         * @return list of appointments.
         */
        public static List<City> generateSampleCities () throws ParseException {
            // todo fix date inputs getting IllegalArgumentException error
            final var city1 = new City(11, "test", 1, "2020-02-26 00:00:00", "test", "2020-02-26 00:00:00",
                    "test");
            return List.of(city1);
        }
    }
