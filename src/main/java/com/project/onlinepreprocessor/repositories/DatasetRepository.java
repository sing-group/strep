package com.project.onlinepreprocessor.repositories;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.project.onlinepreprocessor.domain.Dataset;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for Dataset objects
 * @author Ismael Vázquez
 */
public interface DatasetRepository extends CrudRepository<Dataset, String>{

    /**
     * Return available datasets with public visibility
     * @return Set of available datasets with public visibility
     */
    @Query(value="select * from dataset where access='public' and available=true",
    nativeQuery=true)
    public HashSet<Dataset> getPublicDatasets();

    /**
     * Return available system datasets
     * @return List of available system datasets
     */
    @Query(value="select * from dataset where type='systemdataset' and available=true",nativeQuery=true)
    public ArrayList<Dataset> getSystemDatasets();

    /**
     * Return available protected datasets
     * @return List of available protected datasets
     */
    @Query(value="select * from dataset where access='protected' and available=true", nativeQuery=true)
    public ArrayList<Dataset> getProtectedDatasets();

    /**
     * Return available datasets for the specified author
     * @param username the username of the author of the dataset
     * @return A list of the available datasets for this author
     */
    @Query(value="select * from dataset where author=?1 and available=true", nativeQuery=true)
    public ArrayList<Dataset> getUserDatasets(String username);

    /**
     * Return available datasets filtered by author and type
     * @param username the username of the author of the dataset
     * @param type the type of the dataset: spam or ham
     * @return A list of the available datasets for this filters
     */
    @Query(value="select * from dataset where author=?1 and type=?2 and available=true", nativeQuery=true)
    public ArrayList<Dataset> getUserDatasets(String username, String type);

    /**
     * Return file ids that are associated with the specified dataset
     * @param name the name of the dataset
     * @return A list of ids for the files of the specified dataset
     */
    @Query(value="select file_id from dataset_files where dataset_name=?1", nativeQuery=true)
    public ArrayList<BigInteger> getFileIds(String name);

    /**
     * Return filtered system datasets that meet the marked requirements of languages, datatypes and license
     * @param languages the valid languages
     * @param datatypes the valid datatypes
     * @param license the valid licenses
     * @return a list of the system datasets that meet the requirements
     */
    @Query(value="select distinct(d.name) from dataset d inner join dataset_languages lang on d.name=lang.dataset_name inner join dataset_datatypes data on d.name=data.dataset_name where d.type='systemdataset' and lang.language in ?1 and data.data_type in ?2 and d.id in ?3", nativeQuery=true)
    public ArrayList<String> getFilteredDatasets(Collection<String> languages,Collection<String> datatypes, Collection<String> license);

    /**
     * Return filtered system datasets that meet the marked requirements of languages, datatypes, license, initial messages date and final messages date
     * @param languages the valid languages
     * @param datatypes the valid datatypes
     * @param license the valid license
     * @param date1 the valid initial date
     * @param date2 the valid final date
     * @return a list of system datasets that meet the requirements
     */
    @Query(value="select distinct(d.name) from dataset d inner join dataset_languages lang on d.name=lang.dataset_name inner join dataset_datatypes data on d.name=data.dataset_name where d.type='systemdataset' and lang.language in ?1 and data.data_type in ?2 and d.id in ?3 and (d.first_file_date <= ?5 and d.last_file_date >= ?4)", nativeQuery=true)
    public ArrayList<String> getFilteredDatasetsByDate(Collection<String> languages, Collection<String> datatypes, Collection<String> license, String date1, String date2);

    /**
     * Return the datatypes associated to the list of specified datasets
     * @param datasetNames the names of the datasets
     * @return A list of the datatypes associated to those datasets
     */
    @Query(value = "select distinct(data_type) from dataset_datatypes where dataset_name in (?1)", nativeQuery = true)
    public ArrayList<String> getDatasetsDatatypes(Collection<String> datasetNames);

    /**
     * Return the number of spam files associated to specified datasets
     * @param datasetNames the name the datasets
     * @return the number of spam files associated to those datasets
     */
    @Query(value = "select count(distinct(df.file_id)) from dataset d inner join dataset_files df on d.name=df.dataset_name  inner join file f on df.file_id=f.id where d.name in (?1) and f.type='spam'", nativeQuery=true)
    public int countSpamFiles(Collection<String> datasetNames);

    /**
     * Return the count of files by extension and type associated to the specified datasets 
     * @param datasetNames the name of the datasets
     * @return the count of the files by extension and type associated to those datasets
     */
    @Query(value = "select count(f.id) from dataset d inner join dataset_files df on d.name=df.dataset_name inner join file f on df.file_id=f.id where d.name in(?1) group by f.extension, f.type", nativeQuery=true)
    public ArrayList<Object> getFilesByExtensionAndType(Collection<String> datasetNames);
}