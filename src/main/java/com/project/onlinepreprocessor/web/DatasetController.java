package com.project.onlinepreprocessor.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.project.onlinepreprocessor.repositories.DatasetRepository;
import com.project.onlinepreprocessor.domain.Dataset;
import org.springframework.ui.Model;
import com.project.onlinepreprocessor.forms.LoginForm;

import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping(path="/dataset")
public class DatasetController
{
    @Autowired
    private DatasetRepository datasetRepository;

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
            return "/errorTemplates/error-404";
        }

    }

    //TODO: Make the implementation for this method
    //TODO: make the method returns a String
    @GetMapping("/public/download")
    public void downloadPublicDataset(@RequestParam("id")String name, Model model, LoginForm loginForm)
    {

    }
}