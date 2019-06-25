package com.project.onlinepreprocessor.web;

import java.util.ArrayList;

import com.project.onlinepreprocessor.domain.PermissionRequest;
import com.project.onlinepreprocessor.domain.PermissionRequestPK;
import com.project.onlinepreprocessor.repositories.PermissionRequestRepository;
import com.project.onlinepreprocessor.services.PermissionService;
import com.project.onlinepreprocessor.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller responds to all requests related to permissions
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRequestRepository permissionRequestRepository;

    @GetMapping("/list")
    public String listPermissions(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        return "show_permissions";
    }

    @PostMapping("/request")
    public String addRequest(Authentication authentication, @RequestParam("id")int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String message = permissionService.addPermissionRequest(username, id);

        return "redirect:/permission/requests?message="+message;
    }

    @GetMapping("/requests")
    public String showAllRequests(Authentication authentication, Model model,@RequestParam(value="message",required=false)String message)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        ArrayList<PermissionRequest> permissionRequests = permissionRequestRepository.findRequestsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("authority", authority);
        model.addAttribute("permissionRequests", permissionRequests);
        
        if(message!=null)
        {
            model.addAttribute("message", message);
        }
        
        return "list_requests";
    }

    @GetMapping("/listrequests")
    public String showAllPermissionRequests(Authentication authentication, Model model, @RequestParam(name="message", required=false)String message, 
    @RequestParam(name="searchInput", required=false)String search)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("authority", authority);

        ArrayList<PermissionRequest> permissionRequests;

        if(search==null)
        {
            permissionRequests = permissionRequestRepository.findPendingRequests();
        }
        else
        {
            permissionRequests = permissionRequestRepository.findPendingRequestsByUsername(username);
        }
        model.addAttribute("permissionRequests", permissionRequests);

        if(message!=null)
        {
            model.addAttribute("message", message);
        }

        return "list_permission_requests";
    }

    @PostMapping("accept")
    public String acceptPermissionRequest(Authentication authentication, Model model, @RequestParam("username")String username, @RequestParam("permission")int permission)
    {
        PermissionRequestPK permissionRequestPK = new PermissionRequestPK(new Long(permission), username);

        String message = permissionService.acceptPermission(permissionRequestPK);

        return "redirect:/permission/listrequests?message="+message;
    }

    @PostMapping("reject")
    public String rejectPermissionRequest(Authentication authentication, Model model, @RequestParam("username") String username, @RequestParam("permission") int permission)
    {
        PermissionRequestPK permissionRequestPK = new PermissionRequestPK(new Long(permission), username);

        String message = permissionService.rejectPermission(permissionRequestPK);

        return "redirect:/permission/listrequests?message="+message;
    }
}