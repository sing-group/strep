/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
