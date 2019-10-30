package org.strep.services;

import org.strep.repositories.PermissionRepository;
import org.strep.repositories.UserRepository;

import org.strep.domain.User;
import org.strep.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;    

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
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmedAccount(false);

        Optional<Permission> opt = permissionRepository.findById(new Long(1));

        if(opt.isPresent())
        {
            Permission permission = opt.get();
            user.setPermission(permission);
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
        try{
            return convertPermIdToString(userRepository.findById(username).get().getPermission().getId());
        }catch(NoSuchElementException e){
            return convertPermIdToString(1L);
        }
    }

    /**
     * Edit the permissions for the specified user
     * @param permissionIntId the permission id 
     * @param username the user username
     * @return a description message for the performed operations
     */
    public String editPermissions(int permissionIntId, String username)
    {
        Locale locale = LocaleContextHolder.getLocale();
        
        String message = messageSource.getMessage("edit.permission.fail", Stream.of().toArray(String[]::new), locale);
        
        Long permissionLongId = new Long(permissionIntId);

        Optional<Permission> permissionOpt = permissionRepository.findById(permissionLongId);
        Optional<User> userOpt = userRepository.findById(username);
        if(permissionOpt.isPresent() && userOpt.isPresent())
        {
            Permission permission = permissionOpt.get();
            userOpt.get().setPermission(permission);

            //permissionRepository.deletePermissions(username);
            //for(int i = 1; i<=permission.getId().intValue();i++)
            //{
            //    permissionRepository.addPermission(username, i);
            //}
            message = messageSource.getMessage("edit.permission.sucess", Stream.of().toArray(String[]::new), locale);
        }else{
            message = messageSource.getMessage("edit.permission.fail", Stream.of().toArray(String[]::new), locale);
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
     * @param id the id of the max permission of the user
     * @return perm id converted to String
     */
    private String convertPermIdToString(Long id)
    {
        switch(id.intValue())
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
