package InMemory;

import Interface.CountryDao;
import POJO.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
 * <p>
 * An in memory implementation of {@link CountryDao}, which stores the countries in JVM memory and
 * data is lost when the application exits.
 * <p>
 * This implementation is useful as temporary database or for testing.
 */

public class InMemoryCountryDao implements CountryDao {
    private Map<Integer, Country> idToCountry = new HashMap<>();

    /**
     * An eagerly evaluated stream of addresses stored in memory.
     */
    @Override
    public Stream<Country> getAll() {
        return idToCountry.values().stream();
    }

    @Override
    public Optional<Country> getById(final int id) {
        return Optional.ofNullable((idToCountry).get(id));
    }

    @Override
    public boolean add(final Country country) {
        if (getById(country.getId()).isPresent()) {
            return false;
        }

        idToCountry.put(country.getId(), country);
        return true;
    }

    @Override
    public boolean update(final Country country) {
        return idToCountry.replace(country.getId(), country) != null;
    }

    @Override
    public boolean delete(final Country country) {
        return idToCountry.remove(country.getId()) != null;
    }
}
