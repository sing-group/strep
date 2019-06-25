package com.project.onlinepreprocessor.web;

import java.util.Optional;

import javax.validation.Valid;

import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.forms.LoginForm;
import com.project.onlinepreprocessor.repositories.UserRepository;

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
    public String indexPage(LoginForm loginForm)
    {
        return "index";
    }

    @PostMapping("/")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "index";
        }
        else
        {
            Optional<User> optional = userRepository.findById(loginForm.getUsername());

            if(optional.isPresent())
            {
                User user = optional.get();
                System.out.println("Usuario presente");

                if(bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword()))
                {
                    System.out.println("Contraseña correcta");
                    return "redirect:/user/main";
                }
                else
                    return "index";
            }
            else
                return "index";

            }

    }
}