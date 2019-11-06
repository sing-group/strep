package org.strep.repositories;

import org.strep.domain.FileDatatypeType;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for FileDatatypeType objects
 * @author Ismael Vázquez
 */
public interface FileDatatypeTypeRepository extends CrudRepository<FileDatatypeType, String>
{

}