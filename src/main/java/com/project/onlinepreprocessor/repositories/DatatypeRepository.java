package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.Datatype;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations for Datatype objects
 * @author Ismael Vázquez
 */
public interface DatatypeRepository extends CrudRepository<Datatype, String>
{
}