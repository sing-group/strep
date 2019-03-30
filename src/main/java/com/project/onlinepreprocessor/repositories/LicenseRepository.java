package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.License;
import org.springframework.data.repository.CrudRepository;

public interface LicenseRepository extends CrudRepository<License, String>
{
    
}