package com.project.onlinepreprocessor.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Permission;
import com.project.onlinepreprocessor.domain.User;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.PermissionRepository;
import com.project.onlinepreprocessor.repositories.UserRepository;
import com.project.onlinepreprocessor.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This controller responds to all requests related to users
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping("/register")
    public String register(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            String id = user.getUsername().toLowerCase();
            String email = user.getEmail().toLowerCase();
            String hash = new String(Base64.getEncoder().encode((id + email + Math.random()).getBytes()));

            if (userRepository.findById(id).isPresent()) {
                return "register";
            } else {

                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                try {
                    helper.setTo(user.getEmail());
                    helper.setText(
                            "Welcome to OnlinePreprocessor, click in the following link to log into your account https://localhost:8443/user/accountconfirmation"
                                    + "?hash=" + hash.replaceAll("=", ""));
                    helper.setSubject("Account activation");

                    sender.send(message);

                    User user2 = new User(id, email, hash.replaceAll("=", ""), user.getPassword(),
                            user.getName(), user.getSurname());
                    userService.saveUser(user2);
                } catch (MessagingException e) {
                    return "redirect:/error";
                }

                return "redirect:/";
            }
        }
    }

    @GetMapping("/accountconfirmation")
    public String confirmation(@RequestParam("hash") String hash) {
        Optional<User> opt = userRepository.findUserByHash(hash);
        if (opt.isPresent()) {
            User user = opt.get();
            user.setConfirmedAccount(true);
            userRepository.save(user);

            return "redirect:/";
        } else
            return "redirect:/user/register";
    }

    @GetMapping("list")
    public String listUsers(@RequestParam(name = "search", required = false) String searchInput,
            Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        if (searchInput == null) {
            model.addAttribute("users", userRepository.findAll());
        } else {
            model.addAttribute("users", userRepository.searchUsers(searchInput));
        }

        return "list_users";
    }

    @GetMapping("detailed")
    public String detailedUser(@RequestParam("username") String username, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String sessionUsername = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(sessionUsername);
        String maxUserPermission = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("maxUserPermission", maxUserPermission);
        model.addAttribute("username", sessionUsername);

        Optional<User> optUser = userRepository.findById(username);
        ArrayList<Dataset> systemDatasets = datasetRepository.getUserDatasets(username, "systemdataset");
        ArrayList<Dataset> userDatasets = datasetRepository.getUserDatasets(username, "userdataset");
        Iterable<Permission> permissions = permissionRepository.findAll();

        if (optUser.isPresent()) {
            User user = optUser.get();
            model.addAttribute("user", user);
            model.addAttribute("userdatasetsnum", userDatasets.size());
            model.addAttribute("systemdatasetsnum", systemDatasets.size());
            model.addAttribute("permissions", permissions);
        }

        return "detailed_user";
    }

    @GetMapping("delete")
    public String deleteUser(@RequestParam("username") String deleteUsername,
            @RequestParam(name = "search", required = false) String searchInput, Authentication authentication,
            Model model) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        String message = "";

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<User> optUser = userRepository.findById(deleteUsername);

        if (optUser.isPresent()) {
            permissionRepository.deletePermissions(optUser.get().getUsername());
            userRepository.delete(optUser.get());
            message = "User deleted successfully";
        } else {
            message = "Cannot delete user";
        }
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("message", message);
        return "list_users";
    }

    @PostMapping("editpermissions")
    public String editUserPermissions(@RequestParam("permission") int permission,
            @RequestParam("username") String username, Authentication authentication, Model model,
            RedirectAttributes redirectAttributes) {
        String message = userService.editPermissions(permission, username);
        redirectAttributes.addAttribute("username", username);
        redirectAttributes.addAttribute("message", message);

        return "redirect:/user/detailed";
    }

    @GetMapping("editprofile")
    public String editProfile(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        Optional<User> optUser = userRepository.findById(username);

        if (optUser.isPresent()) {
            User user = optUser.get();

            model.addAttribute("username", username);
            model.addAttribute("authority", authority);
            model.addAttribute("user", user);
            return "edit_profile.html";
        } else {
            return "redirect:/dataset/list";
        }
    }

    @PostMapping("editprofile")
    public String editProfile(Authentication authentication, Model model,
            @RequestParam(name = "photo", required = true) MultipartFile multipartFile) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        userService.editProfile(username, multipartFile);

        return "redirect:/user/editprofile";
    }

    @GetMapping("image")
    public void showUserImage(@RequestParam(name = "username") String username, HttpServletResponse response)
            throws IOException {

        Optional<User> optUser = userRepository.findById(username);
        if (optUser.isPresent()) {
            User user = optUser.get();

            ServletOutputStream sos = response.getOutputStream();

            if (user.getPhoto() != null) {
                response.setContentType("image/jpeg");
                sos.write(user.getPhoto());
            } else {
                response.setContentType("image/svg+xml");
                ClassPathResource cResource = new ClassPathResource("static/images/authorplaceholderwhite.svg");
                InputStream is = cResource.getInputStream();
                StreamUtils.copy(is, response.getOutputStream());
                is.close();
            }
            sos.flush();
            sos.close();
        }
    }

}
