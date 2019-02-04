package com.project.onlinepreprocessor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.project.onlinepreprocessor.domain.User;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.BindingResult;

import com.project.onlinepreprocessor.repositories.UserRepository;
import com.project.onlinepreprocessor.forms.LoginForm;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

                if(bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword()))
                {
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