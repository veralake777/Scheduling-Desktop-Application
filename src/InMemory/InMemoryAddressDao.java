package InMemory;

import Interface.AddressDao;
import POJO.Address;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * An in memory implementation of {@link AddressDao}, which stores the addresses in JVM memory and
 * data is lost when the application exits.
 *
 * This implementation is useful as temporary database or for testing.
 */

public class InMemoryAddressDao implements AddressDao {
    private Map<Integer, Address> idToAddress = new HashMap<>();

    /**
     * An eagerly evaluated stream of addresses stored in memory.
     */
    @Override
    public Stream<Address> getAll() {
        return idToAddress.values().stream();
    }

    @Override
    public Optional<Address> getById(final int id) {
        return Optional.ofNullable((idToAddress).get(id));
    }

    @Override
    public boolean add(final Address address) {
        if (getById(address.getId()).isPresent()) {
            return false;
        }

        idToAddress.put(address.getId(), address);
        return true;
    }

    @Override
    public boolean update(final Address address) {
        return idToAddress.replace(address.getId(), address) != null;
    }

    @Override
    public boolean delete(final Address address) {
        return idToAddress.remove(address.getId()) != null;
    }
}
