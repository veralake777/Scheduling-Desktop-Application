package Interface;

import POJO.City;

import java.util.Optional;
import java.util.stream.Stream;

public interface CityDao {
    /**
     * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
     *
     * In an application the Data Access Object (DAO) is a part of Data access layer. It is an object
     * that provides an interface to some type of persistence mechanism. By mapping application calls to
     * the persistence layer, DAO provides some specific data operations without exposing details of the
     * database. This isolation supports the Single responsibility principle. It separates what data
     * accesses the application needs, in terms of domain-specific objects and data types (the public
     * interface of the DAO), from how these needs can be satisfied with a specific DBMS, database
     * schema, etc.
     *
     * <p>Any change in the way data is stored and retrieved will not change the client code as the
     * client will be using interface and need not worry about exact source.
     *
     *  @see InMemoryCityDao
     *  @see DbCityDao
     **/

    /**
     * Get all citys
     *
     * @return all the cities as a stream. The stream may be lazily or eagerly evaluated based on the implementation.
     * The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<City> getAll() throws Exception;

    /**
     * Get city as Optional by id
     *
     * @param id unique identifier of the city.
     * @return an optional with city if an city with unique identifier cityId exists, empty optional otherwise.
     * @throws Exception if any error occurs
     */
    Optional<City> getById(int id) throws Exception;

    /**
     * Add an city.
     *
     * @param city the city to by updated.
     * @return true if city exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean add(City city) throws Exception;

    /**
     * Update an city.
     *
     * @param city the city to be updated
     * @return true if city exists and is successfully updated, false otherwise.
     * @throws Exception if an error occurs
     */
    boolean update(City city) throws Exception;

    /**
     * Delete a city.
     *
     * @param city the city to be deleted.
     * @return true if city exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(City city) throws Exception;
}
