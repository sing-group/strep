package com.project.onlinepreprocessor.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="dataset_name", referencedColumnName="name")
    private Dataset dataset;

    private String state;

    private String message;

    public Task(Dataset dataset, String state, String message)
    {
        this.dataset = dataset;
        this.state = state;
        this.message = message;
    }

    public Task()
    {

    }

    public Dataset getDataset()
    {
        return this.dataset;
    }

    public void setDataset(Dataset dataset)
    {
        this.dataset = dataset;
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}