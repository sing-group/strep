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
package org.strep.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * JPA Bean for the Dataset objects managed by application
 *
 * @author Ismael Vázquez
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Task implements Serializable {

    /**
     * Waiting state (BBDD representation)
     */
    public static final String STATE_WAITING = "waiting";

    /**
     * Executing state (BBDD representation)
     */
    public static final String STATE_EXECUTING = "executing";

    /**
     * Sucess state (BBDD representation)
     */
    public static final String STATE_SUCESS = "success";

    /**
     * Sucess state (BBDD representation)
     */
    public static final String STATE_FAILED = "failed";

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The id of the task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // No caso de TaskCreateSDataset o que se fai é que o dataset que está en task actua como o dataset que hai que encher cos arquivos que se pasan no ficheiro zip. Básicamente sería un parámetro de E/S (entrada/salida). Este Dataset de salida instanciase no spring e despois échese no strep_service. 
    /**
     * The dataset associated to this task.  Depending on the type of task, the dataset will behave on different way.
     * Preprocessing task (TaskCreateUPreprocessing) : This is an input dataset
     * System task (TaskCreateSDataset) : This is an Input/output dataset. This dataset is filled with zip file content.
     * Tune task (TaskCreateUDataset): Output dataset which contains the combination/filtering of instances made through executing the task
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "dataset_name")
    private Dataset dataset;

    /**
     * The state of the task
     */
    @Pattern(regexp = "^(waiting|executing|success|failed)$", message = "The access must be waiting, executing, sucess or failed")
    @Column(length = 10, columnDefinition = "VARCHAR(10)")
    private String state;

    /**
     * Indicates if the task is active or not
     */
    private boolean active;

    /**
     * The message of the task when failed
     *
     */
    @Column(nullable = true)
    @Lob
    private String message;

    /**
     * Creates an instance of the task
     *
     * @param dataset the dataset associated to the task
     * @param state the state of the task
     * @param message the message of the task when failed
     */
    public Task(Dataset dataset, String state, String message) {
        this.dataset = dataset;
        this.state = state;
        this.message = message;
        this.active=true;
    }

    /**
     * The default constructor of the task
     */
    public Task() {
        this.active=true;
    }

    /**
     * Return the dataset associated to the task
     *
     * @return the dataset associated to the task
     */
    public Dataset getDataset() {
        return this.dataset;
    }

    /**
     * Stablish the dataset associated to the task
     *
     * @param dataset the dataset associated to the task
     */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    /**
     * Return the id of the task
     *
     * @return the id of the task
     */
    public long getId() {
        return this.id;
    }

    /**
     * Stablish the id of the task
     *
     * @param id the id of the task
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return the state of the task
     *
     * @return the state of the task
     */
    public String getState() {
        return this.state;
    }

    /**
     * Stablish the state of the task
     *
     * @param state the state of the task
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Return true if the task is active, false otherwise
     *
     * @return True if the task is active, false otherwise
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * Stablish it a task is active or not
     *
     * @param active True if task is active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Return the message of the task when failed
     *
     * @return the message of the task when failed
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Stablish the message of the task when failed
     *
     * @param message the message of the task when failed
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
