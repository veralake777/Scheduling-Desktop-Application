package Interface;

import DbDao.DbAddressDao;
import InMemory.InMemoryAddressDao;
import POJO.Address;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * resource: https://github.com/iluwatar/java-design-patterns/tree/master/dao
 * <p>
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
 * @see InMemoryAddressDao
 * @see DbAddressDao
 **/
public interface AddressDao {
    /**
     * Get all addresses
     *
     * @return all the addresses as a stream. The stream may be lazily or eagerly evaluated based on the implementation.
     * The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<Address> getAll() throws Exception;

    /**
     * Get Address as Optional by addressId
     *
     * @param id unique identifier of the address.
     * @return an optional with address if an address with unique identifier addressId exists, empty optional otherwise.
     * @throws Exception if any error occurs
     */
    Optional<Address> getById(int id) throws Exception;

    /**
     * Add an address.
     *
     * @param address the address to by updated.
     * @return true if address exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean add(Address address) throws Exception;

    /**
     * Update an address.
     *
     * @param address the address to be updated
     * @return true if address exists and is successfully updated, false otherwise.
     * @throws Exception if an error occurs
     */
    boolean update(Address address) throws Exception;

    /**
     * Delete a address.
     *
     * @param address the address to be deleted.
     * @return true if address exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(Address address) throws Exception;
}
