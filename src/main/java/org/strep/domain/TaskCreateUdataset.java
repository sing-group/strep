/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import javax.persistence.JoinColumn;

/**
 * JPA Bean for the Dataset objects managed by application
 * 
 * @author Ismael Vázquez
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TaskCreateUdataset extends Task {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The spam percentage limit for eml files
     */
    private int limitSpamPercentageEml;

    /**
     * The ham percentage limit for eml files
     */
    private int limitHamPercentageEml;

    /**
     * The spam percentage limit for twtid files
     */
    private int limitSpamPercentageTwtid;

    /**
     * The ham percentage limit for twtid files
     */
    private int limitHamPercentageTwtid;

    /**
     * The spam percentage limit for ytbid files
     */
    private int limitSpamPercentageYtbid;

    /**
     * The ham percentage limit for ytbid files
     */
    private int limitHamPercentageYtbid;

    /**
     * The spam percentage limit for warc files
     */
    private int limitSpamPercentageWarc;

    /**
     * The ham percentage limit for warc files
     */
    private int limitHamPercentageWarc;

    /**
     * The spam percentage limit for tsms files
     */
    private int limitSpamPercentageTsms;

    /**
     * The ham percentage limit for tsms files
     */
    private int limitHamPercentageTsms;
    /**
     * The limit of percentage of spam in the new dataset
     */
    private int limitPercentageSpam;

    /**
     * The limit of files of the new dataset
     */
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
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "taskcreateudataset_languages", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "language", referencedColumnName = "language"))
    private List<Language> languages;

    /**
     * The datatypes selected in the filters to construct the new dataset
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "taskcreateudataset_datatypes", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "datatype", referencedColumnName = "dataType"))
    private List<Datatype> datatypes;

    /**
     * The licenses selected in the filters to construct the new dataset
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "taskcreateudataset_licenses", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "license", referencedColumnName = "name"))
    private List<License> licenses;

    /**
     * The datasets used to construct the new dataset
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "dataset", referencedColumnName = "name"))
    private List<Dataset> datasets;

    /**
     * To identify the selected mode: spam or datatypes
     */
    private boolean spamMode;

    /**
     * Creates an instance of TaskCreateUdataset
     * 
     * @param dataset                  The dataset associated to the task
     * @param state                    The state of the task
     * @param message                  The message of the task when failed
     * @param limitPercentageSpam      The limit of percentage of spam in the new
     *                                 dataset
     * @param limitNumberOfFiles       The limit of files of the new dataset
     * @param dateFrom                 The date from which you can pick up files
     *                                 from the original datasets
     * @param dateTo                   The date until which you can pick up files
     *                                 from the original datasets
     * @param languages                The languages selected in the filters to
     *                                 construct the new dataset
     * @param datatypes                The datatypes selected in the filters to
     *                                 construct the new dataset
     * @param licenses                 The licenses selected in the filters to
     *                                 construct the new dataset
     * @param datasets
     * @param limitSpamPercentageEml
     * @param limitHamPercentageEml
     * @param limitSpamPercentageWarc
     * @param limitHamPercentageWarc
     * @param limitSpamPercentageTytb
     * @param limitSpamPercentageTsms
     * @param limitHamPercentageTsms
     * @param limitSpamPercentageTwtid
     * @param limitHamPercentageTwtid
     * @param spamMode
     * @param limitHamPercentageTytb
     */
    public TaskCreateUdataset(final Dataset dataset, final String state, final String message,
            final int limitPercentageSpam, final int limitNumberOfFiles, final Date dateFrom, final Date dateTo,
            final List<Language> languages, final List<Datatype> datatypes, final List<License> licenses,
            final List<Dataset> datasets, final int limitSpamPercentageEml, final int limitHamPercentageEml,
            final int limitSpamPercentageWarc, final int limitHamPercentageWarc, final int limitSpamPercentageTytb,
            final int limitHamPercentageTytb, final int limitSpamPercentageTsms, final int limitHamPercentageTsms,
            final int limitSpamPercentageTwtid, final int limitHamPercentageTwtid, final boolean spamMode) {
        super(dataset, state, message);
        this.limitSpamPercentageEml = limitSpamPercentageEml;
        this.limitHamPercentageEml = limitHamPercentageEml;
        this.limitSpamPercentageTsms = limitSpamPercentageTsms;
        this.limitHamPercentageTsms = limitHamPercentageTsms;
        this.limitSpamPercentageTwtid = limitSpamPercentageTwtid;
        this.limitHamPercentageTwtid = limitHamPercentageTwtid;
        this.limitSpamPercentageWarc = limitSpamPercentageWarc;
        this.limitHamPercentageWarc = limitHamPercentageWarc;
        this.limitSpamPercentageYtbid = limitSpamPercentageTytb;
        this.limitHamPercentageYtbid = limitHamPercentageTytb;
        this.limitPercentageSpam = limitPercentageSpam;
        this.limitNumberOfFiles = limitNumberOfFiles;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.languages = languages;
        this.datatypes = datatypes;
        this.licenses = licenses;
        this.datasets = datasets;
        this.spamMode = spamMode;
    }

    /**
     * The default constructor
     */
    public TaskCreateUdataset() {
        super();
    }

    /**
     * Return the limit of percentage of spam in the new dataset
     * 
     * @return the limit of percentage of spam in the new dataset
     */
    public int getLimitPercentageSpam() {
        return this.limitPercentageSpam;
    }

    /**
     * Stablish the limit of percentage of spam in the new dataset
     * 
     * @param limitPercentageSpam the limit of percentage of spam in the new dataset
     */
    public void setLimitPercentageSpam(final int limitPercentageSpam) {
        this.limitPercentageSpam = limitPercentageSpam;
    }

    /**
     * Return the limit of files in the new dataset
     * 
     * @return the limit of files in the new dataset
     */
    public int getLimitNumberOfFiles() {
        return this.limitNumberOfFiles;
    }

    /**
     * Stablish the limit of files in the new dataset
     * 
     * @param limitNumberOfFiles the limit of files in the new dataset
     */
    public void setLimitNumberOfFiles(final int limitNumberOfFiles) {
        this.limitNumberOfFiles = limitNumberOfFiles;
    }

    /**
     * Return the date from which you can pick up files from the original datasets
     * 
     * @return the date from which you can pick up files from the original datasets
     */
    public Date getDateFrom() {
        return this.dateFrom;
    }

    /**
     * Stablish the date from which you can pick up files from the original datasets
     * 
     * @param dateFrom the date from which you can pick up files from the original
     *                 datasets
     */
    public void setDateFrom(final Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Return the date until you can pick up files from the original datasets
     * 
     * @return the date until you can pick up files from the original datasets
     */
    public Date getDateTo() {
        return this.dateTo;
    }

    /**
     * Stablish the date until you can pick up files from the original datasets
     * 
     * @param dateTo the date until you can pick up files from the original datasets
     */
    public void setDateTo(final Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Return the list of the languages selected in the filters to construct the new
     * dataset
     * 
     * @return the list of the languages selected in the filters to construct the
     *         new dataset
     */
    public List<Language> getLanguages() {
        return this.languages;
    }

    /**
     * Stablish the list of the languages selected in the filters to construct the
     * new dataset
     * 
     * @param languages the list of the languages selected in the filters to
     *                  construct the new dataset
     */
    public void setLanguages(final List<Language> languages) {
        this.languages = languages;
    }

    /**
     * Return the list of the datatypes selected in the filters to construct the new
     * dataset
     * 
     * @return the list of the datatypes selected in the filters to construct the
     *         new dataset
     */
    public List<Datatype> getDatatypes() {
        return this.datatypes;
    }

    /**
     * Stablish the list of the datatypes selected in the filters to construct the
     * new dataset
     * 
     * @param datatypes the list of the datatypes selected in the filters to
     *                  construct the new dataset
     */
    public void setDatatypes(final List<Datatype> datatypes) {
        this.datatypes = datatypes;
    }

    /**
     * Return the list of licenses selected in the filters to construct the new
     * dataset
     * 
     * @return the list of licenses selected in the filters to construct the new
     *         dataset
     */
    public List<License> getLicenses() {
        return licenses;
    }

    /**
     * Stablish the list of licenses selected in the filters to construct the new
     * dataset
     * 
     * @param licenses the list of licenses selected in the filters to construct the
     *                 new dataset
     */
    public void setLicenses(final List<License> licenses) {
        this.licenses = licenses;
    }

    /**
     * Return the list of datasets used to construct the new dataset
     * 
     * @return the list of datasets used to construct the new dataset
     */
    public List<Dataset> getDatasets() {
        return this.datasets;
    }

    /**
     * Stablish the list of datasets used to construct the new dataset
     * 
     * @param datasets the list of datasets used to construct the new dataset
     */
    public void setDatasets(final List<Dataset> datasets) {
        this.datasets = datasets;
    }

    /**
     * Return the spam percentage limit for eml files
     * 
     * @return the spam percentage limit for eml files
     */
    public int getLimitSpamPercentageEml() {
        return this.limitSpamPercentageEml;
    }

    /**
     * Stablish the spam percentage limit for eml files
     * 
     * @param limitSpamPercentageEml the spam percentage limit for eml files
     */
    public void setLimitSpamPercentageEml(final int limitSpamPercentageEml) {
        this.limitSpamPercentageEml = limitSpamPercentageEml;
    }

    /**
     * Return the ham percentage limit for eml files
     * 
     * @return the ham percentage limit for eml files
     */
    public int getLimitHamPercentageEml() {
        return this.limitHamPercentageEml;
    }

    /**
     * Stablish the ham percentage limit for eml files
     * 
     * @param limitHamPercentageEml the ham percentage limit for eml files
     */
    public void setLimitHamPercentageEml(final int limitHamPercentageEml) {
        this.limitHamPercentageEml = limitHamPercentageEml;
    }

    /**
     * Return the spam percentage limit for warc files
     * 
     * @return the spam percentage limit for warc files
     */
    public int getLimitSpamPercentageWarc() {
        return this.limitSpamPercentageWarc;
    }

    /**
     * Stablish the spam percentage limit for warc files
     * 
     * @param limitSpamPercentageWarc the spam percentage limit for warc files
     */
    public void setLimitSpamPercentageWarc(final int limitSpamPercentageWarc) {
        this.limitSpamPercentageWarc = limitSpamPercentageWarc;
    }

    /**
     * Return the ham percentage limit for warc files
     * 
     * @return the ham percentage limit for warc files
     */
    public int getLimitHamPercentageWarc() {
        return this.limitHamPercentageWarc;
    }

    /**
     * Stablish the ham percentage limit for warc files
     * 
     * @param limitHamPercentageWarc the ham percentage limit for warc files
     */
    public void setLimitHamPercentageWarc(final int limitHamPercentageWarc) {
        this.limitHamPercentageWarc = limitHamPercentageWarc;
    }

    /**
     * Return the spam percentage limit for tsms files
     * 
     * @return the spam percentage limit for tsms files
     */
    public int getLimitSpamPercentageTsms() {
        return this.limitSpamPercentageTsms;
    }

    /**
     * Stablish the spam percentage limit for tsms files
     * 
     * @param limitSpamPercentageTsms the spam percentage limit for tsms files
     */
    public void setLimitSpamPercentageTsms(final int limitSpamPercentageTsms) {
        this.limitSpamPercentageTsms = limitSpamPercentageTsms;
    }

    /**
     * Return the ham percentage limit for tsms files
     * 
     * @return the ham percentage limit for tsms files
     */
    public int getLimitHamPercentageTsms() {
        return this.limitHamPercentageTsms;
    }

    /**
     * Stablish the ham percentage limit for tsms files
     * 
     * @param limitHamPercentageTsms the ham percentage limit for tsms files
     */
    public void setLimitHamPercentageTsms(final int limitHamPercentageTsms) {
        this.limitHamPercentageTsms = limitHamPercentageTsms;
    }

    /**
     * Return the spam percentage limit for ytbid files
     * 
     * @return the spam percentage limit for ytbid files
     */
    public int getLimitSpamPercentageYtbid() {
        return this.limitSpamPercentageYtbid;
    }

    /**
     * Stablish the spam percentage limit for ytbid files
     * 
     * @param limitSpamPercentageYtbid the spam percentage limit for ytbid files
     */
    public void setLimitSpamPercentageYtbid(final int limitSpamPercentageYtbid) {
        this.limitSpamPercentageYtbid = limitSpamPercentageYtbid;
    }

    /**
     * Return the ham percentage limit for ytbid files
     * 
     * @return the ham percentage limit for ytbid files
     */
    public int getLimitHamPercentageYtbid() {
        return this.limitHamPercentageYtbid;
    }

    /**
     * Stablish the ham percentage limit for ytbid files
     * 
     * @param limitHamPercentageYtbid the ham percentage limit for ytbid files
     */
    public void setLimitHamPercentageYtbid(final int limitHamPercentageYtbid) {
        this.limitHamPercentageYtbid = limitHamPercentageYtbid;
    }

    /**
     * Return the spam percentage limit for twtid files
     * 
     * @return the spam percentage limit for twtid files
     */
    public int getLimitSpamPercentageTwtid() {
        return this.limitSpamPercentageTwtid;
    }

    /**
     * Stablish the spam percentage limit for twtid files
     * 
     * @param limitSpamPercentageTwtid the spam percentage limit for twtid files
     */
    public void setLimitSpamPercentageTwtid(final int limitSpamPercentageTwtid) {
        this.limitSpamPercentageTwtid = limitSpamPercentageTwtid;
    }

    /**
     * Return the ham percentage limit for twtid files
     * 
     * @return the ham percentage limit for twtid files
     */
    public int getLimitHamPercentageTwtid() {
        return this.limitHamPercentageTwtid;
    }

    /**
     * Stablish the ham percentage limit for twtid files
     * 
     * @param limitHamPercentageTwtid the ham percentage limit for twtid files
     */
    public void setLimitHamPercentageTwtid(final int limitHamPercentageTwtid) {
        this.limitHamPercentageTwtid = limitHamPercentageTwtid;
    }

    /**
     * Return true if selected mode is spam false if it's datatype
     * 
     * @return true if selected mode is spam false if it's datatype
     */
    public boolean getSpamMode() {
        return this.spamMode;
    }

    /**
     * Stablish the mode
     * 
     * @param spamMode the mode
     */
    public void setSpamMode(final boolean spamMode) {
        this.spamMode = spamMode;
    }

    /**
     * A toString method for license names
     * 
     * @return String with license name
     */
    public String toStringLicenses() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (this.getLicenses().size() == 0) {
            stringBuilder.append("Not licenses selected");
        } else {
            for (final License license : this.getLicenses()) {
                stringBuilder.append(license.getName() + ", ");
            }
        }
        String ret = stringBuilder.toString();
        if (!ret.isEmpty()){
           ret = ret.substring(0, ret.length() - 2);
        }
        return ret;
    }

    /**
     * A toString method for datatype names
     * 
     * @return a String with datatype names
     */
    public String toStringDatatypes() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (this.getDatatypes().isEmpty()) {
            stringBuilder.append("Not datatypes selected");
        } else {
            for (final Datatype datatype : this.getDatatypes()) {
                stringBuilder.append(datatype.getDatatype()).append(", ");
            }
        }

        String ret = stringBuilder.toString();
        if (!ret.isEmpty()){
           ret = ret.substring(0, ret.length() - 2);
        }
        return ret;
    }

    /**
     * A toString method for language names
     * 
     * @return a toString method for language names
     */
    public String toStringLanguages() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (this.getLanguages().size() == 0) {
            stringBuilder.append("Not languages selected");
        } else {
            for (final Language language : this.getLanguages()) {
                stringBuilder.append(language.getLanguage() + ", ");
            }
        }
        String ret = stringBuilder.toString();
        if (!ret.isEmpty()){
           ret = ret.substring(0, ret.length() - 2);
        }
        return ret;
    }

    /**
     * A toString method for message dates
     * 
     * @return String for message dates
     */
    public String toStringDate() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final StringBuilder stringBuilder = new StringBuilder();

        if (dateTo != null && dateFrom != null) {
            String dateFrom = format.format(this.getDateFrom());
            String dateTo = format.format(this.getDateTo());
            stringBuilder.append(dateFrom).append(" / ").append(dateTo);
        } else {
            stringBuilder.append("Not dates selected");
        }
       return stringBuilder.toString();
    }
}
