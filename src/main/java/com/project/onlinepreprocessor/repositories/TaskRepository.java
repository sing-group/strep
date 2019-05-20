package com.project.onlinepreprocessor.repositories;

import java.util.ArrayList;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.Task;
import com.project.onlinepreprocessor.domain.TaskCreateUdataset;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long>
{
    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1")
    public ArrayList<Task> getSystemTasks(String username);

    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2%")
    public ArrayList<Task> getSystemTasksFiltered(String username, String inputSearch);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1")
    public ArrayList<Task> getUserTasks(String username);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2%")
    public ArrayList<Task> getUserTasksFiltered(String username, String inputSearch);

    @Query("select t from TaskCreateUdataset t where t.id=?1")
    public Optional<TaskCreateUdataset> findTaskCreateUdatasetById(long id);
}