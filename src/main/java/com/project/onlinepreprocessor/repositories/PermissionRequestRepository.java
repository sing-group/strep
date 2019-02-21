package com.project.onlinepreprocessor.repositories;

import java.util.ArrayList;

import com.project.onlinepreprocessor.domain.PermissionRequest;
import com.project.onlinepreprocessor.domain.PermissionRequestPK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRequestRepository extends CrudRepository<PermissionRequest, PermissionRequestPK>
{
    @Query(value="SELECT * from permission_request where user_username=?1", nativeQuery=true)
    public ArrayList<PermissionRequest> findRequestsByUsername(String username);
}