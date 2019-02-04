package com.project.onlinepreprocessor.services;

import com.project.onlinepreprocessor.repositories.PermissionRepository;
import com.project.onlinepreprocessor.repositories.UserRepository;

import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.domain.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service("userService")
public class UserService
{
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void saveUser(User user)
    {
        Permission permissionTest = new Permission( "Admin", "The user has administer permissions");
        permissionRepository.save(permissionTest);

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
}
