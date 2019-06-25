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
public class Language
{

    /**
     * The name of the language
     */
    @Id
    private String language;

    /**
     * The datasets associated to this language
     */
    @ManyToMany(mappedBy="language")
    private Set<Dataset> datasets;


    /**
     * The default constructor
     */
    public Language()
    {

    }

    /**
     * Return datasets associated to this language
     * @return datasets associated to this language
     */
    public Set<Dataset> getDatasets()
    {
        return this.datasets;
    }

    /**
     * Stablish the datasets associated to this language
     * @param datasets associated to this language
     */
    public void setDatasets(Set<Dataset> datasets)
    {
        this.datasets = datasets;
    }

    /**
     * Returns the name of the language
     * @return the name of the language
     */ 
    public String getLanguage()
    {
        return this.language;
    }

    /**
     * Stablish the name of the language
     * @param language the name of the language
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }
}