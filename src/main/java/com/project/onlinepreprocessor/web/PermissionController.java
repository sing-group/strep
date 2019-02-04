package com.project.onlinepreprocessor.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.onlinepreprocessor.repositories.PermissionRepository;
import com.project.onlinepreprocessor.domain.Permission;


@Controller
@RequestMapping("/permission")
public class PermissionController
{
    @Autowired
    private PermissionRepository permissionRepository;


    //TODO: Replace this method to a real one, this method is only for test purposes
    @RequestMapping("/add")
    public String addNewPermission()
    {
        Permission permission = new Permission("Permiso 1", "El usuario puede realizar cualquier acción");
        permissionRepository.save(permission);
        return "saved";
    }

}