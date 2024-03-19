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

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * JPA Bean for the DatasetType objects managed by application
 * @author Ismael Vázquez
 */

@Entity
@Table(name="dataset_types")
public class DatasetType implements Serializable
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The id of the dataset type
     */
    @Id
    private int id;

    /**
     * The description of the dataset type
     */
    @Lob
    private String description;
    
    /**
     * The name of the dataset type
     */
    @NotNull(message = "The name of the Dataset type cannot be null")
    private String name;

    /**
     * The default constructor
     */
    protected DatasetType()
    {

    }

    /**
     * Creates an instance of the dataset type
     * @param description the description of the dataset type 
     * @param name the name of the dataset type
     */
    public DatasetType(String description, String name)
    {
        this.description = description;
        this.name = name;
    }

    /**
     * Return the description of the dataset type
     * @return the description of the dataset type
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the dataset type
     * @param description the description of the dataset type
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Return the name of the dataset type
     * @return the name of the dataset type
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the dataset type
     * @param name of the dataset type
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
