/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
