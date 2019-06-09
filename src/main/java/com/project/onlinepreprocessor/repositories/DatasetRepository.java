package com.project.onlinepreprocessor.repositories;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.project.onlinepreprocessor.domain.Dataset;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;




// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DatasetRepository extends CrudRepository<Dataset, String>{

    @Query(value="select * from dataset where access='public' and available=true",
    nativeQuery=true)
    public HashSet<Dataset> getPublicDatasets();

    @Query(value="select * from dataset where type='systemdataset' and available=true",nativeQuery=true)
    public ArrayList<Dataset> getSystemDatasets();

    @Query(value="select * from dataset where access='protected' and available=true", nativeQuery=true)
    public ArrayList<Dataset> getProtectedDatasets();

    @Query(value="select * from dataset where author=?1 and available=true", nativeQuery=true)
    public ArrayList<Dataset> getUserDatasets(String username);

    @Query(value="select * from dataset where author=?1 and type=?2 and available=true", nativeQuery=true)
    public ArrayList<Dataset> getUserDatasets(String username, String type);

    @Query(value="select file_id from dataset_files where dataset_name=?1", nativeQuery=true)
    public ArrayList<BigInteger> getFileIds(String name);

    
    @Query(value="select distinct(d.name) from dataset d inner join dataset_languages lang on d.name=lang.dataset_name inner join dataset_datatypes data on d.name=data.dataset_name where d.type='systemdataset' and lang.language in ?1 and data.data_type in ?2 and d.id in ?3", nativeQuery=true)
    public ArrayList<String> getFilteredDatasets(Collection<String> languages,Collection<String> datatypes, Collection<String> license);

    @Query(value="select distinct(d.name) from dataset d inner join dataset_languages lang on d.name=lang.dataset_name inner join dataset_datatypes data on d.name=data.dataset_name where d.type='systemdataset' and lang.language in ?1 and data.data_type in ?2 and d.id in ?3 and (d.first_file_date <= ?5 and d.last_file_date >= ?4)", nativeQuery=true)
    public ArrayList<String> getFilteredDatasetsByDate(Collection<String> languages, Collection<String> datatypes, Collection<String> license, String date1, String date2);

    @Query(value = "select distinct(data_type) from dataset_datatypes where dataset_name in (?1)", nativeQuery = true)
    public ArrayList<String> getDatasetsDatatypes(Collection<String> datasetNames);

    @Query(value = "select count(distinct(df.file_id)) from dataset d inner join dataset_files df on d.name=df.dataset_name  inner join file f on df.file_id=f.id where d.name in (?1) and f.type='spam'", nativeQuery=true)
    public int countSpamFiles(Collection<String> datasetNames);

    @Query(value = "select count(f.id) from dataset d inner join dataset_files df on d.name=df.dataset_name inner join file f on df.file_id=f.id where d.name in(?1) group by f.extension, f.type", nativeQuery=true)
    public ArrayList<Object> getFilesByExtensionAndType(Collection<String> datasetNames);
}