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

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.strep.domain.License;
import org.strep.domain.User;
import org.strep.repositories.LicenseRepository;
import org.strep.services.LicenseService;
import org.strep.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.strep.repositories.UserRepository;

/**
 * This controller responds to all requests related to licenses
 */
@Controller
@RequestMapping("/license")
public class LicenseController
{

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LicenseService licenseService;

    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;    

    @GetMapping("/list")
    public String listLicenses(Authentication authentication, Model model, @RequestParam(name="searchInput", required=false)String searchInput)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());
        
        Iterable<License> licenses;

        if(searchInput==null)
        {
            licenses = licenseRepository.findAll();
        }
        else
        {
            licenses = licenseRepository.findByName(searchInput);
        }

        model.addAttribute("licenses", licenses);

        return "list_licenses";
    }

    @GetMapping("/detailed")
    public String showDetailedLicense(Authentication authentication, Model model, @RequestParam("name")String name)
    {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        Optional<License> licenseOpt = licenseRepository.findById(name);

        if(licenseOpt.isPresent())
        {
            License license = licenseOpt.get();
            String description = licenseService.formatLicenseDescription(license);
            model.addAttribute("license", license);
            model.addAttribute("description", description);
        }

        return "detailed_license";
    }

    @GetMapping("/add")
    public String addLicense(Authentication authentication, Model model, License license)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        return "add_license";
    }

    @PostMapping("/add")
    public String addLicense(Authentication authentication, Model model, @Valid License license,BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        System.out.println("license redistribute: " + license.isRedistribute());
        Locale locale = LocaleContextHolder.getLocale();
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);
        
        model.addAttribute("photo", optUser.get().getPhoto());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "add_license";
        }
        else
        {
            Optional<License> licenseOpt = licenseRepository.findById(license.getName());
            if(licenseOpt.isPresent())
            {
                redirectAttributes.addFlashAttribute("message", 
                    messageSource.getMessage("add.license.fail.licensealreadyexist", Stream.of().toArray(String[]::new), locale)
                    //"License already exists"
                );
            }
            else
            {
                licenseRepository.save(license);
                redirectAttributes.addFlashAttribute("message", 
                    messageSource.getMessage("add.license.sucess", Stream.of().toArray(String[]::new), locale)
                );
            }
            return "redirect:/license/list";
        }
    }

    @GetMapping("/modify")
    public String modifyLicense(Authentication authentication, Model model, License license, @RequestParam("name") String name)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        Optional<License> licenseOpt = licenseRepository.findById(name);
        if(licenseOpt.isPresent())
        {
            License licenseToModify = licenseOpt.get();
            String description = new String(licenseToModify.getDescription());
            model.addAttribute("licenseToModify", licenseToModify);
            model.addAttribute("description", description);
        }

        return "modify_license";
    }


    @PostMapping("/modify")
    public String modifyLicense(Authentication authentication, Model model, @Valid License license,BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        Locale locale = LocaleContextHolder.getLocale();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("photo", optUser.get().getPhoto());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "modify_license";
        }
        else
        {
            System.out.println("adapt; " + license.isAdaptWork());
            
            licenseRepository.save(license);
            redirectAttributes.addFlashAttribute("message", 
                messageSource.getMessage("modify.license.sucess", Stream.of().toArray(String[]::new), locale)
            );
            
            return "redirect:/license/list";
        }

    }

    @GetMapping("/delete")
    public String deleteLicense(Authentication authentication, Model model, @RequestParam("name")String name, RedirectAttributes redirectAttributes)
    {
        Locale locale = LocaleContextHolder.getLocale();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);
        String message = "";

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        Optional<License> licenseOpt = licenseRepository.findById(name);

        if(licenseOpt.isPresent())
        {
            licenseRepository.delete(licenseOpt.get());
            message=messageSource.getMessage("delete.license.sucess", Stream.of().toArray(String[]::new), locale);
            redirectAttributes.addFlashAttribute("message", message);
        }
        else
        {
            message=messageSource.getMessage("delete.license.fail", Stream.of().toArray(String[]::new), locale);
        }

        return "redirect:/license/list";
    }

}
