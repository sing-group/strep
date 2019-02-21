package com.project.onlinepreprocessor.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.PermissionRequest;
import com.project.onlinepreprocessor.domain.PermissionRequestPK;
import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.repositories.PermissionRepository;
import com.project.onlinepreprocessor.repositories.PermissionRequestRepository;
import com.project.onlinepreprocessor.repositories.UserRepository;
import com.project.onlinepreprocessor.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService
{
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionRequestRepository permissionRequestRepository;

    @Autowired
    private UserRepository userRepository;


    public String addPermissionRequest(String username, int id)
    {
        String message = "";

        ArrayList<BigInteger> permissions = permissionRepository.getUserPermissions(username);
        int maxPerm = 1;
        
        Optional<PermissionRequest> optPermRequest = permissionRequestRepository.findById(new PermissionRequestPK(new Long(id),username ));
        if(optPermRequest.isPresent())
        {
            message = "You already have a request for this permission";
        }
        else{
                for(BigInteger permissionId : permissions)
                {
                    int permissionIdInt = permissionId.intValue();
                    if(permissionIdInt>maxPerm)
                    {
                        maxPerm=permissionIdInt;
                    }
                }

                if(id<maxPerm || id>4)
                {
                    message = "Request error";
                }
                else
                {
                    Optional<User> optUser = userRepository.findById(username);
                    Optional<Permission> optPermission = permissionRepository.findById(new Long(id));

                    if(optUser.isPresent() && optPermission.isPresent())
                    {
                    PermissionRequest permissionRequest = new PermissionRequest(optUser.get(), optPermission.get());
                    permissionRequest.setStatus("pending");
                    permissionRequest.setId(new PermissionRequestPK(optPermission.get().getId(), optUser.get().getUsername()));
                    permissionRequestRepository.save(permissionRequest);
                    message = "Request sent";
                    }
                    else
                    {
                        message = "Request error";
                    }
                }
            }

        return message;
    }
}