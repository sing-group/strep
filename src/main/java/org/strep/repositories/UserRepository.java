package org.strep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.strep.domain.User;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations
 * with added queries for User objects
 *
 * @author Ismael Vázquez
 */
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Return the user with that hash
     *
     * @param hash the hash of the user
     * @return the user with that hash
     */
    @Query(
            value = "SELECT * FROM user u WHERE hash=?1",
            nativeQuery = true)
    Optional<User> findUserByHash(String hash);

    /**
     * Return the user with that email
     *
     * @param email the email of the user
     * @return the user with that email
     */
    @Query(value = "SELECT * FROM user u WHERE email=?1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    /**
     * Return a list of the user with username like input
     *
     * @param inputSearch the input for filter users
     * @return a list of the user with username like input
     */
    @Query(value = "SELECT * FROM user WHERE username LIKE %?1%", nativeQuery = true)
    ArrayList<User> searchUsers(String inputSearch);

    /**
     * Return a list of users with this permission
     *
     * @param permission the permission of users
     * @return the list of users with this permission
     */
    @Query(value = "SELECT u FROM User u WHERE u.permission.name=?1")
    ArrayList<User> findUserByPermission(String permission);
}
