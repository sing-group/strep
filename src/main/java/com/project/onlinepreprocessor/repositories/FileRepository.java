package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.File;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for FileDatatypeType objects
 * @author Ismael Vázquez
 */
public interface FileRepository extends CrudRepository<File, Long>
{

}
