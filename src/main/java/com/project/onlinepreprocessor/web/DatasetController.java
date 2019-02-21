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

    @GetMapping("/list")
    public String listDatasets(@RequestParam("type") String type,Authentication authentication,Model model)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        ArrayList<Dataset> datasets = new ArrayList<Dataset>();

        switch(type)
        {
            case "system":
                datasets = datasetRepository.getSystemDatasets();
                break;
            case "protected":
                datasets = datasetRepository.getProtectedDatasets();
                break;
            case "user":
                if(authority=="canView")
                {
                    datasets = datasetRepository.getSystemDatasets();
                    type = "system";
                }
                else
                {
                    datasets = datasetRepository.getUserDatasets(username);
                }
                break; 
        }

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("type", type);
        model.addAttribute("datasets", datasets);

        return "list_datasets";
    }

    @GetMapping("/detailed")
    public String detailDataset(Authentication authentication, @RequestParam("id") String name, @RequestParam("type") String type, Model model )
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<Dataset> opt = datasetRepository.findById(name);

        if(opt.isPresent())
        {
            Dataset dataset = opt.get();

            if(dataset.getAccess().equals("private"))
            {
                if(dataset.getAuthor().equals(username))
                {
                    model.addAttribute("dataset", dataset);
                    //TODO: Change this, need one detailed dataset view for private datasets
                    return "detailedDataset";
                }
                else
                {
                    return "redirect:/error";
                }
            }
            else
            {
                model.addAttribute("dataset", dataset);
                return "detailedDataset";
            }
        }
        else
        {
            return "redirect:/error";
        }
              
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadDataset(Authentication authentication, @RequestParam("id")String name, Model model, LoginForm loginForm) throws FileNotFoundException
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Optional<Dataset> opt = datasetRepository.findById(name);

        if(opt.isPresent())
        {
        
            Dataset dataset = opt.get();
            String owner = dataset.getAuthor();
            
            if(dataset.getAccess().equals("public") || dataset.getAccess().equals("protected") || owner.equals(username))
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
            else
            {
                return null;
            }

        }
        else
        {
            return null;
        }
    }

    @GetMapping("/delete")
    public String deleteDataset(Authentication authentication, Model model, @RequestParam("id") String name)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        Optional<Dataset> opt = datasetRepository.findById(name);
  
        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("type", "user");

        if(opt.isPresent() && opt.get().getAuthor().equals(username))
        {
            Dataset dataset = opt.get();
            if(datasetService.deleteDataset(dataset))
            {
                model.addAttribute("message", "Successfully deleted");
            }
            else
            {
                model.addAttribute("message", "Error deleting dataset"); 
            } 
        }
        else
        {
            model.addAttribute("message", "Error deleting dataset");
        }

        ArrayList<Dataset> datasets = datasetRepository.getUserDatasets(username);
        model.addAttribute("datasets", datasets);
        return "list_datasets";
    }

    
    @GetMapping("/access")
    public String changeAccess(Authentication authentication, Model model, @RequestParam("id") String name, @RequestParam("access")String access)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        String type="user";
        ArrayList<Dataset> datasets = datasetRepository.getUserDatasets(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);
        model.addAttribute("datasets", datasets);
        model.addAttribute("type", "user");

        Optional<Dataset> opt = datasetRepository.findById(name);

        if(opt.isPresent())
        {
            Dataset dataset = opt.get();

            if(access.equals("private") || access.equals("public") || access.equals("protected"))
            {
                dataset.setAccess(access);
                datasetRepository.save(dataset);
                model.addAttribute("message", "Dataset successfully changed to "+ access);
            }
            else
            {
                model.addAttribute("message", access+" is not a valid access type");
            }
        }
        else
        {
            model.addAttribute("message", "Cannot found specified dataset to change the access");
        }

        return "list_datasets";

    }
    

    @GetMapping("/home")
    public String listHomeDatasets(Authentication authentication, Model model)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        ArrayList<Dataset> systemDatasets = datasetRepository.getSystemDatasets();
        model.addAttribute("systemDatasets", systemDatasets);

        ArrayList<Dataset> protectedDatasets = datasetRepository.getProtectedDatasets();
        model.addAttribute("protectedDatasets", protectedDatasets);

        ArrayList<Dataset> userDatasets = datasetRepository.getUserDatasets(username);
        model.addAttribute("userDatasets", userDatasets);

        return "home";
    }
}