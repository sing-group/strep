package com.project.onlinepreprocessor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Datatype;
import com.project.onlinepreprocessor.domain.File;
import com.project.onlinepreprocessor.domain.FileDatatypeType;
import com.project.onlinepreprocessor.domain.Language;
import com.project.onlinepreprocessor.domain.License;
import com.project.onlinepreprocessor.domain.TaskCreateSdataset;
import com.project.onlinepreprocessor.domain.TaskCreateUdataset;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.DatatypeRepository;
import com.project.onlinepreprocessor.repositories.FileDatatypeTypeRepository;
import com.project.onlinepreprocessor.repositories.LanguageRepository;
import com.project.onlinepreprocessor.repositories.LicenseRepository;
import com.project.onlinepreprocessor.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TaskService {
    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DatatypeRepository datatypeRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private FileDatatypeTypeRepository fileDatatypeTypeRepository;

    @Autowired
    private DatasetService datasetService;

    public void addNewSystemTask(Dataset dataset) {
        TaskCreateSdataset taskCreateSdataset = new TaskCreateSdataset(dataset, "waiting", null);
        dataset.setTask(taskCreateSdataset);
        taskRepository.save(taskCreateSdataset);
        datasetRepository.save(dataset);
    }

    //TODO: add pipeline and username to parameters of this method and call to method addUserDataset of datasetService
    public String addNewUserDatasetTask(Dataset dataset, String[] licenses, String[] languages, String[] datatypes,
            String[] datasets, String dateFrom, String dateTo, int inputSpamEml, int inputHamEml, int inputSpamWarc,
            int inputHamWarc, int inputSpamTsms, int inputHamTsms, int inputSpamTytb, int inputHamTytb,
            int inputSpamTwtid, int inputHamTwtid, int fileNumberInput, int inputSpamPercentage, String username, boolean spamMode) {

        String message = "";

        ArrayList<License> licensesArray = new ArrayList<License>();
        ArrayList<Language> languagesArray = new ArrayList<Language>();
        ArrayList<Datatype> datatypesArray = new ArrayList<Datatype>();
        ArrayList<Dataset> datasetsArray = new ArrayList<Dataset>();

        System.out.println(inputSpamTsms);

        if (licenses != null) {
            licensesArray = toArrayListLicenses(licenses);
        }

        if (languages != null) {
            languagesArray = toArrayListLanguages(languages);
        }

        if (datatypes != null) {
            datatypesArray = toArrayListDatatypes(datatypes);
        }

        if (datasets != null) {
            datasetsArray = toArrayListDatasets(datasets);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date dateFromFormatted;
            Date dateToFormatted;

            if (datasets == null) {
                message = "You have to select at least one dataset";
            } else {
                String shortName = dataset.getName().replaceAll(" ", "");
                dataset.setName(shortName);
                
                Optional<Dataset> optDataset = datasetRepository.findById(dataset.getName());

                if (optDataset.isPresent()) {
                    message = "Already exists a dataset with this name";
                } 
                else if (!correctFilters(datasets, inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, 
                inputSpamTsms, inputHamTsms, inputSpamTytb, inputHamTytb, inputSpamTwtid, inputHamTwtid, fileNumberInput
                ,inputSpamPercentage)) {
                    message = "There are no files to satisfy datatype percentage parameters";  
                } 
                 else {
                    if (!dateFrom.equals("") && !dateTo.equals("")) {
                        datasetRepository.save(dataset);
                        dateFromFormatted = simpleDateFormat.parse(dateFrom);
                        dateToFormatted = simpleDateFormat.parse(dateTo);
                        TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, "waiting", null, inputSpamPercentage,
                        fileNumberInput, dateFromFormatted, dateToFormatted, languagesArray, datatypesArray, licensesArray, datasetsArray
                        ,inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, inputSpamTytb, inputHamTytb, inputSpamTsms, inputHamTsms,
                        inputSpamTwtid,inputHamTwtid, spamMode);
                        Dataset toSaveDataset = datasetService.addUserDataset(dataset, username);
                        toSaveDataset.setTask(taskCreateUdataset);
                        taskRepository.save(taskCreateUdataset);
                        datasetRepository.save(toSaveDataset);

                    } else {

                        datasetRepository.save(dataset);
                        TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, "waiting", null, inputSpamPercentage,
                        fileNumberInput, null, null, languagesArray, datatypesArray, licensesArray, datasetsArray
                        ,inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, inputSpamTytb, inputHamTytb, inputSpamTsms, inputHamTsms,
                        inputSpamTwtid,inputHamTwtid, spamMode);
                        Dataset toSaveDataset = datasetService.addUserDataset(dataset, username);
                        toSaveDataset.setTask(taskCreateUdataset);
                        taskRepository.save(taskCreateUdataset);
                        datasetRepository.save(toSaveDataset);

                    }

                    message = "Task successfully generated";
                }
            }
        } catch (ParseException pe) {
            return "Error parsing date filters";
        }

        return message;
    }
    

    // Method for check if there are files with the indicated datatypes in the
    // selected datasets
    private boolean correctFilters(String[] datasetNames, int inputSpamEml, int inputHamEml, int inputSpamWarc, int inputHamWarc,
     int inputSpamTsms, int inputHamTsms, int inputSpamTytb, int inputHamTytb, int inputSpamTwtid, int inputHamTwtid, int fileNumberInput, int inputSpamPercentage) {

            boolean success = true;
            int total = inputSpamEml+inputHamEml+inputSpamWarc+inputHamWarc+inputSpamTsms+inputHamTsms+inputSpamTytb+inputHamTytb+inputSpamTwtid+inputHamTwtid;
            ArrayList<String> datasets = new ArrayList<String>();

            for(String datasetName : datasetNames)
            {
                datasets.add(datasetName);
            }

            if((total!=100 && total!=0) || (total==0 && inputSpamPercentage==0) || fileNumberInput==0)
            {
                success = false;
                System.out.println(total);
                System.out.println(inputSpamPercentage);
                System.out.println(fileNumberInput);
                System.out.println("FIRST IF");
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

            for(String key: keys)
            {
                if(databaseFilesMap.get(key) < necesaryFilesMap.get(key))
                {
                    success = false;
                    System.out.println("SECOND IF");
                }
                System.out.println(databaseFilesMap.get(key)+" vs "+necesaryFilesMap.get(key));
            }

            int necesarySpamFiles = (int) Math.ceil((double)fileNumberInput * ((double)inputSpamPercentage/100.00));
            int availableSpamFiles = datasetRepository.countSpamFiles(datasets);

            if(availableSpamFiles<necesarySpamFiles)
            {
                success = false;
                System.out.println("THIRD IF");
            }
            }

            return success;
    }

    private ArrayList<Dataset> toArrayListDatasets(String[] datasets) {
        ArrayList<Dataset> datasetsArray = new ArrayList<Dataset>();

        for (String datasetName : datasets) {
            Optional<Dataset> optDataset = datasetRepository.findById(datasetName);
            if (optDataset.isPresent()) {
                datasetsArray.add(optDataset.get());
            }
        }

        return datasetsArray;
    }

    private ArrayList<Datatype> toArrayListDatatypes(String[] datatypes) {
        ArrayList<Datatype> datatypesArray = new ArrayList<Datatype>();

        for (String datatypeName : datatypes) {
            Optional<Datatype> optDatatype = datatypeRepository.findById(datatypeName);
            if (optDatatype.isPresent()) {
                datatypesArray.add(optDatatype.get());
            }
        }

        return datatypesArray;
    }

    private ArrayList<License> toArrayListLicenses(String[] licenses) {
        ArrayList<License> licensesArray = new ArrayList<License>();

        for (String licenseName : licenses) {
            Optional<License> optLicense = licenseRepository.findById(licenseName);
            if (optLicense.isPresent()) {
                licensesArray.add(optLicense.get());
            }
        }

        return licensesArray;
    }

    private ArrayList<Language> toArrayListLanguages(String[] languages) {
        ArrayList<Language> languagesArray = new ArrayList<Language>();

        for (String languageName : languages) {
            Optional<Language> optLanguage = languageRepository.findById(languageName);
            if (optLanguage.isPresent()) {
                languagesArray.add(optLanguage.get());
            }
        }

        return languagesArray;
    }

    private int checkNullsAndEmptyStrings(String requestParam) {
        int value;

        if (requestParam == null || requestParam.equals("")) {
            value = -1;
        } else {
            try {
                value = Integer.parseInt(requestParam);
            } catch (NumberFormatException e) {
                value = -1;
            }
        }

        return value;
    }
}