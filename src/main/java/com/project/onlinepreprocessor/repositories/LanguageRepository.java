package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.Language;

import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, String>
{
}