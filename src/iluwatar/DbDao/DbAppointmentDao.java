package iluwatar.DbDao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import iluwatar.CustomException;
import iluwatar.Interface.AppointmentDao;
import iluwatar.POJO.Appointment;

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
 * An implementation of {@link AppointmentDao} that persists appointmentes in RDBMS.
 */
public class DbAppointmentDao implements AppointmentDao {
//TODO fix logger using Logger.util class
//    private static final Logger LOGGER = LoggerFactory.getLogger(DbAppointmentDao.class);

    private final DataSource dataSource;

    /**
     * Creates an instance of {@link DbAppointmentDao} which uses provided dataSource to
     * store and retrieve appointment information.
     *
     * @param dataSource a non-null dataSource.
     */
    public DbAppointmentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get all appointmentes as Java Stream.
     *
     * @return a lazily populated stream of appointmentes. Note the stream returned must be closed to free
     *     all the acquired resources. The stream keeps an open connection to the database till it is
     *     complete or is closed manually.
     */
    @Override
    public Stream<Appointment> getAll() throws Exception {
        try {
            var connection = getConnection();
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT * FROM appointment");
            var resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Appointment>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super Appointment> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createAppointment(resultSet));
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

    private Appointment createAppointment(ResultSet resultSet) throws SQLException, ParseException {
        return new Appointment(resultSet.getInt("appointmentId"),
                resultSet.getInt("customerId"),
                resultSet.getInt("userId"),

                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getString("location"),
                resultSet.getString("contact"),
                resultSet.getString("type"),
                resultSet.getString("url"),
                String.valueOf(resultSet.getDate("start")),
                String.valueOf(resultSet.getDate("end")),
                String.valueOf(resultSet.getDate("createDate")),
                resultSet.getString("createdBy"),
                String.valueOf(resultSet.getDate("lastUpdate")),
                resultSet.getString("lastUpdateBy"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Appointment> getById(int id) throws Exception {

        ResultSet resultSet = null;

        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM appointment WHERE appointmentId = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createAppointment(resultSet));
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
    public boolean add(Appointment appointment) throws Exception {
        if (getById(appointment.getId()).isPresent()) {
            return false;
        }

        try (var connection = getConnection();
             var statement = connection.prepareStatement("INSERT INTO appointment VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            // dates to strings
            statement.setInt(1, appointment.getId());
            statement.setInt(2, appointment.getCustomerId());
            statement.setInt(3, appointment.getUserId());
            statement.setString(4, appointment.getTitle());
            statement.setString(5, appointment.getDescription());
            statement.setString(6, appointment.getLocation());
            statement.setString(7, appointment.getContact());
            statement.setString(8, appointment.getType());
            statement.setString(9, appointment.getUrl());
            statement.setTimestamp(10, Timestamp.valueOf(appointment.getStart()));
            statement.setTimestamp(11,Timestamp.valueOf((appointment.getEnd())));
            statement.setTimestamp(12,  Timestamp.valueOf((appointment.getCreateDate())));
            statement.setString(13, appointment.getCreatedBy());
            statement.setTimestamp(14, Timestamp.valueOf((appointment.getLastUpdate())));
            statement.setString(15, appointment.getLastUpdateBy());
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
    public boolean update(Appointment appointment) throws Exception {
        try (var connection = getConnection();
             var statement =
                     connection
                             .prepareStatement("UPDATE appointment SET appointment = ?, appointment2 = ? WHERE ID = ?")) {
            //TODO add all updates you would like to make based on UI
            statement.setString(1, appointment.getStart());
            statement.setString(2, appointment.getEnd());
            statement.setInt(3, appointment.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Appointment appointment) throws Exception {
        try (var connection = getConnection();
             var statement = connection.prepareStatement("DELETE FROM appointment WHERE ID = ?")) {
            statement.setInt(1, appointment.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
    }


}
