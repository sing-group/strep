package com.project.onlinepreprocessor.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Task;
import com.project.onlinepreprocessor.domain.TaskCreateUPreprocessing;
import com.project.onlinepreprocessor.domain.TaskCreateUdataset;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.TaskRepository;
import com.project.onlinepreprocessor.services.TaskService;
import com.project.onlinepreprocessor.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private TaskService taskService;

    @Value("${pipeline.storage}")
    private String PIPELINE_PATH;

    // TODO: Implement this method
    @GetMapping("/upload")
    public String listSystemTasks(Authentication authentication, Model model,
            @RequestParam(name = "searchInput", required = false) String inputSearch) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        if (inputSearch != null) {
            model.addAttribute("tasks", taskRepository.getSystemTasksFiltered(username, inputSearch));
        } else {
            model.addAttribute("tasks", taskRepository.getSystemTasks(username));
        }

        return "system_task_list";
    }

    @GetMapping("/create")
    public String listUserTasks(Authentication authentication, Model model,
            @RequestParam(name = "searchInput", required = false) String inputSearch) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        if (inputSearch != null) {
            model.addAttribute("tasks", taskRepository.getUserTasksFiltered(username, inputSearch));
        } else {
            model.addAttribute("tasks", taskRepository.getUserTasks(username));
        }

        return "user_task_list";

    }

    @GetMapping("/detailed")
    public String detailedTask(Authentication authentication, Model model, @RequestParam(name = "task") int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Long idLong = new Long(id);
        Optional<TaskCreateUdataset> optTask = taskRepository.findTaskCreateUdatasetById(idLong);

        if (optTask.isPresent()) {
            TaskCreateUdataset task = optTask.get();
            Optional<Dataset> optDataset = datasetRepository.findById(task.getDataset().getName());

            if (optDataset.isPresent()) {
                Dataset dataset = optDataset.get();
                String sDatasetsNames = "";

                for (Dataset systemDataset : task.getDatasets()) {
                    sDatasetsNames += systemDataset.getName() + " ";
                }

                model.addAttribute("dataset", dataset);
                model.addAttribute("systemdatasets", sDatasetsNames);
                model.addAttribute("task", task);
                model.addAttribute("licenses", task.toStringLicenses());
                model.addAttribute("languages", task.toStringLanguages());
                model.addAttribute("datatypes", task.toStringDatatypes());
                model.addAttribute("dates", task.toStringDate());
            }

        }

        return "user_task_detailed";
    }

    @GetMapping("/preprocess")
    public String listPreprocess(Authentication authentication, Model model,
            @RequestParam(name = "id") String datasetName,
            @RequestParam(name = "state", required = false, defaultValue = "waiting") String state) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        ArrayList<TaskCreateUPreprocessing> tasks = new ArrayList<TaskCreateUPreprocessing>();

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            tasks = taskRepository.getPreprocessingTasks(dataset, state);
            model.addAttribute("state", state);
            model.addAttribute("tasks", tasks);
            model.addAttribute("dataset", dataset);
        } else {
            return "redirect:/error";
        }

        return "list_preprocessing_tasks";
    }

    @GetMapping("/preprocess/create")
    public String createPreprocessingTask(Authentication authentication, Model model,
            @RequestParam(name = "name") String datasetName) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            model.addAttribute("dataset", dataset);
            model.addAttribute("task", new TaskCreateUPreprocessing());
            return "create_preprocessing_task";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/preprocess/create")
    public String createPreprocessingTask(Authentication authentication, Model model,
            @RequestParam(name = "preprocessDataset") String datasetName, RedirectAttributes redirectAttributes,
            TaskCreateUPreprocessing task, @RequestParam(name = "multipart") MultipartFile pipeline) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            String message = taskService.createPreprocessingTask(dataset, task, pipeline);
            redirectAttributes.addAttribute("message", message);
            return "redirect:/task/preprocess?id=" + datasetName;
        } else {
            return "redirect:/error";
        }

    }

    @GetMapping("/preprocess/downloadpipeline")
    public ResponseEntity<InputStreamResource> downloadPipeline(Authentication authentication,
            @RequestParam("id") Long taskId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        String fileName = taskService.downloadPipeline(taskId, username);
        System.out.println(fileName);

        if (fileName != null) {
            try {
                FileInputStream fis = new FileInputStream(new java.io.File(PIPELINE_PATH + fileName));
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("content-type", "application/xml");
                httpHeaders.set("content-disposition", "attachment;" + "filename=" + fileName + ".xml");
                ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                        new InputStreamResource(fis), httpHeaders, HttpStatus.CREATED);
                return response;

            } catch (FileNotFoundException fnfException) {
                return null;
            }

        } else {
            return null;
        }

    }
}