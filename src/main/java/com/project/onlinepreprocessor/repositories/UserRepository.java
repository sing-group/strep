package com.project.onlinepreprocessor.repositories;

import org.springframework.data.repository.CrudRepository;
import com.project.onlinepreprocessor.domain.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, String> {

    @Query(
    value = "SELECT * from user u where hash=?1", 
    nativeQuery = true)
    Optional<User> findUserByHash(Integer hash);
}