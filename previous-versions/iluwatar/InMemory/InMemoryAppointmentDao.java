package iluwatar.InMemory;

import iluwatar.Interface.AppointmentDao;
import iluwatar.POJO.Appointment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * An in memory implementation of {@link iluwatar.Interface.AppointmentDao}, which stores the appointments in JVM memory and
 * data is lost when the application exits.
 *
 * This implementation is useful as temporary database or for testing.
 */

public class InMemoryAppointmentDao implements AppointmentDao {
    private Map<Integer, Appointment> idToAppointment = new HashMap<>();

    /**
     * An eagerly evaluated stream of appointments stored in memory.
     */
    @Override
    public Stream<Appointment> getAll() {
        return idToAppointment.values().stream();
    }

    @Override
    public Optional<Appointment> getById(final int id) {
        return Optional.ofNullable((idToAppointment).get(id));
    }

    @Override
    public boolean add(final Appointment appointment) {
        if (getById(appointment.getId()).isPresent()) {
            return false;
        }

        idToAppointment.put(appointment.getId(), appointment);
        return true;
    }

    @Override
    public boolean update(final Appointment appointment) {
        return idToAppointment.replace(appointment.getId(), appointment) != null;
    }

    @Override
    public boolean delete(final Appointment appointment) {
        return idToAppointment.remove(appointment.getId()) != null;
    }
}
