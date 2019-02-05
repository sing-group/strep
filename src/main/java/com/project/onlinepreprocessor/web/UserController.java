package com.project.onlinepreprocessor.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import com.project.onlinepreprocessor.services.UserService;
import com.project.onlinepreprocessor.domain.User;

import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.*;
import javax.validation.Valid;

import com.project.onlinepreprocessor.forms.RegisterForm;
import com.project.onlinepreprocessor.repositories.UserRepository;

import java.util.Optional;



@Controller
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender sender;


    @GetMapping("/register")
    public String register(RegisterForm registerForm)
    {
        return "register";
    }


    //TODO: Validate form fields before insert user in the database
    //TODO:Uncomment send mail part
    @PostMapping("/register")
    public String addNewUser(@Valid RegisterForm registerForm, BindingResult bindingResult) throws Exception
    {

        if(bindingResult.hasErrors())
        {
            return "register";
        }
        else
        {
            String id = registerForm.getUsername().toLowerCase();
            String email = registerForm.getEmail().toLowerCase();
            int hash = id.concat(email).hashCode();

            if(userRepository.findById(id).isPresent())
            {
                return "register";
            }
            else
            {
                
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(registerForm.getEmail());
            helper.setText("Welcome to OnlinePreprocessor, click in the following link to log into your account https://localhost:8443/user/accountconfirmation"+"?hash="+hash);
            helper.setSubject("Account activation");

            sender.send(message);
            

            User user = new User(id, email,hash, registerForm.getPassword(),
            registerForm.getName(),registerForm.getSurname());
            userService.saveUser(user);

            return "redirect:/";
            }
        }
    }

    //TODO: Make a redirect to a confirmation page when confirmation is OK
    @GetMapping("/accountconfirmation")
    public String confirmation(@RequestParam("hash") int hash)
    {
        Optional<User> opt = userRepository.findUserByHash(hash);
        if(opt.isPresent())
        {
            User user= opt.get();
            user.setConfirmedAccount(true);
            userRepository.save(user);  
        
        return "redirect:/";
        }
        else
            return"redirect:/user/register";
    }

    @GetMapping("/main")
    public String access()
    {
        return "main";
    }
    
}
