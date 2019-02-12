package com.project.onlinepreprocessor.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.forms.LoginForm;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.services.DatasetService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/dataset")
public class DatasetController
{
    @Value("${dataset.storage}")
    private String BASE_PATH;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired 
    private DatasetService datasetService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/home")
    public String listHomeDatasets(Authentication authentication, Model model)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("Authority", authority);
        model.addAttribute("username", username);

        System.out.println("Username: "+ username);
        System.out.println("Authority: "+ authority);

        if(authority.equals("canView"))
        {
            ArrayList<Dataset> datasets = datasetRepository.getSystemDatasets();
            model.addAttribute("datasets", datasets);
            return "system_datasets";
        }
        else
        {
            ArrayList<Dataset> datasets = datasetRepository.getUserDatasets(username);
            model.addAttribute("datasets", datasets);
            return "user_datasets";
        }
    }
}