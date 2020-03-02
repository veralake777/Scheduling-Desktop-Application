package iluwatar.Interface;

import iluwatar.POJO.User;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserDao {
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
     *  @see InMemoryUserDao
     *  @see DbUserDao
     **/

    /**
     * Get all users
     *
     * @return all the users as a stream. The stream may be lazily or eagerly evaluated based on the implementation.
     *         The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<User> getAll() throws Exception;

    /**
     * Get user as Optional by id
     *
     * @param id unique identifier of the user.
     * @return an optional with user if an user with unique identifier userId exists, empty optional otherwise.
     * @throws Exception if any error occurs
     */
    Optional<User> getById(int id) throws Exception;

    /**
     * Add an user.
     *
     * @param user the user to by updated.
     * @return true if user exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean add(User user) throws Exception;

    /**
     * Update an user.
     *
     * @param user the user to be updated
     * @return true if user exists and is successfully updated, false otherwise.
     * @throws Exception if an error occurs
     */
    boolean update(User user) throws Exception;

    /**
     * Delete a user.
     *
     * @param user the user to be deleted.
     * @return true if user exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(User user) throws Exception;
}
