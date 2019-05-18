package com.project.onlinepreprocessor.web;

import com.project.onlinepreprocessor.repositories.TaskRepository;
import com.project.onlinepreprocessor.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/task")
public class TaskController
{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;


    //TODO: Implement this method
    @GetMapping("/upload")
    public String listSystemTasks(Authentication authentication, Model model, @RequestParam(name="searchInput", required=false)String inputSearch)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        if(inputSearch!=null)
        {
            model.addAttribute("tasks", taskRepository.getSystemTasksFiltered(username, inputSearch));
        }
        else
        {
            model.addAttribute("tasks", taskRepository.getSystemTasks(username));
        }

        return "system_task_list";
    }

    @GetMapping("/create")
    public String listUserTasks(Authentication authentication, Model model, @RequestParam(name="searchInput", required=false)String inputSearch)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        if(inputSearch!=null)
        {
            model.addAttribute("tasks", taskRepository.getUserTasksFiltered(username, inputSearch));
        }
        else
        {
            model.addAttribute("tasks", taskRepository.getUserTasks(username));
        }

        return "user_task_list";

    }

    /*
    //TODO: Implement this method
    @GetMapping("/detailed")
    public String detailedTask()
    {

    }
    */
}