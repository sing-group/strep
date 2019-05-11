package com.project.onlinepreprocessor.repositories;

import com.project.onlinepreprocessor.domain.Task;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long>
{

}