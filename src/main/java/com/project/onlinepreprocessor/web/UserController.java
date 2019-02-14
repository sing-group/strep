package com.project.onlinepreprocessor.web;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.forms.RegisterForm;
import com.project.onlinepreprocessor.repositories.UserRepository;
import com.project.onlinepreprocessor.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Base64;



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


    @PostMapping("/register")
    public String addNewUser(@Valid RegisterForm registerForm, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors())
        {
            return "register";
        }
        else
        {
            String id = registerForm.getUsername().toLowerCase();
            String email = registerForm.getEmail().toLowerCase();
            String hash = new String(Base64.getEncoder().encode((id+email+Math.random()).getBytes()));

            if(userRepository.findById(id).isPresent())
            {
                return "register";
            }
            else
            {
                
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
            try
            {
            helper.setTo(registerForm.getEmail());
            helper.setText("Welcome to OnlinePreprocessor, click in the following link to log into your account https://localhost:8443/user/accountconfirmation"+"?hash="+hash.replaceAll("=",""));
            helper.setSubject("Account activation");
            

            sender.send(message);

            User user = new User(id, email,hash.replaceAll("=", ""), registerForm.getPassword(),
            registerForm.getName(),registerForm.getSurname());
            userService.saveUser(user);
            }
            catch(MessagingException e)
            {
                return "redirect:/error";
            }

            return "redirect:/";
            }
        }
    }

    //TODO: Make a redirect to a confirmation page when confirmation is OK
    @GetMapping("/accountconfirmation")
    public String confirmation(@RequestParam("hash") String hash)
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
    
}
