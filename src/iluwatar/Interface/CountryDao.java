package iluwatar.Interface;

import iluwatar.POJO.Country;

import java.util.Optional;
import java.util.stream.Stream;

public interface CountryDao {
    /**
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
     *  @see InMemoryCountryDao
     *  @see DbCountryDao
     **/

    /**
     * Get all countrys
     *
     * @return all the countries as a stream. The stream may be lazily or eagerly evaluated based on the implementation.
     *         The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<Country> getAll() throws Exception;

    /**
     * Get country as Optional by id
     *
     * @param id unique identifier of the country.
     * @return an optional with country if an country with unique identifier countryId exists, empty optional otherwise.
     * @throws Exception if any error occurs
     */
    Optional<Country> getById(int id) throws Exception;

    /**
     * Add an country.
     *
     * @param country the country to by updated.
     * @return true if country exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean add(Country country) throws Exception;

    /**
     * Update an country.
     *
     * @param country the country to be updated
     * @return true if country exists and is successfully updated, false otherwise.
     * @throws Exception if an error occurs
     */
    boolean update(Country country) throws Exception;

    /**
     * Delete a country.
     *
     * @param country the country to be deleted.
     * @return true if country exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(Country country) throws Exception;
}
