package com.project.onlinepreprocessor.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Datatype;
import com.project.onlinepreprocessor.domain.FileDatatypeType;
import com.project.onlinepreprocessor.domain.Language;
import com.project.onlinepreprocessor.domain.License;
import com.project.onlinepreprocessor.forms.LoginForm;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.DatatypeRepository;
import com.project.onlinepreprocessor.repositories.FileDatatypeTypeRepository;
import com.project.onlinepreprocessor.repositories.LanguageRepository;
import com.project.onlinepreprocessor.repositories.LicenseRepository;
import com.project.onlinepreprocessor.services.DatasetService;
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
import java.lang.Math;

@Controller
@RequestMapping(path = "/dataset")
public class DatasetController {
    @Value("${dataset.storage}")
    private String BASE_PATH;

    @Value("${host.name}")
    private String HOST_NAME;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private UserService userService;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private DatatypeRepository datatypeRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileDatatypeTypeRepository fileDatatypeTypeRepository;

    @GetMapping("/public")
    public String listPublicDatasets(LoginForm loginForm, Model model) {
        HashSet<Dataset> datasets = datasetRepository.getPublicDatasets();
        model.addAttribute("datasets", datasets);
        return "public_datasets";
    }

    @GetMapping("/public/detailed")
    public String detailPublicDataset(@RequestParam("id") String name, Model model, LoginForm loginForm) {
        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent()) {
            Dataset dataset = opt.get();
            String languages = datasetService.getLanguagesString(dataset);
            String datatypes = datasetService.getDatatypesString(dataset);

            model.addAttribute("languages", languages);
            model.addAttribute("datatypes", datatypes);
            model.addAttribute("dataset", dataset);
            return "detailedDataset";
        } else {
            return "redirect:/error";
        }

    }

    @GetMapping("/public/download")
    public ResponseEntity<InputStreamResource> downloadPublicDataset(@RequestParam("id") String name, Model model,
            LoginForm loginForm) throws FileNotFoundException {
        if (datasetService.getDownloadFiles(name)) {
            FileInputStream fis = new FileInputStream(new File(BASE_PATH + name + ".zip"));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("content-type", "application/zip");
            httpHeaders.set("content-disposition", "attachment;" + "filename=" + name + ".zip");
            ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                    new InputStreamResource(fis), httpHeaders, HttpStatus.CREATED);
            return response;
        } else {
            return null;
        }
    }

    @GetMapping("/list")
    public String listDatasets(@RequestParam("type") String type, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        ArrayList<Dataset> datasets = new ArrayList<Dataset>();

        switch (type) {
        case "system":
            datasets = datasetRepository.getSystemDatasets();
            break;
        case "protected":
            datasets = datasetRepository.getProtectedDatasets();
            break;
        case "user":
            if (authority == "canView") {
                datasets = datasetRepository.getSystemDatasets();
                type = "system";
            } else {
                datasets = datasetRepository.getUserDatasets(username);
            }
            break;
        }

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("type", type);
        model.addAttribute("datasets", datasets);

        return "list_datasets";
    }

    @GetMapping("/detailed")
    public String detailDataset(Authentication authentication, @RequestParam("id") String name,
            @RequestParam("type") String type, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent()) {
            Dataset dataset = opt.get();
            String languages = datasetService.getLanguagesString(dataset);
            String datatypes = datasetService.getDatatypesString(dataset);

            if (dataset.getAccess().equals("private")) {
                if (dataset.getAuthor().equals(username)) {
                    model.addAttribute("dataset", dataset);
                    model.addAttribute("languages", languages);
                    model.addAttribute("datatypes", datatypes);
                    return "detailed_private_dataset";
                } else {
                    return "redirect:/error";
                }
            } else {
                model.addAttribute("dataset", dataset);
                model.addAttribute("languages", languages);
                model.addAttribute("datatypes", datatypes);
                return "detailed_private_dataset";
            }
        } else {
            return "redirect:/error";
        }

    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadDataset(Authentication authentication,
            @RequestParam("id") String name, Model model, LoginForm loginForm) throws FileNotFoundException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent()) {

            Dataset dataset = opt.get();
            String owner = dataset.getAuthor();

            if (dataset.getAccess().equals("public") || dataset.getAccess().equals("protected")
                    || owner.equals(username)) {

                if (datasetService.getDownloadFiles(name)) {
                    FileInputStream fis = new FileInputStream(new File(BASE_PATH + name + ".zip"));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.set("content-type", "application/zip");
                    httpHeaders.set("content-disposition", "attachment;" + "filename=" + name + ".zip");
                    ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                            new InputStreamResource(fis), httpHeaders, HttpStatus.CREATED);
                    return response;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    @GetMapping("/delete")
    public String deleteDataset(Authentication authentication, Model model, @RequestParam("id") String name) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        Optional<Dataset> opt = datasetRepository.findById(name);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("type", "user");

        if (opt.isPresent() && opt.get().getAuthor().equals(username)) {
            Dataset dataset = opt.get();
            if (datasetService.deleteDataset(dataset)) {
                model.addAttribute("message", "Successfully deleted");
            } else {
                model.addAttribute("message", "Error deleting dataset");
            }
        } else {
            model.addAttribute("message", "Error deleting dataset");
        }

        ArrayList<Dataset> datasets = datasetRepository.getUserDatasets(username);
        model.addAttribute("datasets", datasets);
        return "list_datasets";
    }

    @GetMapping("/access")
    public String changeAccess(Authentication authentication, Model model, @RequestParam("id") String name,
            @RequestParam("access") String access) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        ArrayList<Dataset> datasets = datasetRepository.getUserDatasets(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("datasets", datasets);
        model.addAttribute("type", "user");

        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent()) {
            Dataset dataset = opt.get();

            if (access.equals("private") || access.equals("public") || access.equals("protected")) {
                dataset.setAccess(access);
                datasetRepository.save(dataset);
                model.addAttribute("message", "Dataset successfully changed to " + access);
            } else {
                model.addAttribute("message", access + " is not a valid access type");
            }
        } else {
            model.addAttribute("message", "Cannot found specified dataset to change the access");
        }

        return "list_datasets";

    }

    @GetMapping("/home")
    public String listHomeDatasets(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        ArrayList<Dataset> userDatasets = datasetRepository.getUserDatasets(username);
        model.addAttribute("datasets", userDatasets);

        return "home";
    }

    @GetMapping("/upload")
    public String addNewDataset(Authentication authentication, Model model, Dataset dataset) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("host", HOST_NAME);
        model.addAttribute("licenses", licenseRepository.findAll());

        return "add_dataset";
    }

    @PostMapping("/upload")
    public String addDataset(Authentication authentication, @Valid Dataset dataset, BindingResult bindingResult,
            @RequestParam(name = "dataset-file", required = true) MultipartFile datasetFile,
            RedirectAttributes redirectAttributes, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        if (bindingResult.hasErrors()) {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            model.addAttribute("host", HOST_NAME);
            model.addAttribute("licenses", licenseRepository.findAll());
            return "add_dataset";
        } else {
            String message = datasetService.uploadDataset(dataset, datasetFile, username);
            redirectAttributes.addFlashAttribute("message", message);
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "redirect:/dataset/list?type=user";
        }

    }

    @GetMapping("/modal")
    public String getDatasetInfo(Authentication authentication, Model model, @RequestParam("id") String id) {
        Optional<Dataset> opt = datasetRepository.findById(id);

        if (opt.isPresent()) {
            Dataset dataset = opt.get();
            if (dataset.getType().equals("systemdataset")) {
                model.addAttribute("dataset", dataset);
            }
        }

        return "create_dataset::dataset";
    }

    // TODO:Implement this method
    @GetMapping("/createlist")
    public String filterDatasets(Authentication authentication, Model model,
            @RequestParam(name = "language", required = false) String[] languages,
            @RequestParam(name = "datatype", required = false) String[] datatypes,
            @RequestParam(name = "license", required = false) String[] licenses,
            @RequestParam(name = "date1", required = false) String date1,
            @RequestParam(name = "date2", required = false) String date2) {
        ArrayList<Dataset> datasets = datasetService.getFilteredDatasets(languages, datatypes, licenses, date1, date2);
        model.addAttribute("datasets", datasets);

        return "create_dataset::datasets";
    }

    @GetMapping("/updateDatatypesTable")
    public String updateDatatypesTable(Model model,
            @RequestParam(name = "datasets", required = false) String[] datasetNames) {
        ArrayList<String> datatypes = new ArrayList<String>();
        ArrayList<String> datasets = new ArrayList<String>();

        if (datasetNames != null) {
            for (String datasetName : datasetNames) {
                datasets.add(datasetName);
                System.out.println(datasetName);
            }
            datatypes = datasetRepository.getDatasetsDatatypes(datasets);
        }
        model.addAttribute("tableDatatypes", datatypes);

        return "create_dataset::datatypes-table";
    }

    @GetMapping("/checkPosibleSpam")
    public String showInfoSpam(Model model, @RequestParam(name="inputSpam", required=false) String inputSpam, @RequestParam(name="datasets", required=false)String[] datasets, 
    @RequestParam(name="fileNumber", required=false) String fileNumber)
    {
        int inputSpamInt = -1;
        int availableFiles = -1;
        int necesaryFiles = -1;
        int fileNumberInt = -1;

        ArrayList<String> arrayListDatasets = new ArrayList<String>();

        if(inputSpam!="" && datasets!=null && fileNumber!="")
        {
            for(String dataset : datasets)
            {
                arrayListDatasets.add(dataset);
            }
            
            try {
                inputSpamInt = Integer.parseInt(inputSpam);
                fileNumberInt = Integer.parseInt(fileNumber);
            } catch (NumberFormatException e) {
            }
            
            necesaryFiles = (int) Math.ceil((double)fileNumberInt * ((double)inputSpamInt/100.00));
            availableFiles = datasetRepository.countSpamFiles(arrayListDatasets);

            String message = "Necesary files:"+necesaryFiles+"\nAvailable files"+availableFiles;
            if(availableFiles>=necesaryFiles)
            {
                model.addAttribute("spamSuccessInfo", message);
            }
            else
            {
                model.addAttribute("spamInsufficientInfo", message);
            }
        }
        else
        {
            model.addAttribute("spamErrorInput", "Enter a valid input for the percentage of spam and for the number of files. Select at least one dataset.");
        }
        return "create_dataset::info-spam";
    }

    @GetMapping("/checkPosibleDatatypes")
    public String showInfoDatatypes(Model model, @RequestParam("inputSpamEml")int inputSpamEml, @RequestParam("inputHamEml")int inputHamEml,
    @RequestParam("inputSpamWarc")int inputSpamWarc, @RequestParam("inputHamWarc")int inputHamWarc,
    @RequestParam("inputSpamTsms")int inputSpamTsms, @RequestParam("inputHamTsms")int inputHamTsms,
    @RequestParam("inputSpamTytb")int inputSpamTytb,@RequestParam("inputHamTytb")int inputHamTytb,
    @RequestParam("inputSpamTwtid")int inputSpamTwtid, @RequestParam("inputHamTwtid")int inputHamTwtid, @RequestParam(name="datasets", required=false)String[] datasetNames, 
    @RequestParam("inputFileNumber")int fileNumberInput)
    {
        ArrayList<String> datatypes = new ArrayList<String>();
        ArrayList<String> datasets = new ArrayList<String>();

        if (datasetNames != null) {
            for (String datasetName : datasetNames) {
                datasets.add(datasetName);
                System.out.println(datasetName);
            }
            datatypes = datasetRepository.getDatasetsDatatypes(datasets);
        }
        model.addAttribute("tableDatatypes", datatypes);
        model.addAttribute("inputspameml", inputSpamEml);
        model.addAttribute("inputhameml", inputHamEml);
        model.addAttribute("inputspamwarc", inputSpamWarc);
        model.addAttribute("inputhamwarc", inputHamWarc);
        model.addAttribute("inputspamtytb", inputSpamTytb);
        model.addAttribute("inputhamtytb", inputHamTytb);
        model.addAttribute("inputspamtsms", inputSpamTsms);
        model.addAttribute("inputhamtsms", inputHamTsms);
        model.addAttribute("inputspamtwtid", inputSpamTwtid);
        model.addAttribute("inputhamtwtid", inputHamTwtid);

        if(datasets==null || fileNumberInput==0 || (inputSpamEml+inputHamEml+inputSpamWarc+inputHamWarc+
        inputSpamTsms+inputHamTsms+inputSpamTytb+inputHamTytb+inputSpamTwtid+inputHamTwtid)!=100)
        {
            model.addAttribute("datatypesInputError", "You have to select at least one dataset and enter a valid input in the percentages(sum of percentages must be 100)");
        }
        else
        {
            HashMap<String, Integer> necesaryFilesMap = new HashMap<String, Integer>();

            necesaryFilesMap.put(".emlspam", (int) Math.ceil((double)fileNumberInput * ((double)inputSpamEml/100.00)));
            necesaryFilesMap.put(".emlham", (int) Math.ceil((double)fileNumberInput * ((double)inputHamEml/100.00)));

            necesaryFilesMap.put(".warcspam", (int) Math.ceil((double)fileNumberInput * ((double)inputSpamWarc/100.00)));
            necesaryFilesMap.put(".warcham",(int) Math.ceil((double)fileNumberInput * ((double)inputHamWarc/100.00)));

            necesaryFilesMap.put(".tsmsspam", (int) Math.ceil((double)fileNumberInput * ((double)inputSpamTsms/100.00)));
            necesaryFilesMap.put(".tsmsham", (int) Math.ceil((double)fileNumberInput * ((double)inputHamTsms/100.00)));

            necesaryFilesMap.put(".tytbspam", (int) Math.ceil((double)fileNumberInput * ((double)inputSpamTytb/100.00)));
            necesaryFilesMap.put(".tytbham",(int) Math.ceil((double)fileNumberInput * ((double)inputHamTytb/100.00)));

            necesaryFilesMap.put(".twtidspam", (int) Math.ceil((double)fileNumberInput * ((double)inputSpamTwtid/100.00)));
            necesaryFilesMap.put(".twtidham",(int) Math.ceil((double)fileNumberInput * ((double)inputHamTwtid/100.00)));

            HashMap<String, Integer> databaseFilesMap = new HashMap<String, Integer>();

            databaseFilesMap.put(".emlspam", 0);
            databaseFilesMap.put(".emlham", 0);

            databaseFilesMap.put(".warcspam", 0);
            databaseFilesMap.put(".warcham", 0);

            databaseFilesMap.put(".tsmsspam",0);
            databaseFilesMap.put(".tsmsham", 0);

            databaseFilesMap.put(".tytbspam", 0);
            databaseFilesMap.put(".tytbham", 0);

            databaseFilesMap.put(".twtidspam", 0);
            databaseFilesMap.put(".twtidham", 0);

            //How many files are available of each datatype
            ArrayList<FileDatatypeType> filesDatatypeType = fileDatatypeTypeRepository.getFilesByExtensionAndType(datasets);
            for(FileDatatypeType fileDatatypeType : filesDatatypeType)
            {
                databaseFilesMap.replace(fileDatatypeType.getExtension()+fileDatatypeType.getType(), fileDatatypeType.getCount());
            }

            Set<String> keys = databaseFilesMap.keySet();
            boolean success = true;

            for(String key: keys)
            {
                if(necesaryFilesMap.get(key)!=0)
                {
                    String subkey = key.substring(1);
                    model.addAttribute("necesary"+subkey, necesaryFilesMap.get(key));
                    model.addAttribute("available"+subkey, databaseFilesMap.get(key));
                }
                if(databaseFilesMap.get(key) < necesaryFilesMap.get(key))
                {
                    success = false;
                }
                System.out.println(databaseFilesMap.get(key)+" vs "+necesaryFilesMap.get(key));
            }

            if(success)
            {
                model.addAttribute("class", "info-label");
            }
            else
            {
                model.addAttribute("class", "error-label");
            }
        }
        return "create_dataset::datatypes-table";
    }

    // TODO_ Implement this method
    @GetMapping("/create")
    public String getCreateDataset(Authentication authentication, Model model, Dataset dataset) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        ArrayList<Dataset> datasets = datasetRepository.getSystemDatasets();

        Iterable<License> licenses = licenseRepository.findAll();
        Iterable<Datatype> datatypes = datatypeRepository.findAll();
        Iterable<Language> languages = languageRepository.findAll();

        model.addAttribute("host", HOST_NAME);
        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("datasets", datasets);
        model.addAttribute("licenses", licenses);
        model.addAttribute("datatypes", datatypes);
        model.addAttribute("languages", languages);

        return "create_dataset";

    }

    // TODO: Implement this method
    @PostMapping("/create")
    public String setCreateDataset(Authentication authentication, Model model, @Valid Dataset dataset,
            BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam(name = "datasets", required = false) String[] datasets,
            @RequestParam(name = "license", required = false) String[] licenses,
            @RequestParam(name = "language", required = false) String[] languages,
            @RequestParam(name = "datatype", required = false) String[] datatypes,
            @RequestParam(name="inputSpamPercentage", required = false, defaultValue="0")int inputSpamPercentage,
            @RequestParam(name="inputFileNumber", required = false, defaultValue="0")int inputFileNumber,
            @RequestParam(name="inputSpam.eml", required = false, defaultValue="0")int inputSpamEml,
            @RequestParam(name="inputHam.eml", required = false, defaultValue="0")int inputHamEml,
            @RequestParam(name="inputSpam.warc", required = false, defaultValue="0")int inputSpamWarc,
            @RequestParam(name="inputHam.warc", required = false, defaultValue="0")int inputHamWarc,
            @RequestParam(name="inputSpam.tsms", required = false, defaultValue="0")int inputSpamTsms,
            @RequestParam(name="inputHam.tsms", required = false, defaultValue="0")int inputHamTsms,
            @RequestParam(name="inputSpam.tytb", required = false, defaultValue="0")int inputSpamTytb,
            @RequestParam(name="inputHam.tytb", required = false, defaultValue="0")int inputHamTytb,
            @RequestParam(name="inputSpam.twtid", required = false, defaultValue="0")int inputSpamTwtid,
            @RequestParam(name="inputHam.twtid", required = false, defaultValue="0")int inputHamTwtid,
            @RequestParam(name = "date1", required = false) String dateFrom,
            @RequestParam(name = "date2", required = false) String dateTo) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        String message = "";

        if (bindingResult.hasErrors()) {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            model.addAttribute("host", HOST_NAME);
            model.addAttribute("licenses", licenseRepository.findAll());
            model.addAttribute("datasets", datasetRepository.getSystemDatasets());
            return "create_dataset";
        } else {
            System.out.println(inputSpamTsms);
            message = taskService.addNewUserDatasetTask(dataset, licenses, languages, datatypes,
             datasets, dateFrom, dateTo, inputSpamEml, inputHamEml, inputSpamWarc, 
             inputHamWarc, inputSpamTsms, inputHamTsms, inputSpamTytb, inputHamTytb,
              inputSpamTwtid, inputHamTwtid, inputFileNumber,inputSpamPercentage);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/dataset/home";
        }
    }
}