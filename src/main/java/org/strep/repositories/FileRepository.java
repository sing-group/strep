package org.strep.repositories;

import org.strep.domain.File;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for File objects
 * @author Ismael Vázquez
 * @author José Ramón Méndez
 * 
 */
public interface FileRepository extends CrudRepository<File, Long>
{
    /**
     * Return the number of spam/ham files that matches some conditions
     *
     * @param datasetNames the name the datasets accepted
     * @param languages The 2-code languages accepted
     * @param extensions The extensions of the accepted files
     * @param startDate The start date interval for accepted files
     * @param endDate The end date interval for accepted files
     * @param type spam/ham
     * @return the number of spam/ham files associated to those datasets
     */
    @Query(value = 
      "SELECT count(file.id) as count "
     +"FROM file, dataset_files "
     +"WHERE "
     +  "file.id=dataset_files.file_id " //Join conditions
     +  "AND dataset_files.dataset_name IN (?1) AND file.language IN (?2) "+ //filtering conditions
        "AND file.extension IN (?3) AND file.date>=?4 AND file.date<=?5 AND " 
     +  "file.type = ?6", //see only ham or spam files
    nativeQuery = true)
    public int countSystemDatasetFilesByType(Collection<String> datasetNames, Collection<String> languages, Collection<String> extensions, 
                                             Date startDate, Date endDate, String type);
    
    /**
    * Find the date of the latest file
    * @return the date of the latest file
    */
    @Query(value = "SELECT max(date) FROM file", nativeQuery = true)
    public Date getLatestDate();

    /**
    * Find the date of the earliest file
    * @return the date of the earliest file
    */
    @Query(value = "SELECT min(date) FROM file", nativeQuery = true)
    public Date getEarliestDate();
}
