package DbDao;

import Interface.CityDao;
import POJO.City;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import utils.CustomException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
 * <p>
 * An implementation of {@link CityDao} that persists cities in RDBMS.
 */
public class DbCityDao implements CityDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbCityDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbCityDao} which uses provided dataSource to
     * store and retrieve city information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbCityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all cities as Java Stream.
     *
     * @return a lazily populated stream of cities. Note the stream returned must be closed to free
     * all the acquired resources. The stream keeps an open connection to the database till it is
     * complete or is closed manually.
     */
    @Override
    public Stream<City> getAll() throws Exception {
        // All cities in the three countries USA, Canada, France
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM city_view ORDER BY city" +
                    "");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<City>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super City> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createCity(resultSet));
                        return true;
                    } catch (SQLException | ParseException e) {
                        throw new RuntimeException(e); // NOSONAR
                    }
                }
            }, false).onClose(() -> mutedClose(connection, statement, resultSet));
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    private Connection getConnection() throws SQLException {
        return (Connection) dataSource.getConnection();
    }

    private void mutedClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            //TODO fix logger
//            LOGGER.info("Exception thrown " + e.getMessage());
        }
    }

    private City createCity(ResultSet resultSet) throws SQLException, ParseException {
        return new City(resultSet.getInt("cityId"),
                resultSet.getString("city"),
                resultSet.getInt("countryId"),
                String.valueOf(resultSet.getDate("createDate")),
                resultSet.getString("createdBy"),
                String.valueOf(resultSet.getDate("lastUpdate")),
                resultSet.getString("lastUpdateBy"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<City> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM city WHERE cityId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createCity(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(City city) throws Exception {
        if (getById(city.getId()).isPresent()) {
            return false;
        }


        try (var connection = getConnection();
             var statement = connection.prepareStatement("INSERT INTO city VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            // dates to strings
            // TODO fix types for calendar - must be in 'YYYY-MM-DD 00:00:00'
            statement.setInt(1, city.getId());
            statement.setString(2, city.getCity());
            statement.setInt(3, city.getCountryId());

            statement.setTimestamp(7, Timestamp.valueOf(city.getCreateDate()));
            statement.setString(8, city.getCreatedBy());
            statement.setTimestamp(9, Timestamp.valueOf(city.getLastUpdate()));
            statement.setString(10, city.getLastUpdateBy());

            statement.execute();
            return true;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(City city) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE city SET title = ? WHERE ID = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, city.getCity());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(City city) throws Exception {
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM city WHERE ID = ?")) {
            statement.setInt(1, city.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }


    public int getByName(String city) throws CustomException, SQLException {
        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT cityId FROM city WHERE city = ?")) {

            statement.setString(1, city);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
