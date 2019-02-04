package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import com.project.onlinepreprocessor.domain.File;
import java.util.Set;
import java.util.Date;


@Entity
public class Dataset
{
    /**
     * The name of the dataset
     */
    @Id
    private String name;
      /**
     * The author of the dataset
     */
    private String author;
    /**
     * The description of the dataset
     */
    private String description;

     /**
     * The pipeline used to generate the dataset
     */
    private String pipeline;

    /**
     * The doi for the dataset
     */
    private String doi;

    /**
     * The accesstype for the dataset
     */
    private String access;

    /*TODO: Multiple languages for each Dataset*/
    /**
     * The language of the dataset
     */
    private String language;

    /**
     * The type of the dataset
     */
    private String type;

    /**
     * The date when the dataset was uploaded
     */
    private Date uploadDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="dataset_files", joinColumns= @JoinColumn(name = "dataset_name", referencedColumnName = "name"), 
    inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName="id"))
    private Set<File> files;

    private int percentageSpam;

    private int percentageHam;

    public Dataset()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPipeline()
    {
        return pipeline;
    }

    public void setPipeline(String pipeline)
    {
        this.pipeline = pipeline;
    }

    public String getDoi()
    {
        return doi;
    }

    public void setDoi(String doi)
    {
        this.doi = doi;
    }

    public String getAccess()
    {
        return access;
    }

    public void setAccess(String access)
    {
        this.access = access;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public Date getUploadDate()
    {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public int getPercentageSpam()
    {
        return percentageSpam;
    }

    public void setPercentageSpam(int percentageSpam)
    {
        this.percentageSpam = percentageSpam;
    }

    public int getPercentageHam()
    {
        return percentageHam;
    }

    public void setPercentageHam(int percentageHam)
    {
        this.percentageHam = percentageHam;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Set<File> getFiles()
    {
        return files;
    }
}