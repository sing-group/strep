package com.project.onlinepreprocessor.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Language
{

    @Id
    private String language;

    @ManyToMany(mappedBy="language")
    private Set<Dataset> datasets;


    public Language()
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

    public String getLanguage()
    {
        return this.language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}