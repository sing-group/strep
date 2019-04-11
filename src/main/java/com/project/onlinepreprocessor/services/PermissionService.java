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

    public String acceptPermission(PermissionRequestPK permissionRequestPK)
    {
        String message = "";

        for(int i = 1; i<=permissionRequestPK.getPermissionId().intValue();i++)
        {
        Optional<PermissionRequest> permissionRequestOpt = permissionRequestRepository.findById(new PermissionRequestPK(new Long(i), permissionRequestPK.getUserUsername()));

        if(permissionRequestOpt.isPresent())
        {
            PermissionRequest permissionRequest = permissionRequestOpt.get();
            permissionRequest.setStatus("accepted");
            permissionRequestRepository.save(permissionRequest);
        }

        String username = permissionRequestPK.getUserUsername();
        if(permissionRepository.havePermission(username, new Long(i))==0)
        {
            permissionRepository.addPermission(username, i);
        }

        }

        message = "Successfully accepted";

        return message;
    }

    public String rejectPermission(PermissionRequestPK permissionRequestPK)
    {
        String message = "";

        Optional<Permission> permissionOpt = permissionRepository.findById(permissionRequestPK.getPermissionId());
        Optional<User> userOpt = userRepository.findById(permissionRequestPK.getUserUsername());

        if(userOpt.isPresent() && permissionOpt.isPresent())
        {
            User requestUser = userOpt.get();
            Permission requestPermission = permissionOpt.get();

            PermissionRequest permissionRequest = new PermissionRequest(requestUser, requestPermission);

            permissionRequestRepository.delete(permissionRequest);
            message = "Permission request rejected";
        }
        else
        {
            message = "Cannot reject the permission request";
        }

        return message; 
    }
}