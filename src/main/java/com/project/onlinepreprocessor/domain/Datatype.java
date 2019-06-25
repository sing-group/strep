package com.project.onlinepreprocessor.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class Datatype
{
     /**
      * The name of the datatype
      */
    @Id
    private String datatype;

    /**
     * Datasets associated to this datatype
     */
    @ManyToMany(mappedBy="datatypes")
    private Set<Dataset> datasets;

    /**
     * The default constructor
     */
    protected Datatype()
    {

    }

    /**
     * Return the datasets associated to this datatype
     * @return datasets associated to this datatype
     */
    public Set<Dataset> getDatasets()
    {
        return this.datasets;
    }

    /**
     * Stablish the datasets associated to this datatype
     * @param datasets datasets associated to this datatype
     */
    public void setDatasets(Set<Dataset> datasets)
    {
        this.datasets = datasets;
    }

    /**
     * Return the name of the datatype
     * @return the name of the datatype
     */
    public String getDatatype()
    {
        return this.datatype;
    }

    /**
     * Stablish the name of the datatype
     * @param datatype the name of the datatype
     */
    public void setDatatype(String datatype)
    {
        this.datatype = datatype;
    }
}