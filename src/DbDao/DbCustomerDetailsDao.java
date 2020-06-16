package DbDao;

import Interface.CustomerDetailsDao;
import POJO.CustomerDetails;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import utils.CustomException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DbCustomerDetailsDao implements CustomerDetailsDao {
    private final DataSource dataSource;

    /**
     * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
     * <p>
     * Creates an instance of {@link DbCustomerDetailsDao} which uses provided dataSource to
     * store and retrieve customer information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbCustomerDetailsDao(DataSource dataSource) {
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
    public Stream<CustomerDetails> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("select * from customerDetails");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<CustomerDetails>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer<? super CustomerDetails> action) {
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

    private CustomerDetails createCustomer(ResultSet resultSet) throws SQLException {
        return new CustomerDetails(
                resultSet.getInt("customerId"),
                resultSet.getString("customerName"),
                resultSet.getString("phone"),
                resultSet.getString("address"),
                resultSet.getString("address2"),
                resultSet.getString("city"),
                resultSet.getString("postalCode"),
                resultSet.getString("country"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CustomerDetails> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM customerDetails WHERE customerId = ?")) {

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

    public Optional<CustomerDetails> getByName(String name) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM customerDetails WHERE customerName = ?")) {

            statement.setString(1, name);
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
//    @Override
//    public boolean add(CustomerDetails customer) throws Exception {
//        if (getById(customer.getId()).isPresent()) {
//            return false;
//        }
//
//        try (var connection = getConnection();
//             var statement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?)")) {
//            // dates to strings
//            // TODO fix types for calendar - must be in 'YYYY-MM-DD 00:00:00'
//            statement.setInt(1, customer.getId());
//            statement.setString(2, customer.getName());
//            statement.setInt(3, customer.getAddress1());
//            if(customer.isActive()){
//                statement.setInt(4, 1);
//            } else {
//                statement.setInt(4, 0);
//            }
//            statement.setTimestamp(5, Timestamp.valueOf(customer.getCreateDate()));
//            statement.setString(6, customer.getCreatedBy());
//            statement.setTimestamp(7, Timestamp.valueOf(customer.getLastUpdate()));
//            statement.setString(8, customer.getLastUpdateBy());
//
//            statement.execute();
//            return true;
//        } catch (SQLException ex) {
//            throw new CustomException(ex.getMessage(), ex);
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean update(CustomerDetails customer) throws Exception {
//        try (var connection = getConnection();
//             var statement =
//                     connection
//                             .prepareStatement("UPDATE customer SET title = ? WHERE ID = ?")) {
//            //TODO add all updates you would like to make based on UI
//            statement.setString(1, customer.getName());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            throw new CustomException(ex.getMessage(), ex);
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean delete(CustomerDetails customer) throws Exception {
//        try (var connection = getConnection();
//             var statement = connection.prepareStatement("DELETE FROM customer WHERE ID = ?")) {
//            statement.setInt(1, customer.getId());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            throw new CustomException(ex.getMessage(), ex);
//        }
//    }

}
