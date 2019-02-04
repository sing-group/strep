package com.project.onlinepreprocessor.domain;

import javax.persistence.*;

@Entity
@Table(name="dataset_types")
public class DatasetType
{

    @Id
    private int id;

    private String description;
    
    private String name;


    protected DatasetType()
    {

    }

    public DatasetType(String description, String name)
    {
        this.description = description;
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



}