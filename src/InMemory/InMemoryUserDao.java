package InMemory;


import Interface.UserDao;
import POJO.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * An in memory implementation of {@link UserDao}, which stores the users in JVM memory and
 * data is lost when the application exits.
 *
 * This implementation is useful as temporary database or for testing.
 */

public class InMemoryUserDao implements UserDao {
    private Map<Integer, User> idToUser = new HashMap<>();

    /**
     * An eagerly evaluated stream of addresses stored in memory.
     */
    @Override
    public Stream<User> getAll() {
        return idToUser.values().stream();
    }

    @Override
    public Optional<User> getById(final int id) {
        return Optional.ofNullable((idToUser).get(id));
    }

    @Override
    public boolean add(final User user) {
        if (getById(user.getId()).isPresent()) {
            return false;
        }

        idToUser.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean update(final User user) {
        return idToUser.replace(user.getId(), user) != null;
    }

    @Override
    public boolean delete(final User user) {
        return idToUser.remove(user.getId()) != null;
    }
}
