package org.strep.services;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import org.strep.domain.PermissionRequest;
import org.strep.domain.PermissionRequestPK;
import org.strep.domain.User;
import org.strep.repositories.PermissionRepository;
import org.strep.repositories.PermissionRequestRepository;
import org.strep.repositories.UserRepository;
import org.strep.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class to abstract to the controller of permission tasks not related to the presentation
 * @author Ismael Vázquez
 */
@Service
public class PermissionService
{
    /**
     * The repository to access permission data
     */
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * The repository to access permission request data
     */
    @Autowired
    private PermissionRequestRepository permissionRequestRepository;

    /**
     * The repository to access user data
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;    

    /**
     * Method to add a permission request for the especified user and permission
     * @param username the user username
     * @param id the permission id
     * @return A description message of the performed operations
     */
    public String addPermissionRequest(String username, int id)
    {
        Locale locale = LocaleContextHolder.getLocale();
        
        String message = "";

        //ArrayList<BigInteger> permissions = permissionRepository.getUserPermissions(username);
        Long maxPerm = 1L;
        
        Optional<User> userOpt=userRepository.findById(username);
        Optional<Permission> permissionOpt=permissionRepository.findById(new Long(id));
        if (!userOpt.isPresent() || !permissionOpt.isPresent()) {
            message = messageSource.getMessage("add.permissionrequest.fail.requesterror", Stream.of().toArray(String[]::new), locale);
            return message;
        }

        Optional<PermissionRequest> optPermRequest = permissionRequestRepository.findById(new PermissionRequestPK(userOpt.get(),permissionOpt.get()));
        if(optPermRequest.isPresent())
        {
            message = messageSource.getMessage("add.permissionrequest.fail.alreadyregistered", Stream.of().toArray(String[]::new), locale);
        }
        else{
                //for(BigInteger permissionId : permissions)
                //{
                //    int permissionIdInt = permissionId.intValue();
                //    if(permissionIdInt>maxPerm)
                //    {
                //        maxPerm=permissionIdInt;
                //    }
                //}
                Optional<User> optUser = userRepository.findById(username);
                maxPerm=optUser.isPresent()?optUser.get().getPermission().getId():1L;

                if(id<maxPerm || id>4)
                {
                    message = messageSource.getMessage("add.permissionrequest.fail.requesterror", Stream.of().toArray(String[]::new), locale);
                }
                else
                {
                    
                    Optional<Permission> optPermission = permissionRepository.findById(new Long(id));

                    if(optUser.isPresent() && optPermission.isPresent())
                    {
                        PermissionRequest permissionRequest = new PermissionRequest(optUser.get(), optPermission.get());
                        permissionRequest.setStatus("pending");
                        permissionRequestRepository.save(permissionRequest);
                        message = messageSource.getMessage("add.permissionrequest.sucess", Stream.of().toArray(String[]::new), locale);
                    }
                    else
                    {
                        message = messageSource.getMessage("add.permissionrequest.fail.userorpermnotfound", Stream.of().toArray(String[]::new), locale);
                    }
                }
            }

        return message;
    }

    /**
     * Method to accept a permission request
     * @param permissionRequestPK the combined permission request key
     * @return A description message of the performed operations
     */ 
    public String acceptPermission(PermissionRequestPK permissionRequestPK)
    {
        Locale locale = LocaleContextHolder.getLocale();

        String message = "";

        Optional<PermissionRequest> permissionRequestOpt = permissionRequestRepository.findById(new PermissionRequestPK( permissionRequestPK.getUser(),permissionRequestPK.getPermission()));
        Optional<User> userOpt = userRepository.findById(permissionRequestPK.getUser().getUsername());
        Optional<Permission> permOpt = permissionRepository.findById(permissionRequestPK.getPermission().getId());
        if(permissionRequestOpt.isPresent() && userOpt.isPresent() && permOpt.isPresent() )
        {
            //Authorize request
            PermissionRequest permissionRequest = permissionRequestOpt.get();
            permissionRequest.setStatus("accepted");
            permissionRequestRepository.save(permissionRequest);

            //Change the permission
            User user=userOpt.get();
            user.setPermission(permOpt.get());
            userRepository.save(user);

            message = messageSource.getMessage("accept.permissionrequest.sucess", Stream.of().toArray(String[]::new), locale);
        } else
            message = messageSource.getMessage("accept.permissionrequest.fail", Stream.of().toArray(String[]::new), locale);

        return message;
    }

    /**
     * Method to reject a permission request
     * @param permissionRequestPK the combined permission request key
     * @return A description message of the performed operations
     */ 
    public String rejectPermission(PermissionRequestPK permissionRequestPK)
    {
        Locale locale = LocaleContextHolder.getLocale();
        String message = "";

        Optional<Permission> permissionOpt = permissionRepository.findById(permissionRequestPK.getPermission().getId());
        Optional<User> userOpt = userRepository.findById(permissionRequestPK.getUser().getUsername());

        if(userOpt.isPresent() && permissionOpt.isPresent())
        {
            User requestUser = userOpt.get();
            Permission requestPermission = permissionOpt.get();

            PermissionRequest permissionRequest = new PermissionRequest(requestUser, requestPermission);

            permissionRequestRepository.delete(permissionRequest);
            message = messageSource.getMessage("reject.permissionrequest.sucess", Stream.of().toArray(String[]::new), locale);
        }
        else
        {
            message = messageSource.getMessage("reject.permissionrequest.fail", Stream.of().toArray(String[]::new), locale);
        }

        return message; 
    }
}