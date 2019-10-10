package org.strep.services;

import org.strep.domain.License;

import org.springframework.stereotype.Service;

/**
 * Service class to abstract to the controller of license tasks not related to the presentation
 * @author Ismael Vázquez
 */
@Service
public class LicenseService
{
    /**
     * Method for format license description
     * @param license the license
     * @return String with the description of the license formated
     */
    public String formatLicenseDescription(License license)
    {
        String description = new String(license.getDescription());
        
        String toret = "";

        toret = description.replaceAll("\\n\\r?", "<\\br>");
        return toret;
    }
}