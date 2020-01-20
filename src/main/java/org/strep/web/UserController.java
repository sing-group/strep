package org.strep.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;

import org.strep.domain.Dataset;
import org.strep.domain.Permission;
import org.strep.domain.User;
import org.strep.repositories.DatasetRepository;
import org.strep.repositories.PermissionRepository;
import org.strep.repositories.UserRepository;
import org.strep.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Value("${host.name}")
    private String hostname;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * The message i18n
     */
    @Autowired
    MessageSource messageSource;

    @GetMapping("/register")
    public String register(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            String id = user.getUsername().toLowerCase();
            String email = user.getEmail().toLowerCase();
            String hash = new String(Base64.getEncoder().encode((id + email + Math.random()).getBytes()));
            if (userRepository.findUserByEmail(email).isPresent()) {
                model.addAttribute("message", messageSource.getMessage("header.wrongemail", null, locale));
                model.addAttribute("user", user);
                return "register";
            } else if (userRepository.findById(id).isPresent()) {
                model.addAttribute("message", messageSource.getMessage("header.wrongid", null, locale));
                model.addAttribute("user", user);
                return "register";
            } else {
                // Send mail to new user
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject(messageSource.getMessage("register.email.subject", null, locale));
                message.setText(messageSource.getMessage("register.email.textwelcome", null, locale) + "\n\n"
                        + hostname + "/user/accountconfirmation?hash="
                        + hash.replaceAll("=", "") + "\n\n"
                        + messageSource.getMessage("register.email.textregards", null, locale));
                mailSender.send(message);

                // Send mail to admin
                ArrayList<User> usersList = userRepository.findUserByPermission(Permission.ADMINISTER);
                List<String> toList = new ArrayList<>();
                usersList.forEach((u) -> {
                    toList.add(u.getEmail());
                });
                
                SimpleMailMessage mailMessageToAdmins = new SimpleMailMessage();
                mailMessageToAdmins.setTo(toList.stream().toArray(String[]::new));
                mailMessageToAdmins.setSubject(messageSource.getMessage("register.email.subject.toadmin", null, locale));
                mailMessageToAdmins.setText(messageSource.getMessage("register.email.text.toadmin", Stream.of(id).toArray(String[]::new), locale));
                mailSender.send(mailMessageToAdmins);
                
                User user2 = new User(id, email, hash.replaceAll("=", ""), user.getPassword(),
                        user.getName(), user.getSurname());

                userService.createUser(user2);

                return "redirect:/?register=ok";
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
            return "redirect:/?confirmed=ok";
        } else {
            return "redirect:/?confirmed=nook";
        }
    }

    @GetMapping("/confirm")
    public String confirmAccountRequest(@RequestParam("username") String username, Authentication authentication) {
        Optional<User> userOpt = userRepository.findById(username);
        Locale locale = LocaleContextHolder.getLocale();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setConfirmedAccount(true);

            userRepository.save(user);
            return "redirect:/user/list";
        } else {
            return "redirect:/user/list?message=nook";
        }
    }

    @GetMapping("list")
    public String listUsers(@RequestParam(name = "search", required = false) String searchInput,
            Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

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
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(sessionUsername);
        String maxUserPermission = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("maxUserPermission", maxUserPermission);
        model.addAttribute("username", sessionUsername);
        model.addAttribute("photo", optUser.get().getPhoto());

        ArrayList<Dataset> systemDatasets = datasetRepository.getOwnDatasets(username, Dataset.TYPE_SYSTEM);
        ArrayList<Dataset> userDatasets = datasetRepository.getOwnDatasets(username, Dataset.TYPE_USER);
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
        Optional<User> optRegUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);
        String message = "";

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optRegUser.get().getPhoto());

        Optional<User> optUser = userRepository.findById(deleteUsername);

        if (optUser.isPresent()) {
            //permissionRepository.deletePermissions(optUser.get().getUsername());
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
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setPassword(user.getEncryptedPassword().substring(0, 29));
            user.setConfirmPassword(user.getEncryptedPassword().substring(0, 29));
            user.setUsername(user.getUsername());
            model.addAttribute("username", username);
            model.addAttribute("authority", authority);
            model.addAttribute("user", user);
            model.addAttribute("photo", user.getPhoto());
            return "edit_profile.html";
        } else {
            return "redirect:/dataset/list";
        }
    }

    @PostMapping("editprofile")
    public String editProfile(Authentication authentication, Model model,
            @Valid User loadedUser, BindingResult result,
            @RequestParam(name = "photomp", required = false) MultipartFile multipartFile) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Locale locale = LocaleContextHolder.getLocale();
        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("authority", authority);
        model.addAttribute("photo", optUser.get().getPhoto());

        if (result.hasErrors()) {
            if (result.getFieldErrors().get(0).getField().equals("password")) {
                model.addAttribute("message", messageSource.getMessage("editprofile.passwordnotmatch", null, locale));
            } else {
                model.addAttribute("message", messageSource.getMessage("editprofile.error", null, locale));
            }

            model.addAttribute("user", loadedUser);

            return "edit_profile";
        }

        String savedEmail = optUser.get().getEmail();
        byte[] photo;
        try {
            if (multipartFile.isEmpty()) {
                if (optUser.get().getPhoto() == null) {
                    photo = IOUtils.toByteArray(this.getClass().getResourceAsStream("static.images/authorplaceholderwhite.svg"));
                } else {
                    photo = optUser.get().getPhoto();
                }
            } else {
                photo = multipartFile.getBytes();
            }
        } catch (Exception e) {
            photo = optUser.get().getPhoto();
        }
        loadedUser.setPhoto(photo);

        loadedUser.setEmail(loadedUser.getEmail().toLowerCase());

        if (!savedEmail.equals(loadedUser.getEmail()) && userRepository.findUserByEmail(loadedUser.getEmail()).isPresent()) {
            model.addAttribute("message", messageSource.getMessage("header.wrongemail", null, locale));
            model.addAttribute("user", loadedUser);
            return "edit_profile";
        }

        userService.updateUser(loadedUser);

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
