package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.License;

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
}