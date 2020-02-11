package org.strep.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;

import org.strep.domain.Dataset;
import org.strep.domain.Task;
import org.strep.domain.User;
import org.strep.domain.TaskCreateUPreprocessing;
import org.strep.domain.TaskCreateUdataset;
import org.strep.repositories.DatasetRepository;
import org.strep.repositories.TaskRepository;
import org.strep.repositories.UserRepository;
import org.strep.services.TaskService;
import org.strep.services.UserService;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This controller responds to all requests related to tasks
 */
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
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Value("${pipeline.storage}")
    private String PIPELINE_PATH;

    @Value("${csv.storage}")
    private String OUTPUT_PATH;

    @GetMapping("/upload")
    public String listSystemTasks(Authentication authentication, Model model,
            @RequestParam(name = "searchInput", required = false) String inputSearch,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_WAITING) String state) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("state", state);
        model.addAttribute("photo", optUser.get().getPhoto());

        if (inputSearch != null) {
            model.addAttribute("tasks", taskRepository.getSystemTasksFiltered(username, inputSearch, state));
        } else {
            model.addAttribute("tasks", taskRepository.getSystemTasks(username, state));
        }

        return "system_task_list";
    }

    @GetMapping("/create")
    public String listUserTasks(Authentication authentication, Model model,
            @RequestParam(name = "searchInput", required = false) String inputSearch,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_WAITING) String state) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("state", state);
        model.addAttribute("photo", optUser.get().getPhoto());

        if (inputSearch != null) {
            model.addAttribute("tasks", taskRepository.getActiveUserTasksFiltered(username, inputSearch, state));
        } else {
            model.addAttribute("tasks", taskRepository.getActiveUserTasks(username, state));
        }

        return "user_task_list";

    }

    @GetMapping("/hide")
    public String hideTask(Authentication authentication, Model model, @RequestParam(name = "task") int id,
            @RequestParam(name = "option") String option, @RequestParam(name = "state") String state) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        Long idLong = new Long(id);
        Optional<Task> optTask = taskRepository.findById(idLong);

        Task task = optTask.get();

        if (task.getDataset().getAuthor().equals(username)) {
            task.setActive(Boolean.FALSE);
            taskRepository.save(task);
            switch (option) {
            case "upload":
                return "redirect:/task/upload?state=" + state;
            case "create":
                return "redirect:/task/create?state=" + state;
            case "preprocess":
                return "redirect:/task/preprocess?state=" + state;
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/fillFields")
    public String fillTaskFields(Authentication authentication, Model model, @RequestParam("id") int id) {
        Long idLong = new Long(id);
        TaskCreateUPreprocessing task = taskRepository.findTaskCreateUPreprocessingById(idLong);
        model.addAttribute("task", task);
        System.out.println("task name: " + task.getName());
        System.out.println("task description: " + task.getDescription());
        return "create_preprocessing_task::task-data";
    }

    @GetMapping("/detailed")
    public String detailedTask(Authentication authentication, Model model, @RequestParam(name = "task") int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

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
            @RequestParam(name = "id", required = false) String datasetName,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_WAITING) String state) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        ArrayList<TaskCreateUPreprocessing> tasks = new ArrayList<>();
        ArrayList<Dataset> datasets = new ArrayList<>();

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        // Optional<Dataset> optDataset = datasetRepository.findById(datasetName);
        if (datasetName != null && !datasetName.equals("")) {
            Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

            if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
                Dataset dataset = optDataset.get();
                model.addAttribute("dataset", dataset);
                tasks = taskRepository.getActivePreprocessingTasks(dataset, state);
                model.addAttribute("state", state);
                model.addAttribute("tasks", tasks);
            } else {
                return "redirect:/error";
            }
        } else {
            for (Dataset optDataset : datasetRepository.findAll()) {
                Collection<TaskCreateUPreprocessing> newTasks = taskRepository.getActivePreprocessingTasks(optDataset,
                        state);
                tasks.addAll(newTasks);
                for (int i = 0; i < newTasks.size(); i++) {
                    datasets.add(optDataset);
                }
            }
            model.addAttribute("state", state);
            model.addAttribute("tasks", tasks);
            model.addAttribute("datasets", datasets);
        }

        return "list_preprocessing_tasks";
    }

    @GetMapping("/preprocess/detailed")
    public String listDetailedPreprocesss(Authentication authentication, Model model,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_SUCESS) String state,
            @RequestParam(name = "name", required = true, defaultValue = "") String name) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("state", state);
        model.addAttribute("photo", optUser.get().getPhoto());

        HashMap<String, ArrayList<TaskCreateUPreprocessing>> taskCreateUPreprocessingHM = new HashMap<>();

        ArrayList<TaskCreateUPreprocessing> tasks = taskRepository.getPreprocessingTasks(Task.STATE_SUCESS);

        String currentDataset = "";
        ArrayList<TaskCreateUPreprocessing> currentDatasetTasks = null;
        for (TaskCreateUPreprocessing task : tasks) {
            if (!name.equals("")) {
                if (name.equals(task.getDataset().getName())) {
                    if (!currentDataset.equals(task.getDataset().getName())) {
                        currentDataset = task.getDataset().getName();
                        currentDatasetTasks = new ArrayList<>();
                        taskCreateUPreprocessingHM.put(currentDataset, currentDatasetTasks);
                    }
                    currentDatasetTasks.add(task);
                }
            } else {
                if (!currentDataset.equals(task.getDataset().getName())) {
                    currentDataset = task.getDataset().getName();
                    currentDatasetTasks = new ArrayList<>();
                    taskCreateUPreprocessingHM.put(currentDataset, currentDatasetTasks);
                }
                currentDatasetTasks.add(task);
            }
        }

        ArrayList<Task> datasetsNoPreprocessing = taskRepository.getUserTasks(username, Task.STATE_SUCESS);
        for (Task data : datasetsNoPreprocessing) {
            currentDataset = data.getDataset().getName();

            if (!name.equals("")) {
                if (currentDataset.equals(name)) {
                    if (!taskCreateUPreprocessingHM.containsKey(currentDataset)) {
                        taskCreateUPreprocessingHM.put(currentDataset, null);
                    }
                }
            } else {
                if (!taskCreateUPreprocessingHM.containsKey(currentDataset)) {
                    taskCreateUPreprocessingHM.put(currentDataset, null);
                }
            }
        }
        model.addAttribute("datasets", taskCreateUPreprocessingHM);
        return "list_preprocess_detailed";
    }

    @GetMapping("/showtasks")
    public String listPreprocessTasks(Authentication authentication, Model model,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_SUCESS) String state,
            @RequestParam(name = "name", required = true, defaultValue = "") String name) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("state", state);
        model.addAttribute("photo", optUser.get().getPhoto());

        ArrayList<TaskCreateUPreprocessing> tasks = taskRepository.getPreprocessingTasks(Task.STATE_SUCESS);
        ArrayList<TaskCreateUPreprocessing> currentDatasetTasks = new ArrayList<>();
        for (TaskCreateUPreprocessing task : tasks) {

            if (name.equals(task.getDataset().getName())) {
                currentDatasetTasks.add(task);
            }
        }

        model.addAttribute("name", name);
        model.addAttribute("tasks", currentDatasetTasks);
        
        return "task_detailed";
    }

    @GetMapping("/preprocess/dataset")
    public String listDatasetToPreprocesss(Authentication authentication, Model model,
            @RequestParam(name = "state", required = false, defaultValue = Task.STATE_SUCESS) String state) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("state", state);
        model.addAttribute("photo", optUser.get().getPhoto());

        HashMap<String, ArrayList<TaskCreateUPreprocessing>> taskCreateUPreprocessingHM = new HashMap<>();

        ArrayList<TaskCreateUPreprocessing> tasks = taskRepository.getPreprocessingTasks(Task.STATE_SUCESS);

        String currentDataset = "";
        ArrayList<TaskCreateUPreprocessing> currentDatasetTasks = null;
        for (TaskCreateUPreprocessing task : tasks) {

            if (!currentDataset.equals(task.getDataset().getName())) {
                currentDataset = task.getDataset().getName();
                currentDatasetTasks = new ArrayList<>();
                taskCreateUPreprocessingHM.put(currentDataset, currentDatasetTasks);
            }
            currentDatasetTasks.add(task);
        }
        ArrayList<Task> datasetsNoPreprocessing = taskRepository.getUserTasks(username, Task.STATE_SUCESS);
        for (Task data : datasetsNoPreprocessing) {
            currentDataset = data.getDataset().getName();
            if (!taskCreateUPreprocessingHM.containsKey(currentDataset)) {
                taskCreateUPreprocessingHM.put(currentDataset, null);
            }
        }

        model.addAttribute("datasets", taskCreateUPreprocessingHM);
        return "list_preprocess_dataset";
    }

    @GetMapping("/preprocess/create")
    public String createPreprocessingTask(Authentication authentication, Model model, TaskCreateUPreprocessing task,
            @RequestParam(name = "name") String datasetName) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);
        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            ArrayList<TaskCreateUPreprocessing> distinctTasks = new ArrayList<>();
            ArrayList<TaskCreateUPreprocessing> tasks = taskRepository.findAllTaskCreateUPreprocessing(datasetName,
                    username, Task.STATE_SUCESS);

            for (TaskCreateUPreprocessing t : tasks) {
                String taskName = t.getName();

                if (distinctTasks.size() > 0) {
                    boolean exists = false;
                    for (TaskCreateUPreprocessing dt : distinctTasks) {
                        if (dt.getName().equals(taskName)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        distinctTasks.add(t);
                    }
                } else {
                    distinctTasks.add(t);
                }
            }

            model.addAttribute("dataset", dataset);
            model.addAttribute("task", new TaskCreateUPreprocessing());
            model.addAttribute("tasks", distinctTasks);
            return "create_preprocessing_task";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/preprocess/create")
    public String createPreprocessingTask(Authentication authentication, Model model,
            RedirectAttributes redirectAttributes, @Valid @ModelAttribute("task") TaskCreateUPreprocessing task,
            BindingResult bindingResult, @RequestParam(name = "preprocessDataset") String datasetName,
            @RequestParam(name = "multipart") MultipartFile pipeline) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> optUser = userRepository.findById(username);
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("photo", optUser.get().getPhoto());

        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            if (bindingResult.hasErrors()) {
                if (optDataset.isPresent()) {
                    model.addAttribute("dataset", optDataset.get());
                }
                model.addAttribute("task", task);
                return "create_preprocessing_task";
            }
            try {
                String message = taskService.createPreprocessingTask(dataset, task, pipeline.getBytes());
                redirectAttributes.addAttribute("message", message);
                return "redirect:/task/preprocess?id=" + datasetName;
            } catch (IOException ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
                return "redirect:/error";
            }

        } else {
            return "redirect:/error";
        }

    }

    @PostMapping("/preprocess/reuse")
    public String createPreprocessingTask(Authentication authentication, Model model,
            RedirectAttributes redirectAttributes, @RequestParam(name = "preprocessDataset") String datasetName,
            @RequestParam(name = "task") String taskId) {

        Long id = new Long(taskId);
        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        if (optDataset.isPresent() && optDataset.get().getAuthor().equals(username)) {
            Dataset dataset = optDataset.get();
            TaskCreateUPreprocessing task = taskRepository.findTaskCreateUPreprocessingById(id);
            String message = taskService.createPreprocessingTask(dataset, task, task.getPipeline());
            redirectAttributes.addAttribute("message", message);

            return "redirect:/task/preprocess?id=" + datasetName;
        }
        return "redirect:/error";
    }

    @GetMapping("/preprocess/downloadpipeline")
    public ResponseEntity<InputStreamResource> downloadPipeline(Authentication authentication,
            @RequestParam("id") Long taskId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String fileName = taskService.downloadPipeline(taskId, username);

        if (fileName != null) {
            try {
                FileInputStream fis = new FileInputStream(new java.io.File(PIPELINE_PATH + fileName));
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("content-type", "application/xml");
                httpHeaders.set("content-disposition", "attachment;" + "filename=" + fileName);
                ResponseEntity<InputStreamResource> response = new ResponseEntity<>(new InputStreamResource(fis),
                        httpHeaders, HttpStatus.CREATED);
                return response;

            } catch (FileNotFoundException fnfException) {
                return null;
            }

        } else {
            return null;
        }

    }

    @GetMapping("/preprocess/downloadcsv")
    public ResponseEntity<InputStreamResource> downloadCsv(Authentication authentication,
            @RequestParam("id") Long taskId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String fileName = taskService.downloadCsv(taskId, username);

        if (fileName != null) {
            try {
                FileInputStream fis = new FileInputStream(new java.io.File(OUTPUT_PATH + fileName));
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("content-type", "text/csv");
                httpHeaders.set("content-disposition", "attachment;" + "filename=" + fileName);
                ResponseEntity<InputStreamResource> response = new ResponseEntity<>(new InputStreamResource(fis),
                        httpHeaders, HttpStatus.CREATED);
                return response;
            } catch (FileNotFoundException fnfException) {
                return null;
            }
        } else {
            return null;
        }
    }
}
