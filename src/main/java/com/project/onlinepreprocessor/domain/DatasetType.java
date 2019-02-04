package com.project.onlinepreprocessor.domain;

import javax.persistence.*;


/**
 * JPA Bean for the DatasetType objects managed by application
 * @author Ismael Vázqez
 */

@Entity
@Table(name="dataset_types")
public class DatasetType
{

    /**
     * The id of the dataset type
     */
    @Id
    private int id;

    /**
     * The description of the dataset type
     */
    private String description;
    
    /**
     * The name of the dataset type
     */
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