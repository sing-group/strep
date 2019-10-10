package org.strep.repositories;

import org.strep.domain.Language;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for Language objects
 * @author Ismael Vázquez
 */
public interface LanguageRepository extends CrudRepository<Language, String>
{
}