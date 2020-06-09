package iluwatar.DbDao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import iluwatar.CustomException;
import iluwatar.Interface.UserDao;
import iluwatar.POJO.User;

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
 * An implementation of {@link UserDao} that persists users in RDBMS.
 */
public class DbUserDao implements UserDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbUserDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbUserDao} which uses provided dataSource to
     * store and retrieve user information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all users as Java Stream.
     *
     * @return a lazily populated stream of users. Note the stream returned must be closed to free
     *     all the acquired resources. The stream keeps an open connection to the database till it is
     *     complete or is closed manually.
     */
    @Override
    public Stream<User> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM user");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<User>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super User> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createUser(resultSet));
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

    private User createUser(ResultSet resultSet) throws SQLException, ParseException {
        return new User(resultSet.getInt("userId"),
                resultSet.getString("userName"),
                resultSet.getString("password"),
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
    public Optional<User> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createUser(resultSet));
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
    public boolean add(User user) throws Exception {
        if (getById(user.getId()).isPresent()) {
            return false;
        }



        try (var connection = getConnection();
             var statement = connection.prepareStatement("INSERT INTO user VALUES (?,?,?,?,?,?,?,?)")) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            if(user.getActive()) {
                statement.setInt(4, 1);
            } else {
                statement.setInt(4, 0);
            }
            statement.setTimestamp(5, Timestamp.valueOf(user.getCreateDate()));
            statement.setString(6, user.getCreatedBy());
            statement.setTimestamp(7, Timestamp.valueOf(user.getLastUpdate()));
            statement.setString(8, user.getLastUpdateBy());

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
    public boolean update(User user) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE user SET title = ? WHERE ID = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, user.getUserName());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(User user) throws Exception {
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM user WHERE ID = ?")) {
            statement.setInt(1, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }


}
