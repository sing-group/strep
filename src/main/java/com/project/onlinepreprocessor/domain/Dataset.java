package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.project.onlinepreprocessor.domain.File;
import com.project.onlinepreprocessor.domain.Task;
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
    @NotNull
    @Size(min=8, max=50, message="Name must have beetween 8 and 50 characters")
    private String name;
    
    /**
     * The author of the dataset
     */
    private String author;

    /**
     * The description of the dataset
     */
    @NotNull
    @Size(min=30, max=1000, message="Description must have beetween 30 and 1000 characters")
    private String description;

    /**
     * The pipeline used to generate the dataset
     */
    @Lob
    private byte[] pipeline;

    /**
     * The doi for the dataset
     */
    private String doi;

    /**
     * The accesstype for the dataset
     */
    private String access;

    /**
     * The languages of the dataset
     */
    @ManyToMany
    @JoinTable(name="dataset_languages", joinColumns=@JoinColumn(name="dataset_name", referencedColumnName="name"), 
    inverseJoinColumns = @JoinColumn(name="language", referencedColumnName="language"))
    private Set<Language> language;

    /**
     * The data types of the dataset
     */
    @ManyToMany
    @JoinTable(name="dataset_datatypes", joinColumns=@JoinColumn(name="dataset_name", referencedColumnName="name"),
    inverseJoinColumns = @JoinColumn(name="dataType", referencedColumnName="dataType"))
    private Set<Datatype> datatypes;
    /**
     * The type of the dataset
     */
    private String type;

    /**
     * The date when the dataset was uploaded
     */
    private Date uploadDate;

    /**
     * The URL to access the dataset when the access is public
     */
    private String url;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="dataset_files", joinColumns= @JoinColumn(name = "dataset_name", referencedColumnName = "name"), 
    inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName="id"))
    private Set<File> files;

    /**
     * The percentage of Spam
     */
    private Integer spamPercentage;

    /**
     * The percentage of ham
     */
    private Integer hamPercentage;

    /**
     * Indicates that the dataset is available or not
     */
    private boolean available;

    @OneToOne
    @JoinColumn(name="task_id", referencedColumnName="id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private License license;

    @Null
    private Date firstFileDate;

    @Null
    private Date lastFileDate;

    /**
     * The default constructor
     */
    protected Dataset() {

    }

    public Dataset(String name,String url, String author, String description, String access, Integer spamPercentage, Integer hamPercentage, 
     String type, License license)
    {
        this.name = name;
        this.url = url;
        this.author = author;
        this.description = description;
        this.access = access;
        this.spamPercentage = spamPercentage;
        this.hamPercentage = hamPercentage;
        this.type = type;
        this.available = false;
        this.license = license;
    }

    /**
     * Return the name of the dataset
     * @return the name of the dataset
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the dataset
     * @param name the mame of the dataset
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the url of the dataset
     * @return
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Sets the url of the dataset
     * @param url
     */
    public void setUrl(String url)
    {
        this.url = url;
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
    public byte[] getPipeline() {
        return pipeline;
    }

    /**
     * Stablish the preprocessing pipeline used to generate the corpus
     * @param pipeline the preprocessing pipeline used to generate the corpus
     */
    public void setPipeline(byte[] pipeline) {
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
    public Set<Language> getLanguage() {
        return language;
    }

    /**
     * Stablish the languages contained in the corpus
     * @param language the languages contained in the corpus
     */
    public void setLanguage(Set<Language> language) {
        this.language = language;
    }

    /**
     * Returns the datatypes of the dataset
     * @return the datatypes of the dataset
     */
    public Set<Datatype> getDatatypes()
    {
        return this.datatypes;
    }

     /**
      * Stablish the datatypes of the dataset
      * @param dataType the datatypes of the dataset
      */
      public void setDatatypes(Set<Datatype> datatypes)
      {
          this.datatypes = datatypes;
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
    public Integer getSpamPercentage() {
        return spamPercentage;
    }

    /**
     * Stablish the percentage of spam messages
     * @param spamPercentage the percentage of spam messages
     */
    public void setSpamPercentage(int spamPercentage) {
        this.spamPercentage = new Integer(spamPercentage);
    }

    /**
     * Returns the percentage of ham messages
     * @return the percentage of ham messages
     */
    public Integer getHamPercentage() {
        return hamPercentage;
    }

    /**
     * Stablish the percentage of ham messages
     * @param hamPercentage  the percentage of ham messages
     */
    public void setHamPercentage(int hamPercentage) {
        this.hamPercentage = new Integer(hamPercentage);
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

    /**
     * Returns if the dataset is available or not
     * @return the availability of the dataset
     */
    public boolean getAvailable()
    {
        return available;
    }

    /**
     * Stablish the availability of the dataset
     * @param available the availability 
     */
    public void setAvailable(boolean available)
    {
        this.available = available;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public License getLicense()
    {
        return this.license;
    }

    public void setLicense(License license)
    {
        this.license = license;
    }

    public Date getFirstFileDate()
    {
        return firstFileDate;
    }

    public void setFirstFileDate(Date firstFileDate)
    {
        this.firstFileDate = firstFileDate;
    }

    public Date getLastFileDate()
    {
        return lastFileDate;
    }

    public void setLastFileDate(Date lastFileDate)
    {
        this.lastFileDate = lastFileDate;
    }
}