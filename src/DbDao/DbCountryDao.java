package DbDao;

import Interface.CountryDao;
import POJO.Country;
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
 * An implementation of {@link CountryDao} that persists countries in RDBMS.
 */
public class DbCountryDao implements CountryDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbCountryDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbCountryDao} which uses provided dataSource to
     * store and retrieve country information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all countries as Java Stream.
     *
     * @return a lazily populated stream of countries. Note the stream returned must be closed to free
     * all the acquired resources. The stream keeps an open connection to the database till it is
     * complete or is closed manually.
     */
    @Override
    public Stream<Country> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM country");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Country>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super Country> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createCountry(resultSet));
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

    private Country createCountry(ResultSet resultSet) throws SQLException, ParseException {
        return new Country(resultSet.getInt("countryId"),
                resultSet.getString("country"),
                String.valueOf(resultSet.getDate("createDate")),
                resultSet.getString("createdBy"),
                String.valueOf(resultSet.getDate("lastUpdate")),
                resultSet.getString("lastUpdateBy"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Country> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM country WHERE countryId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createCountry(resultSet));
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
    public boolean add(Country country) throws Exception {
        if (getById(country.getId()).isPresent()) {
            return false;
        }

        try (var connection = getConnection();
             var statement = connection.prepareStatement("INSERT INTO country VALUES (?,?,?,?,?,?)")) {
            // dates to strings
            // TODO fix types for calendar - must be in 'YYYY-MM-DD 00:00:00'
            statement.setInt(1, country.getId());
            statement.setString(2, country.getCountry());

            statement.setTimestamp(3, Timestamp.valueOf(country.getCreateDate()));
            statement.setString(4, country.getCreatedBy());
            statement.setTimestamp(5, Timestamp.valueOf(country.getLastUpdate()));
            statement.setString(6, country.getLastUpdateBy());

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
    public boolean update(Country country) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE country SET title = ? WHERE ID = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, country.getCountry());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Country country) throws Exception {
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM country WHERE ID = ?")) {
            statement.setInt(1, country.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }


    public Optional<Country> getByName(String country) throws CustomException, SQLException {
        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM city WHERE city = ?")) {

            statement.setString(1, country);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createCountry(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException | ParseException ex) {
            throw new CustomException(ex.getMessage(), ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
