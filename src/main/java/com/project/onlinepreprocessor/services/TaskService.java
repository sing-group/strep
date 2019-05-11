package com.project.onlinepreprocessor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Datatype;
import com.project.onlinepreprocessor.domain.File;
import com.project.onlinepreprocessor.domain.Language;
import com.project.onlinepreprocessor.domain.License;
import com.project.onlinepreprocessor.domain.TaskCreateSdataset;
import com.project.onlinepreprocessor.domain.TaskCreateUdataset;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.DatatypeRepository;
import com.project.onlinepreprocessor.repositories.LanguageRepository;
import com.project.onlinepreprocessor.repositories.LicenseRepository;
import com.project.onlinepreprocessor.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addNewSystemTask(Dataset dataset) {
        TaskCreateSdataset taskCreateSdataset = new TaskCreateSdataset(dataset, "waiting", null);
        taskRepository.save(taskCreateSdataset);
    }

    public String addNewUserDatasetTask(Dataset dataset, String[] licenses, String[] languages, String[] datatypes,
            String[] datasets, String limitFilesSpam, String dateFrom, String dateTo, String limitPercentageSpam,
            String limitFiles, String percentageEml, String percentageWarc, String percentageTwtid,
            String percentageTytb, String percentageTsms) {

        String message = "";

        ArrayList<License> licensesArray = new ArrayList<License>();
        ArrayList<Language> languagesArray = new ArrayList<Language>();
        ArrayList<Datatype> datatypesArray = new ArrayList<Datatype>();
        ArrayList<Dataset> datasetsArray = new ArrayList<Dataset>();

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

        int limitFilesSpamNumber = checkNullsAndEmptyStrings(limitFilesSpam);
        int limitPercentageSpamNumber = checkNullsAndEmptyStrings(limitPercentageSpam);
        int limitFilesNumber = checkNullsAndEmptyStrings(limitFiles);
        int limitPercentageEmlNumber = checkNullsAndEmptyStrings(percentageEml);
        int limitPercentageWarcNumber = checkNullsAndEmptyStrings(percentageWarc);
        int limitPercentageTwtidNumber = checkNullsAndEmptyStrings(percentageTwtid);
        int limitPercentageTytbNumber = checkNullsAndEmptyStrings(percentageTytb);
        int limitPercentageTsmsNumber = checkNullsAndEmptyStrings(percentageTsms);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date dateFromFormatted;
            Date dateToFormatted;

            if (datasets == null) {
                message = "You have to select at least one dataset";
            } else {
                Optional<Dataset> optDataset = datasetRepository.findById(dataset.getName());

                if (optDataset.isPresent()) {
                    message = "Already exists a dataset with this name";
                } 
                else if (!correctFilters(datatypes, datasets, percentageEml, percentageWarc, percentageTwtid,
                        percentageTytb, percentageTsms)) {
                    message = "There are no files to satisfy datatype percentage parameters";
                    
                } 
                else if(!invalidPercentages(datatypes, percentageEml, percentageWarc, percentageTwtid, percentageTytb, percentageTsms))
                {
                    message = "Datatypes parameters percentages incorrects";
                }
                else if (!correctParameters(datasets, limitFilesSpam, limitPercentageSpam, limitFiles)) {
                    message = "Incompatible parameters";
                } else {
                    if (!dateFrom.equals("") && !dateTo.equals("")) {

                        datasetRepository.save(dataset);
                        dateFromFormatted = simpleDateFormat.parse(dateFrom);
                        dateToFormatted = simpleDateFormat.parse(dateTo);
                        TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, "waiting", null,
                                limitPercentageSpamNumber, limitFilesSpamNumber, limitFilesNumber, dateFromFormatted,
                                dateToFormatted, languagesArray, datatypesArray, licensesArray, datasetsArray,
                                limitPercentageEmlNumber, limitPercentageTytbNumber, limitPercentageTsmsNumber,
                                limitPercentageTwtidNumber, limitPercentageWarcNumber);
                        taskRepository.save(taskCreateUdataset);

                    } else {

                        datasetRepository.save(dataset);
                        TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, "waiting", null,
                                limitPercentageSpamNumber, limitFilesSpamNumber, limitFilesNumber, null, null,
                                languagesArray, datatypesArray, licensesArray, datasetsArray, limitPercentageEmlNumber,
                                limitPercentageTytbNumber, limitPercentageTsmsNumber, limitPercentageTwtidNumber,
                                limitPercentageWarcNumber);
                        taskRepository.save(taskCreateUdataset);

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
    private boolean correctFilters(String[] datatypes, String[] datasets, String percentageEml, String percentageWarc,
            String percentageTwtid, String percentageTytb, String percentageTsms) {
        boolean correct = false;
        ArrayList<String> datasetsArray = new ArrayList<String>();
        ArrayList<String> filtersDatatypesArray = new ArrayList<String>();
        ArrayList<String> datasetsDatatypesArray = new ArrayList<String>();

        if (datatypes == null) {
            System.out.println("Datatypes null");
            correct = true;
        } else {

            for(String dataset : datasets)
            {
                datasetsArray.add(dataset);
            }

            datasetsDatatypesArray = datasetRepository.getDatasetsDatatypes(datasetsArray);

            for(String datatype : datatypes)
            {
                if(indicatedPercentage(datatype, percentageEml, percentageWarc, percentageTwtid, percentageTytb, percentageTsms))
                {
                    filtersDatatypesArray.add(datatype);
                }
            }
            if(!filtersDatatypesArray.isEmpty())
            {
                if(new HashSet<String>(filtersDatatypesArray).equals(new HashSet<String>(datasetsDatatypesArray)))
                {
                    correct = true;
                }
                else
                {
                    correct = false;
                }
            }
            else
            {
                correct = true;
            }
        }

        return correct;
    }

    private boolean indicatedPercentage(String datatype, String percentageEml, String percentageWarc,
            String percentageTwtid, String percentageTytb, String percentageTsms) {
        boolean indicatedPercentage = false;

        switch (datatype) {
        case ".eml":
            if (!percentageEml.equals("")) {
                indicatedPercentage = true;
            }
            break;
        case ".warc":
            if (!percentageWarc.equals("")) {
                indicatedPercentage = true;
            }
            break;
        case ".twtid":
            if (!percentageTwtid.equals("")) {
                indicatedPercentage = true;
            }
            break;
        case ".tytb":
            if (!percentageTytb.equals("")) {
                indicatedPercentage = true;
            }
            break;
        case ".tsms":
            if (!percentageTsms.equals("")) {
                indicatedPercentage = true;
            }
            break;
        default:
            System.out.println("Default condition");
            break;
        }
        return indicatedPercentage;
    }

    // TODO: Complete this method
    // Method to check if the parameters are compatible
    private boolean correctParameters(String[] datasets, String limitFilesSpam, String limitPercentageSpam,
            String limitFiles) {

        boolean correct = true;

        int limitFilesSpamNumber = checkNullsAndEmptyStrings(limitFilesSpam);
        int limitPercentageSpamNumber = checkNullsAndEmptyStrings(limitPercentageSpam);
        int limitFilesNumber = checkNullsAndEmptyStrings(limitFiles);

        int numFilesDatasets = 0;
        int percentageSpamDatasets = 0;
        int spamFilesDatasets = 0;

        ArrayList<Dataset> datasetsArray = new ArrayList<Dataset>();

        if ((!limitPercentageSpam.equals("") && limitPercentageSpamNumber > 100)
                || (!limitPercentageSpam.equals("") && limitPercentageSpamNumber < 0)
                || (!limitFilesSpam.equals("") && limitFilesSpamNumber < 0)
                || (!limitFiles.equals("") && limitFilesNumber < 0)) {
            correct = false;
        } else {
            datasetsArray = toArrayListDatasets(datasets);

            for (Dataset dataset : datasetsArray) {
                for (File file : dataset.getFiles()) {
                    System.out.println("File name = " + file.getPath());
                    System.out.println("Type = " + file.getType());
                    if (file.getType().equals("spam")) {
                        spamFilesDatasets += 1;
                    }
                    numFilesDatasets += 1;
                }
            }
            try
            {
            percentageSpamDatasets = (spamFilesDatasets * 100) / numFilesDatasets;
            }catch(ArithmeticException e)
            {
                //TODO: Change this
                correct = true;
            }
        }

        return correct;
    }

    //method to check if the sum of the datatypes filters percentages is 100 
    private boolean invalidPercentages(String[] datatypes, String percentageEml, String percentageWarc, String percentageTwtid, String percentageTytb, String percentageTsms)
    {
        boolean validPercentages = false;
        int percentagesValue = 0;

        if(datatypes==null)
        {
            validPercentages = true;
        }
        else
        {
            int percentageEmlNumber = checkNullsAndEmptyStrings(percentageEml);
            int percentageWarcNumber = checkNullsAndEmptyStrings(percentageWarc);
            int percentageTwtidNumber = checkNullsAndEmptyStrings(percentageTwtid);
            int percentageTytbNumber = checkNullsAndEmptyStrings(percentageTytb);
            int percentageTsmsNumber = checkNullsAndEmptyStrings(percentageTsms);

            if(percentageEmlNumber>=0 || percentageWarcNumber>=0 || percentageTwtidNumber>=0 || percentageTytbNumber>=0 || percentageTsmsNumber>=0)
            {
                percentagesValue += sumIfNotNegative(percentageEmlNumber);
                percentagesValue += sumIfNotNegative(percentageWarcNumber);
                percentagesValue += sumIfNotNegative(percentageTwtidNumber);
                percentagesValue += sumIfNotNegative(percentageTytbNumber);
                percentagesValue += sumIfNotNegative(percentageTsmsNumber);

                if(percentagesValue == 100)
                {
                    validPercentages = true;
                }
                else
                {
                    validPercentages = false;
                }
            }
            else
            {
                validPercentages = true;
            }
        }

        return validPercentages;
    }

    private int sumIfNotNegative(int percentage)
    {
        int result = 0;

        if(percentage>=0)
        {
            result+=percentage;
        }
        
        return result;
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