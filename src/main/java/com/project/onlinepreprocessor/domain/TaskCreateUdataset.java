package com.project.onlinepreprocessor.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.JoinColumn;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázqez
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class TaskCreateUdataset extends Task
{
    /**
     * The limit of percentage of spam in the new dataset
     */
    @Column(nullable = true)
    private int limitPercentageSpam;

    /**
     * The limit of files of type spam in the new dataset
     */
    @Column(nullable = true)
    private int limitSpam;

    /**
     * The limit of files of the new dataset
     */
    @Column(nullable = true)
    private int limitNumberOfFiles;

    /**
     * The date from which you can pick up files from the original datasets
     */
    @Column(nullable = true)
    private Date dateFrom;

    /**
     * The date until which you can pick up files from the original datasets
     */
    @Column(nullable = true)
    private Date dateTo;

    /**
     * The languages selected in the filters to construct the new dataset
     */
    @ManyToMany
    @JoinTable(name="taskcreateudataset_languages", joinColumns= @JoinColumn(name = "task_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name="language", referencedColumnName="language"))
    private List<Language> languages;

    /**
     * The datatypes selected in the filters to construct the new dataset
     */
    @ManyToMany
    @JoinTable(name="taskcreateudataset_datatypes", joinColumns= @JoinColumn(name = "task_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name="datatype", referencedColumnName="dataType"))
    private List<Datatype> datatypes;

    /**
     * The licenses selected in the filters to construct the new dataset
     */
    @ManyToMany
    @JoinTable(name="taskcreateudataset_licenses", joinColumns= @JoinColumn(name = "task_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name="license", referencedColumnName="name"))
    private List<License> licenses;

    /**
     * The limit of percentage of .eml files
     */
    @Column(nullable = true)
    private int limitPercentageEml;

    /**
     * The limit of percentage of .tytb files
     */
    @Column(nullable = true)
    private int limitPercentageTytb;

    /**
     * The limit of percentage of .tsms files
     */
    @Column(nullable = true)
    private int limitPercentageTsms;

    /**
     * The limit of percentage of .twtid files
     */
    @Column(nullable = true)
    private int limitPercentageTwtid;

    /**
     * The limit of percentage of .warc files
     */
    @Column(nullable = true)
    private int limitPercentageWarc;


    /**
     * Creates an instance of TaskCreateUdataset
     * @param dataset The dataset associated to the task
     * @param state The state of the task
     * @param message The message of the task when failed
     * @param limitPercentageSpam The limit of percentage of spam in the new dataset
     * @param limitSpam The limit of files of type spam in the new dataset
     * @param limitNumberOfFiles The limit of files of the new dataset
     * @param dateFrom The date from which you can pick up files from the original datasets
     * @param dateTo  The date until which you can pick up files from the original datasets
     * @param languages The languages selected in the filters to construct the new dataset
     * @param datatypes The datatypes selected in the filters to construct the new dataset
     * @param licenses The licenses selected in the filters to construct the new dataset
     * @param limitPercentageEml The limit of percentage of .eml files
     * @param limitPercentageTytb The limit of percentage of .tytb files
     * @param limitPercentageTsms The limit of percentage of .tsms files
     * @param limitPercentageTwtid The limit of percentage of .twtid files
     * @param limitPercentageWarc The limit of percentage of .warc files
     */
    public TaskCreateUdataset(Dataset dataset, String state, String message, int limitPercentageSpam, int limitSpam, int limitNumberOfFiles, 
    Date dateFrom, Date dateTo, List<Language> languages, List<Datatype> datatypes,List<License> licenses, int limitPercentageEml,
     int limitPercentageTytb, int limitPercentageTsms, int limitPercentageTwtid, int limitPercentageWarc) {
        super(dataset, state, message);
        this.limitPercentageSpam = limitPercentageSpam;
        this.limitSpam = limitSpam;
        this.limitNumberOfFiles = limitNumberOfFiles;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.languages = languages;
        this.datatypes = datatypes;
        this.licenses = licenses;
        this.limitPercentageEml = limitPercentageEml;
        this.limitPercentageTytb = limitPercentageTytb;
        this.limitPercentageTsms = limitPercentageTsms;
        this.limitPercentageTwtid = limitPercentageTwtid;
        this.limitPercentageWarc = limitPercentageWarc;
    }

    /**
     * The default constructor
     */
    public TaskCreateUdataset()
    {
        super();
    }

    /**
     * Return the limit of percentage of spam in the new dataset
     * @return the limit of percentage of spam in the new dataset
     */
    public int getLimitPercentageSpam() {
        return this.limitPercentageSpam;
    }

    /**
     * Stablish the limit of percentage of spam in the new dataset
     * @param limitPercentageSpam the limit of percentage of spam in the new dataset
     */
    public void setLimitPercentageSpam(int limitPercentageSpam) {
        this.limitPercentageSpam = limitPercentageSpam;
    }

    /**
     * Return the limit of files of type spam in the new dataset
     * @return the limit of files of type spam in the new dataset
     */
    public int getLimitSpam() {
        return this.limitSpam;
    }

    /**
     * Stablish the limit of files of type spam in the new dataset
     * @param limitSpam the limit of files of type spam in the new dataset
     */
    public void setLimitSpam(int limitSpam) {
        this.limitSpam = limitSpam;
    }

    /**
     * Return the limit of files in the new dataset
     * @return the limit of files in the new dataset
     */
    public int getLimitNumberOfFiles() {
        return this.limitNumberOfFiles;
    }

    /**
     * Stablish the limit of files in the new dataset
     * @param limitNumberOfFiles the limit of files in the new dataset
     */
    public void setLimitNumberOfFiles(int limitNumberOfFiles) {
        this.limitNumberOfFiles = limitNumberOfFiles;
    }

    /**
     * Return the date from which you can pick up files from the original datasets
     * @return  the date from which you can pick up files from the original datasets
     */
    public Date getDateFrom() {
        return this.dateFrom;
    }

    /**
     * Stablish the date from which you can pick up files from the original datasets
     * @param dateFrom the date from which you can pick up files from the original datasets
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Return the date until you can pick up files from the original datasets
     * @return the date until you can pick up files from the original datasets
     */
    public Date getDateTo() {
        return this.dateTo;
    }

    /**
     * Stablish the date until you can pick up files from the original datasets
     * @param dateTo the date until you can pick up files from the original datasets
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Return the list of the languages selected in the filters to construct the new dataset
     * @return the list of the languages selected in the filters to construct the new dataset
     */
    public List<Language> getLanguages() {
        return this.languages;
    }

    /**
     * Stablish the list of the languages selected in the filters to construct the new dataset
     * @param languages the list of the languages selected in the filters to construct the new dataset
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    /**
     * Return the list of the datatypes selected in the filters to construct the new dataset
     * @return the list of the datatypes selected in the filters to construct the new dataset
     */
    public List<Datatype> getDatatypes() {
        return this.datatypes;
    }

    /**
     * Stablish the list of the datatypes selected in the filters to construct the new dataset
     * @param datatypes the list of the datatypes selected in the filters to construct the new dataset
     */
    public void setDatatypes(List<Datatype> datatypes) {
        this.datatypes = datatypes;
    }

    /**
     * Return the limit of percentage of .eml files
     * @return the limit of percentage of .eml files
     */
    public int getLimitPercentageEml() {
        return this.limitPercentageEml;
    }

    /**
     * Stablish the limit of percentage of .eml files
     * @param limitPercentageEml the limit of percentage of .eml files
     */
    public void setLimitPercentageEml(int limitPercentageEml) {
        this.limitPercentageEml = limitPercentageEml;
    }

    /**
     * Return the limit of percentage of .tytb files
     * @return the limit of percentage of .tytb files
     */
    public int getLimitPercentageTytb() {
        return this.limitPercentageTytb;
    }

    /**
     * Stablish the limit of percentage of .tytb files
     * @param limitPercentageTytb the limit of percentage of .tytb files
     */
    public void setLimitPercentageTytb(int limitPercentageTytb) {
        this.limitPercentageTytb = limitPercentageTytb;
    }

    /**
     * Return the limit of percentage of .tsms files
     * @return the limit of percentage of .tsms files
     */
    public int getLimitPercentageTsms() {
        return this.limitPercentageTsms;
    }

    /**
     * Stablish the limit of percentage of .tsms files
     * @param limitPercentageTsms the limit of percentage of .tsms files
     */
    public void setLimitPercentageTsms(int limitPercentageTsms) {
        this.limitPercentageTsms = limitPercentageTsms;
    }

    /**
     * Return the limit of percentage of .twtid files
     * @return the limit of percentage of .twtid files
     */
    public int getLimitPercentageTwtid() {
        return this.limitPercentageTwtid;
    }

    /**
     * Stablish the limit of percentage of .twtid files
     * @param limitPercentageTwtid the limit of percentage of .twtid files
     */
    public void setLimitPercentageTwtid(int limitPercentageTwtid) {
        this.limitPercentageTwtid = limitPercentageTwtid;
    }

    /**
     * Return the limit of percentage of .warc files
     * @return the limit of percentage of .warc files
     */
    public int getLimitPercentageWarc() {
        return this.limitPercentageWarc;
    }

    /**
     * Stablish the limit of percentage of .warc files
     * @param limitPercentageWarc the limit of percentage of .warc files
     */
    public void setLimitPercentageWarc(int limitPercentageWarc) {
        this.limitPercentageWarc = limitPercentageWarc;
    }

    /**
     * Return the list of licenses selected in the filters to construct the new dataset
     * @return the list of licenses selected in the filters to construct the new dataset
     */
    public List<License> getLicenses()
    {
        return licenses;
    }

    /**
     * Stablish the list of licenses selected in the filters to construct the new dataset
     * @param licenses the list of licenses selected in the filters to construct the new dataset
     */
    public void setLicenses(List<License> licenses)
    {
        this.licenses = licenses;
    }
    

}
