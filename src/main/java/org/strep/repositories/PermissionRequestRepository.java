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
    @Query(value="SELECT * FROM permission_request WHERE user_username=?1", nativeQuery=true)
    public ArrayList<PermissionRequest> findRequestsByUsername(String username);

    /**
     * Return the pending requests of the specified user
     * @param username the username of the user
     * @return A list of the permission requests for the specified user
     */
    @Query(value="SELECT * from permission_request WHERE user_username=?1 AND status='pending'", nativeQuery=true)
    public ArrayList<PermissionRequest> findPendingRequestsByUsername(String username);

    /**
     * Return the pending requests
     * @return A list of the pending permission requests
     */
    @Query(value="SELECT * from permission_request WHERE status='pending'", nativeQuery=true)
    public ArrayList<PermissionRequest> findPendingRequests();
}
