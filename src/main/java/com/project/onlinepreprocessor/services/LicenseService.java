package com.project.onlinepreprocessor.services;

import com.project.onlinepreprocessor.domain.License;

import org.springframework.stereotype.Service;

@Service
public class LicenseService
{
    public String formatLicenseDescription(License license)
    {
        String description = new String(license.getDescription());
        
        String toret = "";

        toret = description.replaceAll("\\n\\r?", "<\\br>");
        return toret;
    }
}