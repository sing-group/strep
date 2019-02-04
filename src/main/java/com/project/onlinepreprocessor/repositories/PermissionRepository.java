package com.project.onlinepreprocessor.repositories;

import org.springframework.data.repository.CrudRepository;
import com.project.onlinepreprocessor.domain.Permission;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PermissionRepository extends CrudRepository<Permission, Long> {

}