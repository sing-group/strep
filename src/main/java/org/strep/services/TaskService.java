/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.strep.domain.Dataset;
import org.strep.domain.Datatype;
import org.strep.domain.Language;
import org.strep.domain.License;
import org.strep.domain.TaskCreateSdataset;
import org.strep.domain.TaskCreateUPreprocessing;
import org.strep.domain.TaskCreateUdataset;
import org.strep.repositories.DatasetRepository;
import org.strep.repositories.DatatypeRepository;
import org.strep.repositories.FileRepository;
import org.strep.repositories.LanguageRepository;
import org.strep.repositories.LicenseRepository;
import org.strep.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.strep.domain.Task;

/*
* Service class to abstract to the controller of tasks jobs not related to the presentation
* @author Ismael Vázquez
* @author José Ramón Méndez
 */
@Service
public class TaskService {

    /**
     * The repository to access datasets data
     */
    @Autowired
    private DatasetRepository datasetRepository;

    /**
     * The repository to access task data
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * The repository to access datatypes data
     */
    @Autowired
    private DatatypeRepository datatypeRepository;

    /**
     * The repository to access license data
     */
    @Autowired
    private LicenseRepository licenseRepository;

    /**
     * The repository to access language data
     */
    @Autowired
    private LanguageRepository languageRepository;

    /**
     * The datasets service
     */
    @Autowired
    private DatasetService datasetService;

    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * The path of the stored XML pipeline files
     */
    @Value("${pipeline.storage}")
    private String PIPELINE_PATH;

    /**
     * The path of the stored csv result of the preprocessing tasks
     */
    @Value("${csv.storage}")
    private String OUTPUT_PATH;

    @Autowired
    private FileRepository fileRepository;

    /**
     * To parse dates
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Insert a system task to the database
     *
     * @param dataset the dataset
     */
    public void addNewSystemTask(Dataset dataset) {
        TaskCreateSdataset taskCreateSdataset = new TaskCreateSdataset(dataset, Task.STATE_WAITING, null);
        //dataset.setTask(taskCreateSdataset);
        if (dataset.getTasks() == null) {
            dataset.setTasks(new ArrayList<Task>());
        }
        dataset.getTasks().add(taskCreateSdataset);
        taskRepository.save(taskCreateSdataset);
        datasetRepository.save(dataset);
    }

    /**
     * This method creates a new user task
     *
     * @param dataset
     * @param licenses
     * @param languages
     * @param datatypes
     * @param datasets
     * @param dateFrom
     * @param dateTo
     * @param inputSpamEml
     * @param inputHamEml
     * @param inputSpamWarc
     * @param inputHamWarc
     * @param inputSpamTsms
     * @param inputHamTsms
     * @param inputSpamYtbid
     * @param inputHamYtbid
     * @param inputSpamTwtid
     * @param inputHamTwtid
     * @param fileNumberInput
     * @param inputSpamPercentage
     * @param username
     * @param spamMode
     *
     * @return
     */
    public String addNewUserDatasetTask(Dataset dataset, List<String> licenses, List<String> languages, List<String> datatypes,
            List<String> datasets, Date dateFrom, Date dateTo, int inputSpamEml, int inputHamEml, int inputSpamWarc,
            int inputHamWarc, int inputSpamTsms, int inputHamTsms, int inputSpamYtbid, int inputHamYtbid,
            int inputSpamTwtid, int inputHamTwtid, int fileNumberInput, int inputSpamPercentage, String username, boolean spamMode) {

        Locale locale = LocaleContextHolder.getLocale();
        String message = "";

        ArrayList<License> licensesArray = toArrayListLicenses(licenses);
        ArrayList<Language> languagesArray = toArrayListLanguages(languages);
        ArrayList<Datatype> datatypesArray = toArrayListDatatypes(datatypes);
        ArrayList<Dataset> datasetsArray = toArrayListDatasets(datasets);

        if (datasets == null) {
            message = messageSource.getMessage("task.create.dataset.fail.nodatasetselected", Stream.of().toArray(String[]::new), locale);
        } else {
            String shortName = dataset.getName().replaceAll(" ", "");
            dataset.setName(shortName);

            Optional<Dataset> optDataset = datasetRepository.findById(dataset.getName());

            if (optDataset.isPresent()) {
                message = messageSource.getMessage("task.create.dataset.fail.alreadyexists", Stream.of().toArray(String[]::new), locale);
            } else if (!correctFilters(datasets, inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc,
                    inputSpamTsms, inputHamTsms, inputSpamYtbid, inputHamYtbid, inputSpamTwtid, inputHamTwtid, fileNumberInput,
                        inputSpamPercentage, spamMode, languages, datatypes, licenses, dateFrom, dateTo)) {

                message = messageSource.getMessage("task.create.dataset.fail.noenougthfiles", Stream.of().toArray(String[]::new), locale);
            } else {
                if (!dateFrom.equals("") && !dateTo.equals("")) {
                    datasetRepository.save(dataset);
                    TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, Task.STATE_WAITING, null, inputSpamPercentage,
                            fileNumberInput, dateFrom, dateTo, languagesArray, datatypesArray, licensesArray, datasetsArray,
                                inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, inputSpamYtbid, inputHamYtbid, inputSpamTsms, inputHamTsms,
                            inputSpamTwtid, inputHamTwtid, spamMode);
                    Dataset toSaveDataset = datasetService.addUserDataset(dataset, username);
                    
                    if (toSaveDataset.getTasks() == null) {
                        toSaveDataset.setTasks(new ArrayList<>());
                    }
                    toSaveDataset.getTasks().add(taskCreateUdataset);
                    taskRepository.save(taskCreateUdataset);
                    datasetRepository.save(toSaveDataset);

                } else {

                    datasetRepository.save(dataset);
                    TaskCreateUdataset taskCreateUdataset = new TaskCreateUdataset(dataset, Task.STATE_WAITING, null, inputSpamPercentage,
                            fileNumberInput, null, null, languagesArray, datatypesArray, licensesArray, datasetsArray,
                                inputSpamEml, inputHamEml, inputSpamWarc, inputHamWarc, inputSpamYtbid, inputHamYtbid, inputSpamTsms, inputHamTsms,
                            inputSpamTwtid, inputHamTwtid, spamMode);
                    Dataset toSaveDataset = datasetService.addUserDataset(dataset, username);
                    
                    if (toSaveDataset.getTasks() == null) {
                        toSaveDataset.setTasks(new ArrayList<>());
                    }
                    toSaveDataset.getTasks().add(taskCreateUdataset);
                    taskRepository.save(taskCreateUdataset);
                    datasetRepository.save(toSaveDataset);

                }

                message = messageSource.getMessage("task.create.dataset.sucessfull", Stream.of().toArray(String[]::new), locale);
            }
        }

        return message;
    }

    public String createPreprocessingTask(Dataset dataset, TaskCreateUPreprocessing task, byte[] pipeline) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = "";
        try {
            TaskCreateUPreprocessing toCreateTask = new TaskCreateUPreprocessing(dataset, task.getName(), Task.STATE_WAITING, null, task.getDescription(), pipeline, null, new Date());
            taskRepository.save(toCreateTask);
            message = messageSource.getMessage("createpreprocessing.sucessfull.message", Stream.of().toArray(String[]::new), locale);
        } catch (Exception exception) {
            message = messageSource.getMessage("createpreprocessing.pipeline.error", Stream.of(pipeline).toArray(String[]::new), locale);

            return message;
        }

        return message;

    }

    /**
     * This method take the pipeline of the specified task in the database and
     * store if not exists in the pipeline storage
     *
     * @param taskId the id of the task
     * @param username the username that want to download the pipeline
     * @return the name of the file in the pipeline storage or null if it's not
     * accesible
     */
    public String downloadPipeline(Long taskId, String username) {
        String fileName = null;

        TaskCreateUPreprocessing task = taskRepository.findTaskCreateUPreprocessingById(taskId);
        //Dataset dataset = task.getPreprocessDataset();
        Dataset dataset = task.getDataset();
        String name = dataset.getName() + taskId + ".xml";
        if (dataset.getAuthor().equals(username)) {
            java.io.File file = new java.io.File(PIPELINE_PATH + name);
            if (file.exists()) {
                fileName = name;
            } else {
                byte[] pipeline = task.getPipeline();

                FileOutputStream fos;

                try {
                    fos = new FileOutputStream(file);
                    fos.write(pipeline);
                    fos.close();

                    fileName = name;
                } catch (IOException ioException) {
                    return null;
                }
            }
        }
        return fileName;
    }

    /**
     * This method return the filename of the csv if it's available or null if
     * it's not
     *
     * @param taskId the id of the task
     * @param username the username of the user that want to download the csv
     * @return the filename of the csv if it's available or null if it's not
     */
    public String downloadCsv(Long taskId, String username) {
        String fileName = null;

        TaskCreateUPreprocessing task = taskRepository.findTaskCreateUPreprocessingById(taskId);
        //Dataset dataset = task.getPreprocessDataset();
        Dataset dataset = task.getDataset();
        String name = dataset.getName() + taskId + ".csv";
        if (dataset.getAuthor().equals(username)) {
            java.io.File file = new java.io.File(OUTPUT_PATH + name);
            if (file.exists()) {
                fileName = name;
            }
        }
        return fileName;
    }

    // Method for check if there are enought files with the indicated datatypes in the
    // selected datasets
    private boolean correctFilters(List<String> datasetNames, int inputSpamEml, int inputHamEml, int inputSpamWarc, int inputHamWarc,
            int inputSpamTsms, int inputHamTsms, int inputSpamYtbid, int inputHamYtbid, int inputSpamTwtid, int inputHamTwtid, int fileNumberInput, int inputSpamPercentage, boolean spamMode,
            List<String> languages, List<String> sdatatypes, List<String> licenses, Date date1, Date date2 //filters
            ) {

        boolean success = true;
        //ArrayList<String> datasets = new ArrayList<String>();

        //for (String datasetName : datasetNames) {
        //    datasets.add(datasetName);
        //}

        if (!spamMode) {
            int total = inputSpamEml + inputHamEml + inputSpamWarc + inputHamWarc + inputSpamTsms + inputHamTsms + inputSpamYtbid + inputHamYtbid + inputSpamTwtid + inputHamTwtid;

            if ((total != 100 && total != 0) || (total == 0 && inputSpamPercentage == 0) || fileNumberInput == 0) {
                success = false;
            } else {
                HashMap<String, Integer> necesaryFilesMap = new HashMap<String, Integer>();

                necesaryFilesMap.put(".emlspam", (int) Math.ceil((double) fileNumberInput * ((double) inputSpamEml / 100.00)));
                necesaryFilesMap.put(".emlham", (int) Math.ceil((double) fileNumberInput * ((double) inputHamEml / 100.00)));

                necesaryFilesMap.put(".warcspam", (int) Math.ceil((double) fileNumberInput * ((double) inputSpamWarc / 100.00)));
                necesaryFilesMap.put(".warcham", (int) Math.ceil((double) fileNumberInput * ((double) inputHamWarc / 100.00)));

                necesaryFilesMap.put(".tsmsspam", (int) Math.ceil((double) fileNumberInput * ((double) inputSpamTsms / 100.00)));
                necesaryFilesMap.put(".tsmsham", (int) Math.ceil((double) fileNumberInput * ((double) inputHamTsms / 100.00)));

                necesaryFilesMap.put(".ytbidspam", (int) Math.ceil((double) fileNumberInput * ((double) inputSpamYtbid / 100.00)));
                necesaryFilesMap.put(".ytbidham", (int) Math.ceil((double) fileNumberInput * ((double) inputHamYtbid / 100.00)));

                necesaryFilesMap.put(".twtidspam", (int) Math.ceil((double) fileNumberInput * ((double) inputSpamTwtid / 100.00)));
                necesaryFilesMap.put(".twtidham", (int) Math.ceil((double) fileNumberInput * ((double) inputHamTwtid / 100.00)));

                HashMap<String, Integer> databaseFilesMap = new HashMap<String, Integer>();
    
            databaseFilesMap.put(".emlspam", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".eml").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "spam"));
            databaseFilesMap.put(".emlham", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".eml").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "ham"));

            databaseFilesMap.put(".warcspam", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".warc").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "spam"));
            databaseFilesMap.put(".warcham", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".warc").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "ham"));

            databaseFilesMap.put(".tsmsspam", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".tsms").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "spam"));
            databaseFilesMap.put(".tsmsham", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".tsms").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "ham"));

            databaseFilesMap.put(".ytbidspam", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".ytbid").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "spam"));
            databaseFilesMap.put(".ytbidham", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".ytbid").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "ham"));

            databaseFilesMap.put(".twtidspam", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".twtid").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "spam"));
            databaseFilesMap.put(".twtidham", 
                fileRepository.countSystemDatasetFilesByType(datasetNames, languages, Stream.of(".twtid").collect(Collectors.toCollection(ArrayList::new)), licenses, date1, date2, "ham"));

                Set<String> keys = databaseFilesMap.keySet();

                for (String key : keys) {
                    if (databaseFilesMap.get(key) < necesaryFilesMap.get(key)) {
                        success = false;
                        break;
                    }
                }
            }
        } else {
           int necesarySpamFiles = (int) Math.ceil((double) fileNumberInput * ((double) inputSpamPercentage / 100.00));
           //int availableSpamFiles = datasetRepository.countFilesByType(datasets, "spam");

           int necesaryHamFiles = fileNumberInput - necesarySpamFiles;
           //int availableHamFiles = datasetRepository.countFilesByType(datasets, "ham");


           int availableSpamFiles = fileRepository.countSystemDatasetFilesByType(datasetNames, languages, sdatatypes, licenses, date1, date2, "spam");
           int availableHamFiles = fileRepository.countSystemDatasetFilesByType(datasetNames, languages, sdatatypes, licenses, date1, date2, "ham");

            success = !(availableSpamFiles < necesarySpamFiles || availableHamFiles < necesaryHamFiles);
        }

        return success;
    }

    //Method for convert array to arraylist
    private ArrayList<Dataset> toArrayListDatasets(List<String> datasets) {
        ArrayList<Dataset> datasetsArray = new ArrayList<Dataset>();

        for (String datasetName : datasets) {
            Optional<Dataset> optDataset = datasetRepository.findById(datasetName);
            if (optDataset.isPresent()) {
                datasetsArray.add(optDataset.get());
            }
        }

        return datasetsArray;
    }

    //Method for convert array to arraylist
    private ArrayList<Datatype> toArrayListDatatypes(List<String> datatypes) {
        ArrayList<Datatype> datatypesArray = new ArrayList<Datatype>();

        for (String datatypeName : datatypes) {
            Optional<Datatype> optDatatype = datatypeRepository.findById(datatypeName);
            if (optDatatype.isPresent()) {
                datatypesArray.add(optDatatype.get());
            }
        }

        return datatypesArray;
    }

    //Method for convert array to arraylist
    private ArrayList<License> toArrayListLicenses(List<String> licenses) {
        ArrayList<License> licensesArray = new ArrayList<License>();

        for (String licenseName : licenses) {
            Optional<License> optLicense = licenseRepository.findById(licenseName);
            if (optLicense.isPresent()) {
                licensesArray.add(optLicense.get());
            }
        }

        return licensesArray;
    }

    //Method for convert array to arraylist
    private ArrayList<Language> toArrayListLanguages(List<String> languages) {
        ArrayList<Language> languagesArray = new ArrayList<Language>();

        for (String languageName : languages) {
            Optional<Language> optLanguage = languageRepository.findById(languageName);
            if (optLanguage.isPresent()) {
                languagesArray.add(optLanguage.get());
            }
        }

        return languagesArray;
    }

}
