package org.strep.web;

import java.util.Optional;

import javax.validation.Valid;

import org.strep.domain.User;
import org.strep.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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
            //System.out.println("Binding problem");
            return "index";
        }
        else
        {
            Optional<User> optional = userRepository.findById(user.getUsername());

            if(optional.isPresent())
            {
                User userInDB = optional.get();
                //System.out.println("Usuario presente");

                if(bCryptPasswordEncoder.matches(user.getPassword(), userInDB.getEncryptedPassword()))
                {
                    //System.out.println("Contraseña correcta");
                    return "redirect:/user/main";
                }
                else
                    return "index";
            }
            else
            {
                //System.out.println("User not found problem");
                return "index";
            }  
        }

    }
}