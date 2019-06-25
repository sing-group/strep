package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.Language;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for Language objects
 * @author Ismael Vázquez
 */
public interface LanguageRepository extends CrudRepository<Language, String>
{
}