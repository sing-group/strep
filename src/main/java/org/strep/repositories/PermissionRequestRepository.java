package org.strep.repositories;

import java.util.ArrayList;

import org.strep.domain.PermissionRequest;
import org.strep.domain.PermissionRequestPK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for PermissionRequest objects
 * @author Ismael Vázquez
 */
public interface PermissionRequestRepository extends CrudRepository<PermissionRequest, PermissionRequestPK>
{
    /**
     * Return the requests of the specified user
     * @param username the username of the user
     * @return A list of the permission requests for the specified users
     */
    @Query(value="SELECT * from permission_request where user_username=?1", nativeQuery=true)
    public ArrayList<PermissionRequest> findRequestsByUsername(String username);

    /**
     * Return the pending requests of the specified user
     * @param username the username of the user
     * @return A list of the permission requests for the specified user
     */
    @Query(value="SELECT * from permission_request where user_username=?1 and status='pending'", nativeQuery=true)
    public ArrayList<PermissionRequest> findPendingRequestsByUsername(String username);

    /**
     * Return the pending requests
     * @return A list of the pending permission requests
     */
    @Query(value="SELECT * from permission_request where status='pending'", nativeQuery=true)
    public ArrayList<PermissionRequest> findPendingRequests();
}