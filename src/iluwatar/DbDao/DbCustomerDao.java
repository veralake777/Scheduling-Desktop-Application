package iluwatar.DbDao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import iluwatar.CustomException;
import iluwatar.Interface.CustomerDao;
import iluwatar.POJO.Customer;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
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
     *     all the acquired resources. The stream keeps an open connection to the database till it is
     *     complete or is closed manually.
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
             var statement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?)")) {
            // dates to strings
            // TODO fix types for calendar - must be in 'YYYY-MM-DD 00:00:00'
            statement.setInt(1, customer.getId());
            statement.setString(2, customer.getCustomerName());
            statement.setInt(3, customer.getAddressId());
            if(customer.isActive()){
                statement.setInt(4, 1);
            } else {
                statement.setInt(4, 0);
            }
            statement.setTimestamp(5, Timestamp.valueOf(customer.getCreateDate()));
            statement.setString(6, customer.getCreatedBy());
            statement.setTimestamp(7, Timestamp.valueOf(customer.getLastUpdate()));
            statement.setString(8, customer.getLastUpdateBy());

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
                             .prepareStatement("UPDATE customer SET title = ? WHERE ID = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, customer.getCustomerName());
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
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM customer WHERE ID = ?")) {
            statement.setInt(1, customer.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }


}
