package com.project.onlinepreprocessor.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Datatype;
import com.project.onlinepreprocessor.domain.File;
import com.project.onlinepreprocessor.domain.Language;
import com.project.onlinepreprocessor.domain.License;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.DatatypeRepository;
import com.project.onlinepreprocessor.repositories.FileRepository;
import com.project.onlinepreprocessor.repositories.LanguageRepository;
import com.project.onlinepreprocessor.repositories.LicenseRepository;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class DatasetService
{

    @Value("${dataset.storage}")
    private String BASE_PATH;

    @Autowired 
    private DatasetRepository datasetRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired 
    private DatatypeRepository datatypeRepository;

    @Autowired
    private TaskService taskService;

    @Value("${host.name}")
    private String HOST_NAME;

    public DatasetService(DatasetRepository datasetRepository, FileRepository fileRepository)
    {
        this.datasetRepository = datasetRepository;
        this.fileRepository = fileRepository;
    }

    public boolean getDownloadFiles(String name)
    {

        java.io.File datasetDirectory = new java.io.File(BASE_PATH+name+"/");
        java.io.File datasetZip = new java.io.File(BASE_PATH+name+".zip");

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
        java.io.File directoryHam = new java.io.File(datasetDirectory, "_ham_/");
        java.io.File directorySpam = new java.io.File(datasetDirectory, "_spam_/");

        ArrayList<java.io.File> spamFiles = new ArrayList<java.io.File>();
        ArrayList<java.io.File> hamFiles = new ArrayList<java.io.File>();

        System.out.println(directorySpam.getAbsolutePath());
        System.out.println(directoryHam.getAbsolutePath());

        for(int i = 0; i<files.size();i++)
        {
            com.project.onlinepreprocessor.domain.File file = files.get(i);

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
        zip.putNextEntry(new ZipEntry("_ham_/"));
        
        for(int i = 0;i<hamFiles.size();i++)
        {
            System.out.println(hamFiles.get(i).getAbsolutePath());
            byte[] buf = new byte[1024];
            java.io.File file = hamFiles.get(i);
            zip.putNextEntry(new ZipEntry("_ham_/"+file.getName()));
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


        zip.putNextEntry(new ZipEntry("_spam_/"));

        for(int i = 0;i<spamFiles.size();i++)
        {
            byte[] buf = new byte[1024];
            java.io.File file = spamFiles.get(i);
            zip.putNextEntry(new ZipEntry("_spam_/"+file.getName()));
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
            System.out.println("File doesn't found");
            return false;
        }
        catch(IOException e)
        {
            System.out.println("IOException");
            return false;
        }
        }
        return true;

    }

    public String uploadDataset(Dataset dataset, MultipartFile datasetFile, String username)
    {
        String message = "";

        String name = dataset.getName().replace(" ", "").toLowerCase();
        String url = HOST_NAME+"/dataset/detailed/"+name;
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
            message="Already exists dataset with that name, error uploading dataset";
        }
        else{

            try{
                this.store(datasetFile, dataset);
                }catch(IOException e)
                {
                    message = "IOException error";
                    return message;
                }
        
                datasetRepository.save(dataset);
                taskService.addNewSystemTask(dataset);
                message = "Successfully uploaded: the dataset will be available after it has been processed";
        }

        return message;

    }

    private void store(MultipartFile datasetFile, Dataset dataset) throws IOException
    {
        datasetFile.transferTo(new java.io.File(BASE_PATH+dataset.getName()+".zip"));
    }

    public boolean deleteDataset(Dataset dataset)
    {
        java.io.File datasetDirectory = new java.io.File(BASE_PATH+dataset.getName());
        java.io.File zipDirectory = new java.io.File(BASE_PATH+dataset.getName()+".zip");
        
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

        datasetRepository.delete(dataset);
        return true;
    }

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

    private ArrayList<String> fillLanguagesList(Iterable<Language> languages)
    {
        ArrayList<String> languagesList = new ArrayList<String>();

        for(Language language:languages)
        {
            languagesList.add(language.getLanguage());
        }

        return languagesList;
    }

    private ArrayList<String> fillLicensesList(Iterable<License> licenses)
    {
        ArrayList<String> licensesList = new ArrayList<String>();

        for(License license:licenses)
        {
            licensesList.add(license.getName());
        }

        return licensesList;
    }

    private ArrayList<String> fillDatatypesList(Iterable<Datatype> datatypes)
    {
        ArrayList<String> datatypesList = new ArrayList<String>();

        for(Datatype datatype : datatypes)
        {
            datatypesList.add(datatype.getDatatype());
        }

        return datatypesList;
    }
    
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