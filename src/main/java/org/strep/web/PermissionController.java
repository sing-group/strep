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
package org.strep.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import org.strep.domain.Permission;
import org.strep.domain.PermissionRequest;
import org.strep.domain.PermissionRequestPK;
import org.strep.domain.User;
import org.strep.repositories.PermissionRepository;
import org.strep.repositories.PermissionRequestRepository;
import org.strep.repositories.UserRepository;
import org.strep.services.PermissionService;
import org.strep.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    
    @Autowired
    private JavaMailSender mailSender;
    
    /**
     * The message i18n
     */
    @Autowired
    MessageSource messageSource;

    @GetMapping("/list")
    public String listPermissions(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        return "show_permissions";
    }

    @PostMapping("/request")
    public String addRequest(Authentication authentication, @RequestParam("id") int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String message = permissionService.addPermissionRequest(username, id);

        // Send mail to admin
        Locale locale = LocaleContextHolder.getLocale();
        ArrayList<User> usersList = userRepository.findUserByPermission(Permission.ADMINISTER);
        List<String> toList = new ArrayList<>();
        usersList.forEach((u) -> {
            toList.add(u.getEmail());
        });

        SimpleMailMessage mailMessageToAdmins = new SimpleMailMessage();
        mailMessageToAdmins.setTo(toList.stream().toArray(String[]::new));
        mailMessageToAdmins.setSubject(messageSource.getMessage("permission.request.subject.toadmin", null, locale));
        mailMessageToAdmins.setText(messageSource.getMessage("permission.request.text.toadmin", Stream.of(username).toArray(String[]::new), locale));
        mailSender.send(mailMessageToAdmins);

        return "redirect:/permission/requests?message=" + message;
    }

    @GetMapping("/requests")
    public String showAllRequests(Authentication authentication, Model model, @RequestParam(value = "message", required = false) String message) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        ArrayList<PermissionRequest> permissionRequests = permissionRequestRepository.findRequestsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("authority", authority);
        model.addAttribute("permissionRequests", permissionRequests);
        model.addAttribute("photo", optUser.get().getPhoto());

        if (message != null) {
            model.addAttribute("message", message); //TODO: This does not seem adequate
        }

        return "list_requests";
    }

    @GetMapping("/listrequests")
    public String showAllPermissionRequests(Authentication authentication, Model model, @RequestParam(name = "message", required = false) String message,
            @RequestParam(name = "searchInput", required = false) String search) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("authority", authority);
        model.addAttribute("photo", optUser.get().getPhoto());

        ArrayList<PermissionRequest> permissionRequests;

        if (search == null) {
            permissionRequests = permissionRequestRepository.findPendingRequests();
        } else {
            permissionRequests = permissionRequestRepository.findPendingRequestsByUsername(username);
        }
        model.addAttribute("permissionRequests", permissionRequests);

        if (message != null) {
            model.addAttribute("message", message);
        }

        return "list_permission_requests";
    }

    @PostMapping("accept")
    public String acceptPermissionRequest(Authentication authentication, Model model, @RequestParam("username") String username, @RequestParam("permission") int permission) {
        Optional<User> userOpt = userRepository.findById(username);
        Optional<Permission> permissionOpt = permissionRepository.findById(new Long(permission));

        if (!userOpt.isPresent() || !permissionOpt.isPresent()) //TODO: revise this message
        {
            return "redirect:/permission/listrequests?message=Invalid";
        }

        PermissionRequestPK permissionRequestPK = new PermissionRequestPK(userOpt.get(), permissionOpt.get());

        String message = permissionService.acceptPermission(permissionRequestPK);

        return "redirect:/permission/listrequests?message=" + message;
    }

    @PostMapping("reject")
    public String rejectPermissionRequest(Authentication authentication, Model model, @RequestParam("username") String username, @RequestParam("permission") int permission) {
        Optional<User> userOpt = userRepository.findById(username);
        Optional<Permission> permissionOpt = permissionRepository.findById(new Long(permission));

        if (!userOpt.isPresent() || !permissionOpt.isPresent()) //TODO: revise this message
        {
            return "redirect:/permission/listrequests?message=Invalid";
        }

        PermissionRequestPK permissionRequestPK = new PermissionRequestPK(userOpt.get(), permissionOpt.get());

        String message = permissionService.rejectPermission(permissionRequestPK);

        return "redirect:/permission/listrequests?message=" + message;
    }
}
