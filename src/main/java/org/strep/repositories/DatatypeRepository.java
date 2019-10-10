package org.strep.repositories;

import org.strep.domain.Datatype;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations for Datatype objects
 * @author Ismael Vázquez
 */
public interface DatatypeRepository extends CrudRepository<Datatype, String>
{
}