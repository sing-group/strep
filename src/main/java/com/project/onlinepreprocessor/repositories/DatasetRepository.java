package com.project.onlinepreprocessor.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.onlinepreprocessor.domain.Dataset;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.math.BigInteger;
import java.util.ArrayList;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DatasetRepository extends CrudRepository<Dataset, String> {

    @Query(value="select * from dataset where access='public'",
    nativeQuery=true)
    public HashSet<Dataset> getPublicDatasets();

    @Query(value="select * from dataset where type='systemdataset'",nativeQuery=true)
    public ArrayList<Dataset> getSystemDatasets();

    @Query(value="select * from dataset where access='protected'", nativeQuery=true)
    public ArrayList<Dataset> getProtectedDatasets();

    @Query(value="select * from dataset where author=?1", nativeQuery=true)
    public ArrayList<Dataset> getUserDatasets(String username);

    @Query(value="select file_id from dataset_files where dataset_name=?1", nativeQuery=true)
    public ArrayList<BigInteger> getFileIds(String name);

}