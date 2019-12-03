package org.strep.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.strep.domain.Dataset;
import org.strep.domain.Datatype;
import org.strep.domain.Language;
import org.strep.domain.License;
import org.strep.domain.User;
import org.strep.domain.Permission;
import org.strep.repositories.DatasetRepository;
import org.strep.repositories.DatatypeRepository;
import org.strep.repositories.FileRepository;
import org.strep.repositories.LanguageRepository;
import org.strep.repositories.LicenseRepository;
import org.strep.services.DatasetService;
import org.strep.services.TaskService;
import org.strep.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This controller responds to all requests related to datasets
 */
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
    private FileRepository fileRepository;

    /**
     * To parse dates
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/public")
    public String listPublicDatasets(User user, Model model) {
        HashSet<Dataset> datasets = datasetRepository.getPublicDatasets();
        model.addAttribute("datasets", datasets);
        return "public_datasets";
    }

    @GetMapping("/public/detailed")
    public String detailPublicDataset(@RequestParam("id") String name, Model model, User user) {
        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent() && opt.get().getAccess().equals(Dataset.ACCESS_PUBLIC)) {
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

    @GetMapping("/public/detailed/{name}")
    public String shareDataset(@PathVariable String name, Model model, User user) {
        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent() && opt.get().getAccess().equals(Dataset.ACCESS_PUBLIC)) {
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
            User user) throws FileNotFoundException {
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
    public String listDatasets(@RequestParam(name = "type", required = false, defaultValue = "user") String type,
            Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        ArrayList<Dataset> datasets = new ArrayList<>();
        switch (type) {
            case "community":
                datasets = datasetRepository.getCommunityDatasets(username, Dataset.TYPE_USER);
                break;

            case "user":               
                // You have to do this because in case of view permission, default view is communityDatasets but, default type is always user
                if (authority.equals(Permission.VIEW)) {
                    datasets = datasetRepository.getCommunityDatasets(username, Dataset.TYPE_USER);
                } else {
                    datasets = datasetRepository.getOwnDatasets(username, Dataset.TYPE_USER);
                }
                break;

            case "usersystem":
                if (authority.equals(Permission.ADMINISTER)) {
                    datasets = datasetRepository.getSystemDatasets();
                } else {
                    datasets = datasetRepository.getSystemDatasets(username, Dataset.TYPE_SYSTEM);
                }
                break;

            default:
                datasets = datasetRepository.getCommunityDatasets(username, Dataset.TYPE_USER);
                type = "community";
        }
        model.addAttribute("type", type);
        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("datasets", datasets);
        return "list_datasets";
    }

    @GetMapping("/detailed")
    public String detailDataset(Authentication authentication, @RequestParam("id") String name, Model model) {
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

            if (dataset.getAccess().equals(Dataset.ACCESS_PRIVATE)) {
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
            @RequestParam("id") String name, Model model, User user) throws FileNotFoundException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent()) {

            Dataset dataset = opt.get();
            String owner = dataset.getAuthor();

            if (dataset.getAccess().equals(Dataset.ACCESS_PUBLIC) || dataset.getAccess().equals(Dataset.ACCESS_PROTECTED)
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
    public String deleteDataset(Authentication authentication, Model model, @RequestParam("id") String name, RedirectAttributes redirectAttributes) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Locale locale = LocaleContextHolder.getLocale();

        String username = userDetails.getUsername();
        Optional<Dataset> opt = datasetRepository.findById(name);

        if (opt.isPresent() && opt.get().getAuthor().equals(username)) {
            Dataset dataset = opt.get();
            if (datasetService.deleteDataset(dataset)) {
                model.addAttribute(
                        "message",
                        messageSource.getMessage("delete.dataset.sucess", Stream.of().toArray(String[]::new), locale)
                );
            } else {
                model.addAttribute(
                        "message",
                        messageSource.getMessage("delete.dataset.fail", Stream.of().toArray(String[]::new), locale)
                );
            }
        } else {
            model.addAttribute(
                    "message",
                    messageSource.getMessage("delete.dataset.fail", Stream.of().toArray(String[]::new), locale)
            );
        }

        return "redirect:/dataset/list";
    }

    @GetMapping("/access")
    public String changeAccess(Authentication authentication, Model model, @RequestParam("id") String name,
            @RequestParam("access") String access) {

        Locale locale = LocaleContextHolder.getLocale();
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

            if (access.equals(Dataset.ACCESS_PRIVATE) || access.equals(Dataset.ACCESS_PUBLIC) || access.equals(Dataset.ACCESS_PROTECTED)) {
                dataset.setAccess(access);
                datasetRepository.save(dataset);
                model.addAttribute("message",
                        messageSource.getMessage("changeacess.dataset.sucess", Stream.of(access).toArray(String[]::new), locale)
                );
            } else {
                model.addAttribute("message",
                        messageSource.getMessage("changeacess.dataset.fail.invalidaccess", Stream.of(access).toArray(String[]::new), locale)
                );
            }
        } else {
            model.addAttribute("message",
                    messageSource.getMessage("changeacess.dataset.fail.datasetnotfound", Stream.of().toArray(String[]::new), locale)
            );
        }

        return "list_datasets";

    }

    @GetMapping("/upload")
    public String uploadDataset(Authentication authentication, Model model, Dataset dataset) {

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
    public String uploadDataset(Authentication authentication, @Valid Dataset dataset, BindingResult bindingResult,
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
            model.addAttribute("dataset",dataset);
            return "add_dataset";
        } else {
            String message = datasetService.uploadDataset(dataset, datasetFile, username);
            redirectAttributes.addFlashAttribute("message", message);
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "redirect:/dataset/list?type=user";
        }

    }

    @GetMapping("/edit")
    public String editDataset(Authentication authentication, @RequestParam("id") String name, Model model, Dataset dataset) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        Optional<Dataset> optDataset = datasetRepository.findById(name);

        if (optDataset.isPresent()) {
            Dataset toUpdateDataset = optDataset.get();

            if (toUpdateDataset.getAuthor().equals(username)) {
                model.addAttribute("authority", authority);
                model.addAttribute("username", username);
                model.addAttribute("host", HOST_NAME);
                model.addAttribute("licenses", licenseRepository.findAll());
                model.addAttribute("toUpdateDataset", toUpdateDataset);
                return "edit_dataset";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/edit")
    public String editDataset(Authentication authentication, Model model, @Valid Dataset dataset, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam(name = "id") String name) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        Optional<Dataset> optDataset = datasetRepository.findById(name);

        if (bindingResult.hasErrors()) {
            //TODO: Revisar este mensaje de error
            //System.out.println("ERRORES");
            if (optDataset.isPresent()) {
                Dataset toUpdateDataset = optDataset.get();

                if (toUpdateDataset.getAuthor().equals(username)) {
                    model.addAttribute("authority", authority);
                    model.addAttribute("username", username);
                    model.addAttribute("host", HOST_NAME);
                    model.addAttribute("licenses", licenseRepository.findAll());
                    model.addAttribute("toUpdateDataset", toUpdateDataset);
                    return "edit_dataset";
                } else {
                    return "redirect:/error";
                }

            } else {
                return "redirect:/error";
            }

        } else {
            String message = datasetService.updateDataset(dataset, username);
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
            if (dataset.getType().equals(Dataset.TYPE_SYSTEM)) {
                model.addAttribute("dataset", dataset);
            }
        }

        return "create_dataset::dataset";
    }

    @GetMapping("/createlist")
    public String filterDatasets(Authentication authentication, Model model,
            @RequestParam(name = "language", required = false) String[] languages,
            @RequestParam(name = "datatype", required = false) String[] datatypes,
            @RequestParam(name = "license", required = false) String[] licenses,
            @RequestParam(name = "date1", required = false) String date1,
            @RequestParam(name = "date2", required = false) String date2) {

        ArrayList<Dataset> datasets = datasetService.getFilteredDatasets(languages, datatypes, licenses, date1, date2);
        model.addAttribute("datasets", datasets);

        return "create_dataset::datasets-list";
    }

    @GetMapping("/updateDatatypesTable")
    public String updateDatatypesTable(Model model,
            @RequestParam(name = "datasets", required = false) String[] datasetNames) {
        ArrayList<String> datatypes = new ArrayList<>();
        ArrayList<String> datasets = new ArrayList<>();

        if (datasetNames != null) {
            for (String datasetName : datasetNames) {
                datasets.add(datasetName);
            }
            datatypes = datasetRepository.getDatasetsDatatypes(datasets);
        }
        model.addAttribute("tableDatatypes", datatypes);

        return "create_dataset::datatypes-table";
    }

    /**
     * Check spam percentage data
     *
     * @param model The model
     * @param inputSpam The percentage of spam messages desired
     * @param datasets The selected datasets
     * @param fileNumber The number of files to be included in the dataset
     * @return The view fragment that is going to be updated
     */
    @GetMapping("/checkPosibleSpam")
    public String showInfoSpam(Model model, @RequestParam(name = "inputSpam", required = false) String inputSpam,
            @RequestParam(name = "datasets", required = false) String[] datasets,
            //Now these parameters are neccesary
            @RequestParam(name = "languages", required = false) String[] languages,
            @RequestParam(name = "datatypes", required = false) String[] sdatatypes,
            @RequestParam(name = "date1", required = false) String date1,
            @RequestParam(name = "date2", required = false) String date2,
            @RequestParam(name = "licenses", required = false) String[] licenses,
            //Now the previous parameters are neccesary            
            @RequestParam(name = "fileNumber", required = false) String fileNumber) {
        int inputSpamInt = -1;
        int availableFilesSpam = -1;
        int availableFilesHam = -1;
        int neccesaryFilesSpam = -1;
        int neccesaryFilesHam = -1;
        int fileNumberInt = -1;
        Locale locale = LocaleContextHolder.getLocale();

        ArrayList<String> arrayListDatasets = new ArrayList<String>();

        if (inputSpam != "" && datasets != null && fileNumber != "") {
            for (String dataset : datasets) {
                arrayListDatasets.add(dataset);
            }

            try {
                inputSpamInt = Integer.parseInt(inputSpam);
                fileNumberInt = Integer.parseInt(fileNumber);
            } catch (NumberFormatException e) {
            }

            neccesaryFilesSpam = (int) Math.ceil((double) fileNumberInt * ((double) inputSpamInt / 100.00));
            neccesaryFilesHam = fileNumberInt - neccesaryFilesSpam;

            //Parse the received date
            Date d1=null,d2=null;
            if (date1==null || date1.equals("")){
                d1=fileRepository.getEarliestDate();
            }else try{
                d1=simpleDateFormat.parse(date1);
            }catch(ParseException pe){
                d1=fileRepository.getEarliestDate();
            }
            if (date2==null || date2.equals("")){
                d2=fileRepository.getLatestDate();
            } else try{
                d2=simpleDateFormat.parse(date2);
            }catch(ParseException pe){
                d2=fileRepository.getLatestDate();
            }

            //Parse the received languages
            List<String> l;
            if (languages == null || languages.length==0) {
                Iterable<Language> allLangs = languageRepository.findAll();
                l = StreamSupport.stream(allLangs.spliterator(), false)
                    .map(Language::getLanguage)
                    .collect(Collectors.toList());
            } else {
                l=Arrays.asList(languages);
            }

            //Parse the received datatypes
            List<String> d;
            if (sdatatypes == null || sdatatypes.length == 0) {
                Iterable<Datatype> datatypes = datatypeRepository.findAll();
                d = StreamSupport.stream(datatypes.spliterator(), false)
                    .map(Datatype::getDatatype)
                    .collect(Collectors.toList());
            } else {
                d=Arrays.asList(sdatatypes);
            }    

            //Parse the received licenses
            List<String> lic;
            if (licenses == null || licenses.length == 0) {
                Iterable<License> licens = licenseRepository.findAll();
                lic = StreamSupport.stream(licens.spliterator(), false)
                    .map(License::getName)
                    .collect(Collectors.toList());
            } else {
                lic=Arrays.asList(licenses);
            }   

            availableFilesSpam = fileRepository.countSystemDatasetFilesByType(arrayListDatasets, l, d, lic, d1, d2, "spam");
            availableFilesHam = fileRepository.countSystemDatasetFilesByType(arrayListDatasets, l, d, lic, d1, d2, "ham");

            String message = messageSource.getMessage("checkposiblespam.dataset.message", Stream.of(
                    Integer.toString(neccesaryFilesSpam),
                    Integer.toString(availableFilesSpam),
                    Integer.toString(neccesaryFilesHam),
                    Integer.toString(availableFilesHam)
            ).toArray(String[]::new), locale);
            //String message = "Necesary spam files:" + neccesaryFilesSpam + "\nAvailable spam files" + availableFilesSpam;
            //message += " / Necesary ham files:" + neccesaryFilesHam + "\nAvailable spam files" + availableFilesHam;
            if (availableFilesSpam >= neccesaryFilesSpam && availableFilesHam >= neccesaryFilesHam) {
                model.addAttribute("spamSuccessInfo", message);
            } else {
                model.addAttribute("spamInsufficientInfo", message);
            }
        } else {
            model.addAttribute("spamErrorInput",
                    messageSource.getMessage("checkposiblespam.dataset.spamerrorimput", Stream.of().toArray(String[]::new), locale)
            );
        }
        return "create_dataset::info-spam";
    }

    /**
     * Validates the input data when "Check" button is pressed
     *
     * @param model the model
     * @param inputSpamEml Spam EML percentage
     * @param inputHamEml Ham EML percentage
     * @param inputSpamWarc Spam WARC percentage
     * @param inputHamWarc Ham WARC percentage
     * @param inputSpamTsms Spam SMS percentage
     * @param inputHamTsms Ham SMS percentage
     * @param inputSpamTytb Spam YTB percentage
     * @param inputHamTytb Ham YTB percentage
     * @param inputSpamTwtid Spam TWT percentage
     * @param inputHamTwtid Ham TWV percentage
     * @param datasetNames The datasets
     * @param fileNumberInput Number of files for the new dataset
     * @return The part of the view that is going to be updated
     */
    @GetMapping("/checkPosibleDatatypes")
    public String showInfoDatatypes(Model model, 
            //Now these parameters are neccesary
            @RequestParam(name = "languages", required = false) String[] languages,
            @RequestParam(name = "datatypes", required = false) String[] sdatatypes,
            @RequestParam(name = "date1", required = false) String date1,
            @RequestParam(name = "date2", required = false) String date2,
            @RequestParam(name = "licenses", required = false) String[] licenses,
            //Now the previous parameters are neccesary
            @RequestParam("inputSpamEml") int inputSpamEml,
            @RequestParam("inputHamEml") int inputHamEml, @RequestParam("inputSpamWarc") int inputSpamWarc,
            @RequestParam("inputHamWarc") int inputHamWarc, @RequestParam("inputSpamTsms") int inputSpamTsms,
            @RequestParam("inputHamTsms") int inputHamTsms, @RequestParam("inputSpamTytb") int inputSpamTytb,
            @RequestParam("inputHamTytb") int inputHamTytb, @RequestParam("inputSpamTwtid") int inputSpamTwtid,
            @RequestParam("inputHamTwtid") int inputHamTwtid,
            @RequestParam(name = "datasets", required = false) String[] datasetNames,
            @RequestParam("inputFileNumber") int fileNumberInput) {

        Locale locale = LocaleContextHolder.getLocale();
        ArrayList<String> datatypes = new ArrayList<String>();
        ArrayList<String> datasets = new ArrayList<String>();

        if (datasetNames != null) {
            for (String datasetName : datasetNames) {
                datasets.add(datasetName);
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

        if (datasets == null || fileNumberInput == 0
                || (inputSpamEml + inputHamEml + inputSpamWarc + inputHamWarc + inputSpamTsms + inputHamTsms
                + inputSpamTytb + inputHamTytb + inputSpamTwtid + inputHamTwtid) != 100) {
            model.addAttribute("datatypesInputError",
                    messageSource.getMessage("checkposibledatatypes.dataset.datatypesinputerror", Stream.of().toArray(String[]::new), locale)
            );
        } else {
            HashMap<String, Integer> necesaryFilesMap = new HashMap<String, Integer>();

            necesaryFilesMap.put(".emlspam",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputSpamEml / 100.00)));
            necesaryFilesMap.put(".emlham",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputHamEml / 100.00)));

            necesaryFilesMap.put(".warcspam",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputSpamWarc / 100.00)));
            necesaryFilesMap.put(".warcham",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputHamWarc / 100.00)));

            necesaryFilesMap.put(".tsmsspam",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputSpamTsms / 100.00)));
            necesaryFilesMap.put(".tsmsham",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputHamTsms / 100.00)));

            necesaryFilesMap.put(".ytbidspam",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputSpamTytb / 100.00)));
            necesaryFilesMap.put(".ytbidham",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputHamTytb / 100.00)));

            necesaryFilesMap.put(".twtidspam",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputSpamTwtid / 100.00)));
            necesaryFilesMap.put(".twtidham",
                    (int) Math.ceil((double) fileNumberInput * ((double) inputHamTwtid / 100.00)));

            HashMap<String, Integer> databaseFilesMap = new HashMap<String, Integer>();

            //Compute available files

            //Parse the received date
            Date d1=null,d2=null;
            if (date1==null || date1.equals("")){
                d1=fileRepository.getEarliestDate();
            }else try{
                d1=simpleDateFormat.parse(date1);
            }catch(ParseException pe){
                d1=fileRepository.getEarliestDate();
            }
            if (date2==null || date2.equals("")){
                d2=fileRepository.getLatestDate();
            } else try{
                d2=simpleDateFormat.parse(date2);
            }catch(ParseException pe){
                d2=fileRepository.getLatestDate();
            }

            //Parse the received languages
            List<String> l;
            if (languages == null) {
                Iterable<Language> allLangs = languageRepository.findAll();
                l = StreamSupport.stream(allLangs.spliterator(), false)
                .map(Language::getLanguage)
                .collect(Collectors.toList());
            } else {
                l=Arrays.asList(languages);
            }

            //Parse the received licenses
            List<String> lic;
            if (licenses == null || licenses.length == 0) {
                Iterable<License> licens = licenseRepository.findAll();
                lic = StreamSupport.stream(licens.spliterator(), false)
                    .map(License::getName)
                    .collect(Collectors.toList());
            } else {
                lic=Arrays.asList(licenses);
            }           
    
            databaseFilesMap.put(".emlspam", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".eml").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "spam"));
            databaseFilesMap.put(".emlham", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".eml").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "ham"));

            databaseFilesMap.put(".warcspam", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".warc").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "spam"));
            databaseFilesMap.put(".warcham", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".warc").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "ham"));

            databaseFilesMap.put(".tsmsspam", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".tsms").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "spam"));
            databaseFilesMap.put(".tsmsham", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".tsms").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "ham"));

            databaseFilesMap.put(".ytbidspam", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".ytbid").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "spam"));
            databaseFilesMap.put(".ytbidham", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".ytbid").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "ham"));

            databaseFilesMap.put(".twtidspam", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".twtid").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "spam"));
            databaseFilesMap.put(".twtidham", 
                fileRepository.countSystemDatasetFilesByType(datasets, l, Stream.of(".twtid").collect(Collectors.toCollection(ArrayList::new)), lic, d1, d2, "ham"));

            Set<String> keys = databaseFilesMap.keySet();
            boolean success = true;

            for (String key : keys) {
                if (necesaryFilesMap.get(key) != 0) {
                    String subkey = key.substring(1);
                    model.addAttribute("necesary" + subkey, necesaryFilesMap.get(key));
                    model.addAttribute("available" + subkey, databaseFilesMap.get(key));
                }
                if (databaseFilesMap.get(key) < necesaryFilesMap.get(key)) {
                    success = false;
                }
            }

            if (success) {
                model.addAttribute("class", "info-label");
            } else {
                model.addAttribute("class", "error-label");
            }
        }
        return "create_dataset::datatypes-table";
    }

    @GetMapping("/create")
    public String getCreateDataset(Authentication authentication, Model model, Dataset dataset) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        ArrayList<Dataset> datasets = datasetRepository.getSystemDatasets();

        Iterable<License> licenses = licenseRepository.findAll();
        Iterable<Datatype> datatypes = datatypeRepository.findAll();
        Iterable<Language> languages = languageRepository.findAllSortedByDescription();
        

        model.addAttribute("host", HOST_NAME);
        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("datasets", datasets);
        model.addAttribute("licenses", licenses);
        model.addAttribute("datatypes", datatypes);
        model.addAttribute("languages", languages);

        return "create_dataset";
    }

    @PostMapping("/create")
    public String setCreateDataset(Authentication authentication, Model model, @Valid Dataset dataset,
            BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam(name = "datasets", required = false) String[] datasets,
            @RequestParam(name = "license", required = false) String[] licenses,
            @RequestParam(name = "languages", required = false) String[] languages,
            @RequestParam(name = "datatypes", required = false) String[] datatypes,
            @RequestParam(name = "inputSpamPercentage", required = false, defaultValue = "0") int inputSpamPercentage,
            @RequestParam(name = "inputFileNumber", required = false, defaultValue = "0") int inputFileNumber,
            @RequestParam(name = "inputSpam.eml", required = false, defaultValue = "0") int inputSpamEml,
            @RequestParam(name = "inputHam.eml", required = false, defaultValue = "0") int inputHamEml,
            @RequestParam(name = "inputSpam.warc", required = false, defaultValue = "0") int inputSpamWarc,
            @RequestParam(name = "inputHam.warc", required = false, defaultValue = "0") int inputHamWarc,
            @RequestParam(name = "inputSpam.tsms", required = false, defaultValue = "0") int inputSpamTsms,
            @RequestParam(name = "inputHam.tsms", required = false, defaultValue = "0") int inputHamTsms,
            @RequestParam(name = "inputSpam.ytbid", required = false, defaultValue = "0") int inputSpamTytb,
            @RequestParam(name = "inputHam.ytbid", required = false, defaultValue = "0") int inputHamTytb,
            @RequestParam(name = "inputSpam.twtid", required = false, defaultValue = "0") int inputSpamTwtid,
            @RequestParam(name = "inputHam.twtid", required = false, defaultValue = "0") int inputHamTwtid,
            @RequestParam(name = "date1", required = false) String dateFrom,
            @RequestParam(name = "date2", required = false) String dateTo,
            @RequestParam(name = "mode", required = false, defaultValue = "spam") String mode) {
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
            boolean modeSpam = false;

            if (mode.equals("spam")) {
                modeSpam = true;
            }

            message = taskService.addNewUserDatasetTask(dataset, licenses, languages, datatypes, datasets, dateFrom,
                    dateTo, inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, inputSpamTsms, inputHamTsms,
                    inputSpamTytb, inputHamTytb, inputSpamTwtid, inputHamTwtid, inputFileNumber, inputSpamPercentage,
                    username, modeSpam);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/dataset/list";
        }
    }
}
