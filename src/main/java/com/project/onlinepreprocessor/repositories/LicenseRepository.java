package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.License;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LicenseRepository extends CrudRepository<License, String>
{
    @Query(value = "SELECT * FROM license WHERE name LIKE %?1%", nativeQuery=true)
    public Iterable<License> findByName(String searchInput);
}