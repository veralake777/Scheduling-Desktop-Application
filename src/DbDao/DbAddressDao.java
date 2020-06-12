package DbDao;

import Interface.AddressDao;
import POJO.Address;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import utils.CustomException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * An implementation of {@link AddressDao} that persists addresses in RDBMS.
 */
public class DbAddressDao implements AddressDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbAddressDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbAddressDao} which uses provided dataSource to
     * store and retrieve address information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbAddressDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all addresses as Java Stream.
     *
     * @return a lazily populated stream of addresses. Note the stream returned must be closed to free
     * all the acquired resources. The stream keeps an open connection to the database till it is
     * complete or is closed manually.
     */
    @Override
    public Stream<Address> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM address");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Address>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super Address> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createAddress(resultSet));
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

    private Address createAddress(ResultSet resultSet) throws SQLException, ParseException {
        return new Address(resultSet.getInt("addressId"),
                resultSet.getString("address"),
                resultSet.getString("address2"),
                resultSet.getInt("cityId"),
                resultSet.getString("postalCode"),
                resultSet.getString("phone"),
                String.valueOf(resultSet.getDate("createDate")),
                resultSet.getString("createdBy"),
                String.valueOf(resultSet.getDate("lastUpdate")),
                resultSet.getString("lastUpdateBy"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Address> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM address WHERE addressId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createAddress(resultSet));
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

    // select statement uses address1 and phone as a unique identifier for the address
    public Optional<Address> getByAddress(String address, String phone) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM address WHERE address = ?  AND phone = ?")) {

            statement.setString(1, address);
            statement.setString(2, phone);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createAddress(resultSet));
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
    public boolean add(Address address) throws Exception {
        if (getById(address.getId()).isPresent()) {
            return false;
        }

        try (var connection = getConnection();
             var statement = connection.prepareStatement("INSERT INTO address VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            // dates to strings
            statement.setNull(1, address.getId());
            statement.setString(2, address.getAddress());
            statement.setString(3, address.getAddress2());
            statement.setInt(4, address.getCityId());
            statement.setString(5, address.getPostalCode());
            statement.setString(6, address.getPhone());
            statement.setString(7, address.getCreateDate());
            statement.setString(8, address.getCreatedBy());
            statement.setString(9, address.getLastUpdate());
            statement.setString(10, address.getLastUpdateBy());
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
    public boolean update(Address address) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE address SET " +
                                     "address = ?, " +
                                     "address2 = ?, " +
                                     "cityId = ?, " +
                                     "postalCode = ?, " +
                                     "phone = ?" +
                                     "WHERE addressId = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, address.getAddress());
            statement.setString(2, address.getAddress2());
            statement.setInt(3, address.getCityId());
            statement.setString(4, address.getPostalCode());
            statement.setString(5, address.getPhone());
            statement.setInt(6, address.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Address address) throws Exception {
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM address WHERE ID = ?")) {
            statement.setInt(1, address.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    public int getMaxId() throws CustomException, SQLException {
        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT max(addressId) FROM address")) {
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
