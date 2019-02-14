package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import com.project.onlinepreprocessor.domain.File;
import java.util.Set;
import java.util.Date;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázqez
 */
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
    @Lob
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

    /**
     * The percentage of Spam
     */
    private int spamPercentage;

    /**
     * The percentage of ham
     */
    private int hamPercentage;

    /**
     * The default constructor
     */
    public Dataset() {

    }

    /**
     * Return the name of the dataset
     * @return the name of the dataset
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the mame of the dataset
     * @param name the mame of the dataset
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the author of the dataset
     * @return the author of the dataset
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the name of the author
     * @param author the name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the description of the dataset
     * @return the description of the dataset
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the datset
     * @param description the description of the datset
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the preprocessing pipeline used to generate the corpus
     * @return the preprocessing pipeline used to generate the corpus
     */
    public String getPipeline() {
        return pipeline;
    }

    /**
     * Stablish the preprocessing pipeline used to generate the corpus
     * @param pipeline the preprocessing pipeline used to generate the corpus
     */
    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * Returns the DOI (Document Identifier for the corpus)
     * @return the DOI (Document Identifier for the corpus)
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the the DOI (Document Identifier for the corpus)
     * @param doi the DOI (Document Identifier for the corpus)
     */
    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * Returns the access for the corpus
     * @return the access for the corpus
     */
    public String getAccess() {
        return access;
    }

    /**
     * Stablish the access for the corpus
     * @param access the access for the corpus
     */
    public void setAccess(String access) {
        this.access = access;
    }

    /**
     * Returns the languages contained in the corpus
     * @return the languages contained in the corpus
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Stablish the languages contained in the corpus
     * @param language the languages contained in the corpus
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Returns the date when the corpus was uploaded
     * @return the date when the corpus was uploaded
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * Stablish the date when the corpus was uploaded
     * @param uploadDate the date when the corpus was uploaded
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Returns the percentage of spam messages
     * @return the percentage of spam messages
     */
    public int getSpamPercentage() {
        return spamPercentage;
    }

    /**
     * Stablish the percentage of spam messages
     * @param spamPercentage the percentage of spam messages
     */
    public void setSpamPercentage(int spamPercentage) {
        this.spamPercentage = spamPercentage;
    }

    /**
     * Returns the percentage of ham messages
     * @return the percentage of ham messages
     */
    public int getHamPercentage() {
        return hamPercentage;
    }

    /**
     * Stablish the percentage of ham messages
     * @param hamPercentage  the percentage of ham messages
     */
    public void setHamPercentage(int hamPercentage) {
        this.hamPercentage = hamPercentage;
    }

    /**
     * Returns the type of the corpus
     * @return the type of the corpus
     */
    public String getType() {
        return type;
    }

    /**
     * Stablish the type of the corpus
     * @param type the type of the corpus
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the files included in the dataset
     * @return the list of files included in the dataset
     */
    public Set<File> getFiles() {
        return files;
    }
}