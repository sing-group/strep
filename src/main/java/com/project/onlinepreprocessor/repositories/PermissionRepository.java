package com.project.onlinepreprocessor.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.transaction.Transactional;

import com.project.onlinepreprocessor.domain.Permission;


/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for Permission objects
 * @author Ismael Vázquez
 */

public interface PermissionRepository extends CrudRepository<Permission, Long> {

    /**
     * Return the permissions of the specified user
     * @param username the username of the user
     * @return the permissions of the specified user
     */
    @Query(value="SELECT perm_id from user_perm where user=?1",nativeQuery=true)
    public ArrayList<BigInteger> getUserPermissions(String username);

    /**
     * Delete the permissions of the specified user
     * @param username the username of the user
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE from user_perm where user=?1", nativeQuery=true)
    public void deletePermissions(String username);

    /**
     * Insert the specified permission to the specified user
     * @param username the username of the user
     * @param permission the permission to insert for the user
     */
    @Modifying
    @Transactional
    @Query(value = "insert into user_perm(user, perm_id) values(?1, ?2)", nativeQuery=true)
    public void addPermission(String username, int permission);

    /**
     * Return the count of the permissions for the user
     * @param username the username of the user
     * @param id the id of the permission   
     * @return the count of the permissions
     */
    @Query(value = "SELECT COUNT(perm_id) from user_perm where user=?1 and perm_id=?2", nativeQuery=true)
    public int havePermission(String username, Long id);

}