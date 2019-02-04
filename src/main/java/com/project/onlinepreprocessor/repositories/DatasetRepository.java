package com.project.onlinepreprocessor.repositories;

import org.springframework.data.repository.CrudRepository;
import com.project.onlinepreprocessor.domain.Dataset;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DatasetRepository extends CrudRepository<Dataset, String> {

    @Query(value="select * from dataset where access='public'",
    nativeQuery=true)
    public HashSet<Dataset> getPublicDatasets();

}