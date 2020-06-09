package InMemory;

import Interface.CityDao;
import POJO.City;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
 *
 * An in memory implementation of {@link CityDao}, which stores the cities in JVM memory and
 * data is lost when the application exits.
 *
 * This implementation is useful as temporary database or for testing.
 */

public class InMemoryCityDao implements CityDao {
    private Map<Integer, City> idToCity = new HashMap<>();

    /**
     * An eagerly evaluated stream of addresses stored in memory.
     */
    @Override
    public Stream<City> getAll() {
        return idToCity.values().stream();
    }

    @Override
    public Optional<City> getById(final int id) {
        return Optional.ofNullable((idToCity).get(id));
    }

    @Override
    public boolean add(final City city) {
        if (getById(city.getId()).isPresent()) {
            return false;
        }

        idToCity.put(city.getId(), city);
        return true;
    }

    @Override
    public boolean update(final City city) {
        return idToCity.replace(city.getId(), city) != null;
    }

    @Override
    public boolean delete(final City city) {
        return idToCity.remove(city.getId()) != null;
    }
}
