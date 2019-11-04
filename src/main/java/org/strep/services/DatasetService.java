package org.strep.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.strep.domain.Dataset;
import org.strep.domain.Datatype;
import org.strep.domain.File;
import org.strep.domain.Language;
import org.strep.domain.License;
import org.strep.repositories.DatasetRepository;
import org.strep.repositories.DatatypeRepository;
import org.strep.repositories.FileRepository;
import org.strep.repositories.LanguageRepository;
import org.strep.repositories.LicenseRepository;
import org.strep.repositories.TaskRepository;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class to abstract to the controller of dataset tasks not related to the presentation
 * @author Ismael Vázquez
 */
@Service
public class DatasetService
{

    /**
     * The path of the file system where datasets are stored
     */
    @Value("${dataset.storage}")
    private String BASE_PATH;

    /**
     * The repository to access datasets data
     */
    @Autowired 
    private DatasetRepository datasetRepository;

    /**
     * The repository to access files data
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     * The repository to access licenses data
     */
    @Autowired
    private LicenseRepository licenseRepository;

    /**
     * The repository to access languages data
     */
    @Autowired
    private LanguageRepository languageRepository;

    /**
     * The repository to access datatypes data
     */
    @Autowired 
    private DatatypeRepository datatypeRepository;

    /**
     * The repository to access tasks data
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Task service object to perform task related jobs
     */
    @Autowired
    private TaskService taskService;

    /**
     * The name of the host
     */
    @Value("${host.name}")
    private String HOST_NAME;

    /**
     * The message i18n
     */
    @Autowired
    private MessageSource messageSource;  

    /**
     * The constructor for instance DatasetService objects
     * @param datasetRepository the repository to access datasets data
     * @param fileRepository The repository to access files data
     */
    public DatasetService(DatasetRepository datasetRepository, FileRepository fileRepository)
    {
        this.datasetRepository = datasetRepository;
        this.fileRepository = fileRepository;
    }

    /**
     * This method make the dataset zip file available
     * @param name the name of the dataset to create zip file
     * @return true if zip available, false in the other case
     */
    public boolean getDownloadFiles(String name)
    {

        java.io.File datasetDirectory = new java.io.File(BASE_PATH+name+java.io.File.separator);

        if(!datasetDirectory.exists())
        {
            return false;
        }


        ArrayList<BigInteger> ids =datasetRepository.getFileIds(name);

        ArrayList<File> files = new ArrayList<File>();

        for(int i=0; i<ids.size();i++)
        {
            Long id = ids.get(i).longValue();

            Optional<File> opt = fileRepository.findById(id);

            if(opt.isPresent())
            {
                files.add(opt.get());
            }
        }

        java.io.File zipResult = new java.io.File(BASE_PATH+name+".zip");

        if(!zipResult.exists() || datasetDirectory.lastModified()>zipResult.lastModified())
        {
        //java.io.File directoryHam = new java.io.File(datasetDirectory, "_ham_"+java.io.File.separator);
        //java.io.File directorySpam = new java.io.File(datasetDirectory, "_spam_"+java.io.File.separator);

        ArrayList<java.io.File> spamFiles = new ArrayList<>();
        ArrayList<java.io.File> hamFiles = new ArrayList<>();

        for(int i = 0; i<files.size();i++)
        {
                org.strep.domain.File file = files.get(i);

            if(file.getType().equals("ham"))
            {
                java.io.File file1 = new java.io.File(file.getPath()+file.getExtension());
                if(file1.exists())
                {
                hamFiles.add(file1);
                }
            }
            else
            {
                java.io.File file1 = new java.io.File(file.getPath()+file.getExtension());
                if(file1.exists())
                {
                spamFiles.add(file1);
                }
            }
        }

        java.io.File zippedFile = new java.io.File(BASE_PATH+name+".zip");

        try{
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zippedFile));
            zip.putNextEntry(new ZipEntry("_ham_"+java.io.File.separator));
            
            for(int i = 0;i<hamFiles.size();i++)
            {
                byte[] buf = new byte[1024];
                java.io.File file = hamFiles.get(i);
                zip.putNextEntry(new ZipEntry("_ham_"+java.io.File.separator+file.getName()));
                FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                int moreText = 0;
                while((moreText=fis.read())!=-1)
                {
                    zip.write(buf, 0, moreText);
                }
                fis.close();
                zip.closeEntry();
            }

            zip.closeEntry();

            zip.putNextEntry(new ZipEntry("_spam_"+java.io.File.separator));

            for(int i = 0;i<spamFiles.size();i++)
            {
                byte[] buf = new byte[1024];
                java.io.File file = spamFiles.get(i);
                zip.putNextEntry(new ZipEntry("_spam_"+java.io.File.separator+file.getName()));
                FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                int moreText = 0;
                while((moreText=fis.read())!=-1)
                {
                    zip.write(buf, 0, moreText);
                }
                fis.close();
                zip.closeEntry();
            }

            zip.closeEntry();
            zip.close();
        }catch(FileNotFoundException e)
        {
            return false;
        }
        catch(IOException e)
        {
            return false;
        }
        }
        return true;

    }

    /**
     * This method save in the database a dataset object and creates a system task operation for extract metadata of the zip file of the dataset
     * @param dataset the dataset object to save
     * @param datasetFile the zip file uploaded with the dataset
     * @param username the username of the author of the dataset
     * @return a description message for the performed operation
     */
    public String uploadDataset(Dataset dataset, MultipartFile datasetFile, String username)
    {
        Locale locale = LocaleContextHolder.getLocale();
        
        String message = "";

        String name = dataset.getName().replace(" ", "").toLowerCase();
        String url = HOST_NAME+java.io.File.separator+"dataset"+java.io.File.separator+"public"+java.io.File.separator+"detailed"+java.io.File.separator+name;
        Date date = new Date();
        
        dataset.setName(name);
        dataset.setUrl(url);
        dataset.setUploadDate(date);
        dataset.setAuthor(username);
        dataset.setType("systemdataset");
        dataset.setAvailable(false);

        //Added default value because Spam and ham percentage cannot be null
        dataset.setSpamPercentage(-1);
        dataset.setHamPercentage(-1);

        Optional<Dataset> opt = datasetRepository.findById(dataset.getName());
        if(opt.isPresent())
        {
            message=messageSource.getMessage("add.dataset.fail.alreadyexists", Stream.of().toArray(String[]::new), locale);
        }
        else{
            try{
                this.store(datasetFile, dataset);
                }catch(IOException e)
                {
                    message=messageSource.getMessage("add.dataset.fail.ioexception", Stream.of(e.getMessage()).toArray(String[]::new), locale);
                    return message;
                }
        
                datasetRepository.save(dataset);
                taskService.addNewSystemTask(dataset);
               
                message=messageSource.getMessage("add.dataset.sucess", Stream.of((BASE_PATH+"/"+dataset.getName()+".zip")).toArray(String[]::new), locale);
        }

        return message;
    }

    /**
     * This method add all necesary fields of an user dataset
     * @param dataset the user dataset to complete
     * @param username the author of the user dataset
     * @return the dataset with the completed fields
     */
    public Dataset addUserDataset(Dataset dataset, String username)
    {
        String name = dataset.getName().replace(" ", "").toLowerCase();
        String url = HOST_NAME+java.io.File.separator+"dataset"+java.io.File.separator+"public"+java.io.File.separator+"detailed"+java.io.File.separator+name;

        Date date = new Date();
        dataset.setUploadDate(date);
        dataset.setAuthor(username);
        dataset.setType("userdataset");
        dataset.setAvailable(false);
        dataset.setUrl(url);
        
        return dataset;
    }

    /**
     * This method update the information of a dataset
     * @param dataset the dataset to be updated
     * @param username the username of the author of the dataset
     * @return a description message for the performed operation
     */
    public String updateDataset(Dataset dataset, String username)
    {
        String message = "";
        String datasetName = dataset.getName();

        Locale locale = LocaleContextHolder.getLocale();

        Optional<Dataset> optDataset = datasetRepository.findById(datasetName);

        if(optDataset.isPresent())
        {
            Dataset oldDataset = optDataset.get();

            String author = oldDataset.getAuthor();

            if(username.equals(author))
            {
                Dataset newDataset = updateDataset(oldDataset, dataset);
                datasetRepository.save(newDataset);
                message = messageSource.getMessage("edit.dataset.sucess", Stream.of().toArray(String[]::new), locale);
            }
            else
            {
                message = messageSource.getMessage("edit.dataset.fail.notowner", Stream.of().toArray(String[]::new), locale);
            }
        }
        else
        {
            message = messageSource.getMessage("edit.dataset.fail.notpresent", Stream.of().toArray(String[]::new), locale);
        }

        return message;
    }

    /**
     * Private method for complete the fields of the old dataset based on fields of the new
     * @param oldDataset the old dataset
     * @param newDataset the new dataset
     * @return the old dataset with the new fields added
     */
    private Dataset updateDataset(Dataset oldDataset, Dataset newDataset)
    {
        String access = newDataset.getAccess();
        String description = newDataset.getDescription();
        License license = newDataset.getLicense();

        oldDataset.setAccess(access);
        oldDataset.setDescription(description);

        if(license!=null)
        {
            oldDataset.setLicense(license);
        }
    
        return oldDataset;
    }

    /**
     * Store a dataset zip file in the dataset storage
     * @param datasetFile the dataset zip file
     * @param dataset the dataset object
     * @throws IOException if there is an exception in I/O operations
     */
    private void store(MultipartFile datasetFile, Dataset dataset) throws IOException
    {
        datasetFile.transferTo(new java.io.File(BASE_PATH+dataset.getName()+".zip"));
    }

    /**
     * Delete the specified dataset in the database and the respective zip on the storage
     * @param dataset the dataset to delete
     * @return true if correct deleted, false if not deleted
     */
    public boolean deleteDataset(Dataset dataset)
    {
        java.io.File datasetDirectory = new java.io.File(BASE_PATH+dataset.getName());
        java.io.File zipDirectory = new java.io.File(BASE_PATH+dataset.getName()+".zip");
        String datasetName = dataset.getName();
        
        if(datasetDirectory.exists())
        {
            try
            {
            FileUtils.deleteDirectory(datasetDirectory);
            }catch(IOException e)
            {
                return false;
            }
        }
        zipDirectory.delete();
        if(dataset.getType().equals("systemdataset"))
        {
            taskRepository.deleteUserTasks(datasetName);
        }

        datasetRepository.delete(dataset);
        return true;
    }

    /**
     * Return a list of the datasets filtered by languages, datatypes, licenses, dateFrom and dateTo
     * @param selectedLanguages list of languages
     * @param selectedDatatypes list of datatypes
     * @param selectedLicenses list of the licenses
     * @param date1 date from
     * @param date2 date to
     * @return  a list of the datasets filtered by languages, datatypes, licenses, dateFrom and dateTo
     */
    public ArrayList<Dataset> getFilteredDatasets(String[] selectedLanguages, String[] selectedDatatypes, String[] selectedLicenses, String date1, String date2)
    {

        ArrayList<String> selectedLanguagesList = new ArrayList<String>();
        ArrayList<String> selectedDatatypesList = new ArrayList<String>();
        ArrayList<String> selectedLicensesList = new ArrayList<String>();

        if(selectedLanguages==null)
        {
            Iterable<Language> languages = languageRepository.findAll();
            selectedLanguagesList = fillLanguagesList(languages);
        }
        else
        {
            for(int i = 0; i<selectedLanguages.length;i++)
            {
                selectedLanguagesList.add(selectedLanguages[i]);
            }

        }

        if(selectedLicenses==null)
        {
            Iterable<License> licenses = licenseRepository.findAll();
            selectedLicensesList = fillLicensesList(licenses);
        }
        else
        {
            for(int i = 0; i<selectedLicenses.length; i++)
            {
                selectedLicensesList.add(selectedLicenses[i]);
            }
        }

        if(selectedDatatypes==null)
        {
            Iterable<Datatype> datatypes = datatypeRepository.findAll();
            selectedDatatypesList = fillDatatypesList(datatypes);
        }
        else
        {
            for(int i = 0; i<selectedDatatypes.length; i++)
            {
                selectedDatatypesList.add(selectedDatatypes[i]);
            }
        }

        ArrayList<String> datasetNames = new ArrayList<String>();

        if(date1=="" || date2=="")
        {
            datasetNames = datasetRepository.getFilteredDatasets(selectedLanguagesList, selectedDatatypesList, selectedLicensesList);
        }
        else
        {
            datasetNames = datasetRepository.getFilteredDatasetsByDate(selectedLanguagesList, selectedDatatypesList, selectedLicensesList, date1, date2);
        }

         
        ArrayList<Dataset> datasets = new ArrayList<Dataset>();

        for(String datasetName : datasetNames)
        {
            Optional<Dataset> datasetOpt = datasetRepository.findById(datasetName);

            if(datasetOpt.isPresent())
            {
                datasets.add(datasetOpt.get());
            }
        }

        return datasets;
    }

    /**
     * Private method to fill an arraylist with the specified languages
     * @param languages the specified languages
     * @return An arraylist with the languages
     */
    private ArrayList<String> fillLanguagesList(Iterable<Language> languages)
    {
        ArrayList<String> languagesList = new ArrayList<String>();

        for(Language language:languages)
        {
            languagesList.add(language.getLanguage());
        }

        return languagesList;
    }

    /**
     * Private method to fill an arraylist with the specified licenses
     * @param licenses the specified licenses
     * @return An arraylist with the licenses
     */
    private ArrayList<String> fillLicensesList(Iterable<License> licenses)
    {
        ArrayList<String> licensesList = new ArrayList<String>();

        for(License license:licenses)
        {
            licensesList.add(license.getName());
        }

        return licensesList;
    }

    /**
     * Private method to fill an arraylist with the specified datatypes
     * @param datatypes the specified datatypes
     * @return An arraylist with the datatypes
     */
    private ArrayList<String> fillDatatypesList(Iterable<Datatype> datatypes)
    {
        ArrayList<String> datatypesList = new ArrayList<String>();

        for(Datatype datatype : datatypes)
        {
            datatypesList.add(datatype.getDatatype());
        }

        return datatypesList;
    }
    
    /**
     * Method to return String with all languages associated to the specified dataset
     * @param dataset the specified dataset
     * @return String with all languages associated to the specified dataset
     */
    public String getLanguagesString(Dataset dataset)
    {
        String languages = "";
        ArrayList<Language> languagesArray = new ArrayList<Language>(dataset.getLanguage());

        for(int i = 0; i<languagesArray.size(); i++)
        {
            languages+=languagesArray.get(i).getLanguage();
            languages+=" ";
        }

        return languages;
    }

    /**
     * Method to return String with all datatypes associated to the specified dataset
     * @param dataset the specified dataset
     * @return String with all datatypes associated to the specified dataset
     */
    public String getDatatypesString(Dataset dataset)
    {
        String datatypes = "";
        ArrayList<Datatype> datatypesArray = new ArrayList<Datatype>(dataset.getDatatypes());

        for(int i = 0; i<datatypesArray.size();i++)
        {
            datatypes+= datatypesArray.get(i).getDatatype();
            datatypes+=" ";
        }
        return datatypes;
    }
    
        
}