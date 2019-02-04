package com.project.onlinepreprocessor.services;


import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.File;

import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.repositories.FileRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.Path;
import java.lang.Iterable;
import java.util.Optional;

@Service
public class DatasetService
{
    @Autowired 
    private DatasetRepository datasetRepository;

    @Autowired
    private FileRepository fileRepository;

    public DatasetService(DatasetRepository datasetRepository, FileRepository fileRepository)
    {
        this.datasetRepository = datasetRepository;
        this.fileRepository = fileRepository;
    }

    //TODO: Implementation for this method
    //TODO: Make the method return Iterable<Path>
    public void getDownloadPaths()
    {

    }


}