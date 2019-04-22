package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.Datatype;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DatatypeRepository extends CrudRepository<Datatype, String>
{
}