package com.project.onlinepreprocessor.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.forms.LoginForm;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.services.DatasetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/dataset")
public class DatasetController
{
    private final String BASE_PATH = "/home/ismael/Desarrollo/datasets/";

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired 
    private DatasetService datasetService;

    @GetMapping("/public")
    public String listPublicDatasets(LoginForm loginForm, Model model)
    {
        HashSet<Dataset> datasets = datasetRepository.getPublicDatasets();
        model.addAttribute("datasets", datasets);
        return "public_datasets";
    }

    @GetMapping("/public/detailed")
    public String detailPublicDataset(@RequestParam("id")String name, Model model,LoginForm loginForm)
    {
        Optional<Dataset> opt = datasetRepository.findById(name);

        if(opt.isPresent())
        {
            Dataset dataset = opt.get();
            model.addAttribute("dataset", dataset);
            return "detailedDataset";
        }
        else
        {
            return "redirect:/error";
        }

    }

    @GetMapping("/public/download")
    public ResponseEntity<InputStreamResource> downloadPublicDataset(@RequestParam("id")String name, Model model, LoginForm loginForm) throws FileNotFoundException
    {
        if(datasetService.getDownloadFiles(name))
        {
        FileInputStream fis = new FileInputStream(new File(BASE_PATH+name+".zip"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type","application/zip");
        httpHeaders.set("content-disposition","attachment;"+"filename="+name+".zip");
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(new InputStreamResource(fis),httpHeaders,HttpStatus.CREATED);
        return response;
        }
        else
        {   
            return null;
        }
    }
}