package com.project.onlinepreprocessor.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.JoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class TaskCreateUser extends Task
{
    @Column(nullable = true)
    private int limitPercentageSpam;

    @Column(nullable = true)
    private int limitSpam;

    @Column(nullable = true)
    private int limitNumberOfFiles;

    @Column(nullable = true)
    private Date dateFrom;

    @Column(nullable = true)
    private Date dateTo;

    @ManyToMany
    @JoinTable(name="taskcreateuser_languages", joinColumns= @JoinColumn(name = "task_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name="language", referencedColumnName="language"))
    private List<Language> languages;

    @ManyToMany
    @JoinTable(name="taskcreateuser_datatypes", joinColumns= @JoinColumn(name = "task_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name="datatype", referencedColumnName="dataType"))
    private List<Datatype> datatypes;

    @Column(nullable = true)
    private int limitPercentageEml;

    @Column(nullable = true)
    private int limitPercentageTytb;

    @Column(nullable = true)
    private int limitPercentageTsms;

    @Column(nullable = true)
    private int limitPercentageTwtid;

    @Column(nullable = true)
    private int limitPercentageWarc;



    public TaskCreateUser(Dataset dataset, String state, String message, int limitPercentageSpam, int limitSpam, int limitNumberOfFiles, 
    Date dateFrom, Date dateTo, List<Language> languages, List<Datatype> datatypes, int limitPercentageEml,
     int limitPercentageTytb, int limitPercentageTsms, int limitPercentageTwtid, int limitPercentageWarc) {
        super(dataset, state, message);
        this.limitPercentageSpam = limitPercentageSpam;
        this.limitSpam = limitSpam;
        this.limitNumberOfFiles = limitNumberOfFiles;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.languages = languages;
        this.datatypes = datatypes;
        this.limitPercentageEml = limitPercentageEml;
        this.limitPercentageTytb = limitPercentageTytb;
        this.limitPercentageTsms = limitPercentageTsms;
        this.limitPercentageTwtid = limitPercentageTwtid;
        this.limitPercentageWarc = limitPercentageWarc;
    }

    public int getLimitPercentageSpam() {
        return this.limitPercentageSpam;
    }

    public void setLimitPercentageSpam(int limitPercentageSpam) {
        this.limitPercentageSpam = limitPercentageSpam;
    }

    public int getLimitSpam() {
        return this.limitSpam;
    }

    public void setLimitSpam(int limitSpam) {
        this.limitSpam = limitSpam;
    }

    public int getLimitNumberOfFiles() {
        return this.limitNumberOfFiles;
    }

    public void setLimitNumberOfFiles(int limitNumberOfFiles) {
        this.limitNumberOfFiles = limitNumberOfFiles;
    }

    public Date getDateFrom() {
        return this.dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return this.dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Language> getLanguages() {
        return this.languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Datatype> getDatatypes() {
        return this.datatypes;
    }

    public void setDatatypes(List<Datatype> datatypes) {
        this.datatypes = datatypes;
    }

    public int getLimitPercentageEml() {
        return this.limitPercentageEml;
    }

    public void setLimitPercentageEml(int limitPercentageEml) {
        this.limitPercentageEml = limitPercentageEml;
    }

    public int getLimitPercentageTytb() {
        return this.limitPercentageTytb;
    }

    public void setLimitPercentageTytb(int limitPercentageTytb) {
        this.limitPercentageTytb = limitPercentageTytb;
    }

    public int getLimitPercentageTsms() {
        return this.limitPercentageTsms;
    }

    public void setLimitPercentageTsms(int limitPercentageTsms) {
        this.limitPercentageTsms = limitPercentageTsms;
    }

    public int getLimitPercentageTwtid() {
        return this.limitPercentageTwtid;
    }

    public void setLimitPercentageTwtid(int limitPercentageTwtid) {
        this.limitPercentageTwtid = limitPercentageTwtid;
    }

    public int getLimitPercentageWarc() {
        return this.limitPercentageWarc;
    }

    public void setLimitPercentageWarc(int limitPercentageWarc) {
        this.limitPercentageWarc = limitPercentageWarc;
    }
    

}
