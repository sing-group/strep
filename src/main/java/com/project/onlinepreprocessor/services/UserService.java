package com.project.onlinepreprocessor.services;

import com.project.onlinepreprocessor.repositories.PermissionRepository;
import com.project.onlinepreprocessor.repositories.UserRepository;

import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * Service class to abstract to the controller of user tasks not related to the presentation
 * @author Ismael Vázquez
 */
@Service("userService")
public class UserService
{
    /**
     * The repository to access user data
     */
    private UserRepository userRepository;

    /**
     * The repository to access permission data
     */
    private PermissionRepository permissionRepository;

    /**
     * The password encoder object
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The constructor for create instances of UserService
     * @param userRepository the user repository
     * @param permissionRepository the permission repository
     * @param bCryptPasswordEncoder the password encoder
     */
    @Autowired
    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Save the specified user
     * @param user the specified user
     */
    public void saveUser(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmedAccount(false);

        Optional<Permission> opt = permissionRepository.findById(new Long(1));

        if(opt.isPresent())
        {
        Permission permission = opt.get();
        user.setPermissions(new HashSet<Permission>(Arrays.asList(permission)));
        userRepository.save(user);
        }
    }

    /**
     * Return the max authority for the specified user
     * @param username the user username
     * @return the max authority for the specified user
     */
    public String getPermissionsByUsername(String username)
    {
        ArrayList<BigInteger> permissions = permissionRepository.getUserPermissions(username);

        Long maxId = new Long(Long.MIN_VALUE);

        for(int i = 0; i<permissions.size(); i++)
        {
            if(maxId<permissions.get(i).longValue())
            {
                maxId = permissions.get(i).longValue();
            }
        }

        String authority = convertPermIdToString(maxId);

        return authority;
    }

    /**
     * Edit the permissions for the specified user
     * @param permissionIntId the permission id 
     * @param username the user username
     * @return a description message for the performed operations
     */
    public String editPermissions(int permissionIntId, String username)
    {
        String message = "Cannot edit permissions";
        Long permissionLongId = new Long(permissionIntId);

        Optional<Permission> permissionOpt = permissionRepository.findById(permissionLongId);

        if(permissionOpt.isPresent())
        {
            Permission permission = permissionOpt.get();

            permissionRepository.deletePermissions(username);
            for(int i = 1; i<=permission.getId().intValue();i++)
            {
                permissionRepository.addPermission(username, i);
            }
            message = "User permissions changed";
        }

        return message;
    }

    /**
     * This method uploads a photo of the specified user
     * @param username the user username
     * @param multipartFile the uploaded photo
     */
    public void editProfile(String username, MultipartFile multipartFile)
    {
        Optional<User> optUser = userRepository.findById(username);

        if(optUser.isPresent())
        {
            try
            {
                User user = optUser.get();
                user.setPhoto(multipartFile.getBytes());
                userRepository.save(user);
            }
            catch(Exception e)
            {

            }
        }

    }

    /**
     * Auxiliar method to convert perm id to String
     * @param maxId the id of the max permission of the user
     * @return perm id converted to String
     */
    private String convertPermIdToString(Long maxId)
    {
        switch(maxId.intValue())
        {
            case 1:
            return "canView";
            case 2:
            return "canCreateCorpus";
            case 3:
            return "canUpload";
            case 4:
            return "canAdminister";
            default: 
            return "canView";
        }
    }


}
