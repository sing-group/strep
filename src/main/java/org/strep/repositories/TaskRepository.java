/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.strep.domain.Dataset;
import org.strep.domain.Task;
import org.strep.domain.TaskCreateUPreprocessing;
import org.strep.domain.TaskCreateUdataset;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations
 * with added queries for Task objects
 *
 * @author Ismael Vázquez
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    /**
     * Return the system task for the specified user
     *
     * @param username the username of the user
     * @return A list of the system tasks of the user
     */
    @Query("SELECT t FROM TaskCreateSdataset t WHERE t.dataset.author=?1 AND t.active='1'")
    public ArrayList<Task> getSystemTasks(String username);

    /**
     * Return the system task for the specified user
     *
     * @param username the username of the user
     * @param inputSearch the input for filter de tasks
     * @return A list of the system tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateSdataset t WHERE t.dataset.author=?1 AND t.dataset.name LIKE %?2% AND t.active='1'")
    public ArrayList<Task> getSystemTasksFiltered(String username, String inputSearch);

    /**
     *
     * Return the system task for the specified user
     *
     * @param username the username of the user
     * @param inputSearch the input for filter de tasks
     * @param state the state for filter de tasks
     * @return A list of the system tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateSdataset t WHERE t.dataset.author=?1 AND t.dataset.name LIKE %?2% AND t.state=?3 AND t.active='1'")
    public ArrayList<Task> getSystemTasksFiltered(String username, String inputSearch, String state);

    /**
     * Return the system task for the specified user
     *
     * @param username
     * @param state the state for filter de tasks
     * @return the system task for the specified user filtered
     */
    @Query("SELECT t FROM TaskCreateSdataset t WHERE t.dataset.author=?1 AND t.state=?2 AND t.active='1'")
    public ArrayList<Task> getSystemTasks(String username, String state);

    /**
     * Return the user tasks for the specified user
     *
     * @param username the username of the user
     * @return A list of the user tasks of the user
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.active='1'")
    public ArrayList<Task> getUserTasks(String username);

    /**
     * Return the list of active user tasks for the specified user (please note
     * that only active tasks are retrieved)
     *
     * @param username the username of the user
     * @param state the state for filter de tasks
     * @return A list of the user tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.state=?2 AND t.active='1'")
    public ArrayList<Task> getActiveUserTasks(String username, String state);

    /**
     * Return the user tasks for the specified user (please note that active and
     * not active tasks are retrieved)
     *
     * @param username the username of the user
     * @param state the state for filter de tasks
     * @return A list of the user tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.state=?2")
    public ArrayList<Task> getUserTasks(String username, String state);

    /**
     * Return the specified preprocessing task
     *
     * @param id the id of the task
     * @return the preprocessing task with that id
     */
    @Query("SELECT t FROM TaskCreateUPreprocessing t WHERE t.id=?1 AND t.active='1'")
    public TaskCreateUPreprocessing findTaskCreateUPreprocessingById(Long id);

    /**
     * Return the list of preprocessing tasks that do not belong to the
     * mentioned dataset, do not have private access (or that were created by
     * username) and the mentioned state.
     *
     * @param datasetName Name of the dataset
     * @param username Username
     * @param state The state of the tasks to get
     * @return The list of preprocessing tasks list of preprocessing tasks that
     * do not belong to the mentioned dataset, do not have private access (or
     * that were created by username) and the mentioned state.
     */
    @Query(value = "SELECT t "
            + "FROM TaskCreateUPreprocessing t, Dataset d "
            + "WHERE d.name=t.dataset.name "
            + "AND t.dataset.name<>?1 AND (d.access IN ('public','protected') OR d.author=?2) "
            + "AND  t.state=?3")
    public ArrayList<TaskCreateUPreprocessing> findAllTaskCreateUPreprocessing(String datasetName, String username, String state);

    /**
     * Return the user task for the specified user
     *
     * @param username the username of the user
     * @param inputSearch the input for filter the tasks
     * @return A list of the user tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.dataset.name LIKE %?2% AND t.active='1'")
    public ArrayList<Task> getActiveUserTasksFiltered(String username, String inputSearch);

    /**
     * Return the user task for the specified user
     *
     * @param username the username of the user
     * @param inputSearch the input for filter de tasks
     * @param state the state for filter the tasks
     * @return A list of the user tasks of the user filtered
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.dataset.name LIKE %?2% AND t.state=?3 AND t.active='1'")
    public ArrayList<Task> getActiveUserTasksFiltered(String username, String inputSearch, String state);

    /**
     * Return the User task with that id
     *
     * @param id the id of the task
     * @return the user task with that id
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.id=?1 AND t.active='1'")
    public Optional<TaskCreateUdataset> findTaskCreateUdatasetById(long id);

    /**
     * Delete the user tasks of the specified dataset
     *
     * @param datasetName the name of the dataset
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task_create_udataset_datasets WHERE dataset=?1", nativeQuery = true)
    public void deleteUserTasks(String datasetName);

    /**
     * Return a list with active preprocessing tasks of the dataset filtered by
     * state (only active tasks are listed)
     *
     * @param dataset the specified dataset
     * @param state the specified state
     * @return a list with the preprocessing tasks of the dataset filtered by
     * state
     */
    @Query(value = "SELECT t FROM TaskCreateUPreprocessing t WHERE t.dataset.name=?1 AND t.state=?2 AND t.active='1'")
    public ArrayList<TaskCreateUPreprocessing> getActivePreprocessingTasks(Dataset dataset, String state);

    /**
     * Return a list with all preprocessing tasks of the dataset filtered by
     * state (active and non active tasks are listed)
     *
     * @param dataset the specified dataset
     * @param state the specified state
     * @return a list with the preprocessing tasks of the dataset filtered by
     * state
     */
    @Query(value = "SELECT t FROM TaskCreateUPreprocessing t WHERE t.dataset.name=?1 AND t.state=?2")
    public ArrayList<TaskCreateUPreprocessing> getPreprocessingTasks(Dataset dataset, String state);

    /**
     * Return a list with the preprocessing tasks of the dataset filtered by
     * state
     *
     * @param state the specified state
     * @return a list with the preprocessing tasks of the dataset filtered by
     * state
     */
    @Query(value = "SELECT t FROM TaskCreateUPreprocessing t WHERE t.state=?1 ORDER BY t.dataset.name")
    public ArrayList<TaskCreateUPreprocessing> getPreprocessingTasks(String state);

    /**
     * Return the active tasks for an user and dataset
     * @param username the name of the user
     * @param name The dataset
     * @return List of active tasks for a dataset
     */
    @Query("SELECT t FROM TaskCreateUdataset t WHERE t.dataset.author=?1 AND t.dataset.name =?2 AND t.active='1'")
    public ArrayList<Task> getActiveUserTasksFilteredByDataset(String username, String name);

}
