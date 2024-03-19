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

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.strep.domain.User;
import org.strep.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * This controller responds to all requests related to login
 */
@Controller 
public class LoginController 
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String indexPage(User user)
    {
        return "index";
    }

    @PostMapping("/")
    public String login(@Valid User user, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "index";
        }
        else
        {
            Optional<User> optional = userRepository.findById(user.getUsername());

            if(optional.isPresent())
            {
                User userInDB = optional.get();

                if(bCryptPasswordEncoder.matches(user.getPassword(), userInDB.getEncryptedPassword()))
                {
                    return "redirect:/user/main";
                }
                else
                    return "index";
            }
            else
            {
                return "index";
            }  
        }

    }


    @GetMapping("/changeLang")
    public String changeLanguage(HttpSession session,@RequestParam(name="lang", required=true) String lang)
    {
        session.setAttribute("lang", lang);        
        return "header::locales";
    }

}
