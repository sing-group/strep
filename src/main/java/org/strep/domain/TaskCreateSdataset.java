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

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TaskCreateSdataset extends Task
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an instance of TaskCreateSdataset
     * 
     * @param dataset the dataset associated to the task
     * @param state   the state of the task
     * @param message the message of the task when failed
     */
    public TaskCreateSdataset(Dataset dataset, String state, String message)
    {
        super(dataset, state, message);
    }

    /**
     * The default constructor
     */
    public TaskCreateSdataset()
    {
        super();
    }
}
