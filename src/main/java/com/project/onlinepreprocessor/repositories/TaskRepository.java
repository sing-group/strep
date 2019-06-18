package com.project.onlinepreprocessor.repositories;

import java.util.ArrayList;
import java.util.Optional;

import com.project.onlinepreprocessor.domain.Dataset;
import com.project.onlinepreprocessor.domain.Task;
import com.project.onlinepreprocessor.domain.TaskCreateUPreprocessing;
import com.project.onlinepreprocessor.domain.TaskCreateUdataset;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends CrudRepository<Task, Long>
{
    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1")
    public ArrayList<Task> getSystemTasks(String username);

    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2%")
    public ArrayList<Task> getSystemTasksFiltered(String username, String inputSearch);

    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2% and t.state=?3")
    public ArrayList<Task> getSystemTasksFiltered(String username, String inputSearch, String state);

    @Query("select t from TaskCreateSdataset t where t.dataset.author=?1 and t.state=?2")
    public ArrayList<Task> getSystemTasks(String username, String state);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1")
    public ArrayList<Task> getUserTasks(String username);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1 and t.state=?2")
    public ArrayList<Task> getUserTasks(String username, String state);

    @Query("select t from TaskCreateUPreprocessing t where t.id=?1")
    public TaskCreateUPreprocessing findTaskCreateUPreprocessingById(Long id);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2%")
    public ArrayList<Task> getUserTasksFiltered(String username, String inputSearch);

    @Query("select t from TaskCreateUdataset t where t.dataset.author=?1 and t.dataset.name LIKE %?2% and t.state=?3")
    public ArrayList<Task> getUserTasksFiltered(String username, String inputSearch, String state);

    @Query("select t from TaskCreateUdataset t where t.id=?1")
    public Optional<TaskCreateUdataset> findTaskCreateUdatasetById(long id);

    @Transactional
    @Modifying
    @Query(value="delete from task_create_udataset_datasets where dataset=?1", nativeQuery=true)
    public void deleteUserTasks(String datasetName);

    @Query(value="select t from TaskCreateUPreprocessing t where t.preprocessDataset=?1 and t.state=?2")
    public ArrayList<TaskCreateUPreprocessing> getPreprocessingTasks(Dataset dataset, String state);
}