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