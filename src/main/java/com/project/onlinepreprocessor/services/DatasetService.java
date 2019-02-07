package com.project.onlinepreprocessor.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.project.onlinepreprocessor.domain.File;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class DatasetService
{

    @Value("${dataset.storage}")
    private String BASE_PATH;

    @Autowired 
    private DatasetRepository datasetRepository;

    @Autowired
    private FileRepository fileRepository;

    public DatasetService(DatasetRepository datasetRepository, FileRepository fileRepository)
    {
        this.datasetRepository = datasetRepository;
        this.fileRepository = fileRepository;
    }

    public boolean getDownloadFiles(String name)
    {

        java.io.File datasetDirectory = new java.io.File(BASE_PATH+name+"/");

        
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


}