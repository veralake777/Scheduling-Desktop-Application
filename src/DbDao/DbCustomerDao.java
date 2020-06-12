package DbDao;

import Interface.CustomerDao;
import POJO.Address;
import POJO.City;
import POJO.Customer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import utils.CustomException;
import utils.DBUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
 * <p>
 * An implementation of {@link CustomerDao} that persists customers in RDBMS.
 */
public class DbCustomerDao implements CustomerDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbCustomerDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbCustomerDao} which uses provided dataSource to
     * store and retrieve customer information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbCustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all customers as Java Stream.
     *
     * @return a lazily populated stream of customers. Note the stream returned must be closed to free
     * all the acquired resources. The stream keeps an open connection to the database till it is
     * complete or is closed manually.
     */
    @Override
    public Stream<Customer> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM customer");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Customer>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super Customer> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createCustomer(resultSet));
                        return true;
                    } catch (SQLException e) {
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

    private Customer createCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(resultSet.getInt("customerId"),
                resultSet.getString("customerName"),
                resultSet.getInt("addressId"),
                resultSet.getBoolean("active"),
                String.valueOf(resultSet.getDate("createDate")),
                resultSet.getString("createdBy"),
                String.valueOf(resultSet.getDate("lastUpdate")),
                resultSet.getString("lastUpdateBy"));
    }

    public int maxId() throws CustomException, SQLException {
        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT max(customerId) FROM customer")) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Customer> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM customer WHERE customerId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createCustomer(resultSet));
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
    public boolean add(Customer customer) throws Exception {
        if (getById(customer.getId()).isPresent()) {
            return false;
        }
        try (var connection = getConnection();
             // int id, String customerName, int addressId, boolean active, String createDate,
             // String createdBy, String lastUpdate, String lastUpdateBy
             //-- CALL new_customer('name', 'address1', 'address2', 'Quebec', '88888', '555-5555', 1);
             var statement = connection.prepareStatement("INSERT INTO customer(customerId, customerName, addressId, active, createDate, lastUpdate) VALUES (?,?,?,?, now(), now())")) {
            // dates to strings
            // TODO fix types for calendar - must be in 'YYYY-MM-DD 00:00:00'
            // set customerId to null bc of auto_increment on table
            statement.setInt(1, customer.getId());
            statement.setString(2, customer.getCustomerName());
            statement.setInt(3, customer.getAddressId());
            statement.setInt(4, 1);

            // customer isActive is always set to 1
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
    public boolean update(Customer customer) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE customer SET " +
                                     "customerName = ?, " +
                                     "addressId = ? " +
                                     "WHERE customerId = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getAddressId());
            statement.setInt(3, customer.getId());
            statement.closeOnCompletion();
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Customer customer) throws Exception {
        boolean statement1;
        boolean statement2;
        try (var connection = getConnection();
             var s1 = connection.prepareStatement("DELETE FROM appointment WHERE customerId = ?")) {
            s1.setInt(1, customer.getId());
            statement1 = s1.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
        try (var connection = getConnection();
             var s2 = connection.prepareStatement("DELETE FROM customer WHERE customerId = ?")) {
            s2.setInt(1, customer.getId());
            statement2 = s2.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
        return statement1 && statement2;
    }

    private Optional<Address> getAddress(int addressId) throws Exception {
        Optional<Address> address = new DbAddressDao(DBUtils.getMySQLDataSource()).getById(addressId);
        return address;
    }

    private Optional<City> getCity(int cityId) throws Exception {
        Optional<City> city = new DbCityDao(DBUtils.getMySQLDataSource()).getById(cityId);
        assert city.isPresent();
        return city;
    }

}
