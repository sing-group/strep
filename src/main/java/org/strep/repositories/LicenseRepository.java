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
package org.strep.repositories;

import java.util.ArrayList;
import java.util.Collection;
import org.strep.domain.License;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for License objects
 * @author Ismael Vázquez
 */
public interface LicenseRepository extends CrudRepository<License, String>
{
    /**
     * Return the licenses that meet the requirements
     * @param searchInput the string to filter licenses name
     * @return An Iterable license object with filtered licenses
     */
    @Query(value = "SELECT * FROM license WHERE name LIKE %?1%", nativeQuery=true)
    public Iterable<License> findByName(String searchInput);
    
    /**
     * Return the license that meet the requirements
     * @param name the name of license
     * @return An License object
     */
    @Query(value = "SELECT * FROM license WHERE name =?1", nativeQuery=true)
    public License findByCompleteName(String name);
    
    
    /**
     * Select all licenses of a list of dataset
     * @param datasetNames The dataset names
     * @return The list of licenses of the datasets 
     */
    @Query(value="SELECT * FROM license, dataset WHERE license.name=dataset.id  AND dataset.name in (?1)", nativeQuery=true)
    public ArrayList<License> getDatasetLicenses(Collection<String> datasetNames);    
    
    
}
