package com.project.onlinepreprocessor.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Datatype
{

    @Id
    private String datatype;

    @ManyToMany(mappedBy="datatypes")
    private Set<Dataset> datasets;

    protected Datatype()
    {

    }

    public Set<Dataset> getDatasets()
    {
        return this.datasets;
    }

    public void setDatasets(Set<Dataset> datasets)
    {
        this.datasets = datasets;
    }

    public String getDatatype()
    {
        return this.datatype;
    }

    public void setDatatype(String datatype)
    {
        this.datatype = datatype;
    }
}