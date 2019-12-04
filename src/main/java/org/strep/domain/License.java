package org.strep.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JPA Bean for the Dataset objects managed by application
 *
 * @author Ismael Vázquez
 */
@Entity
public class License implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the license
     */
    @Id
    @NotNull(message = "Name of the license cannot be null")
    @Size(min = 1, max = 100)
    @Column(length = 100, columnDefinition = "VARCHAR(100)")
    private String name;

    /**
     * The legal code of the license
     */
    @Lob
    private byte[] description;

    /**
     * The url to the original source of the license
     */
    @NotNull(message = "License URL cannot be null")
    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String url;

    /**
     * Stablish if you can copy and redistribute the work
     */
    private boolean redistribute;

    /**
     * Stablish if to attribute the author is required
     */
    private boolean attributeRequired;

    /**
     * Stablish if you can use the work with commercially purposes
     */
    private boolean commerciallyUse;

    /**
     * Stablish if you can adapt the work
     */
    private boolean adaptWork;

    /**
     * Stablish if you can change the licence when redistributing?
     */
    private boolean changeLicense;

    /**
     * Creates an instance of the license
     *
     * @param name the name of the license
     * @param description the legal code of the license
     * @param url the url to the original source of the license
     */
    public License(String name, byte[] description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    /**
     * The default constructor
     */
    public License() {

    }

    /**
     * Return the name of the license
     *
     * @return the name of the license
     */
    public String getName() {
        return name;
    }

    /**
     * Stablish the name of the license
     *
     * @param name the name of the license
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the legal code of the license
     *
     * @return
     */
    public byte[] getDescription() {
        return description;
    }

    /**
     * Stablish the legal code of the license
     *
     * @param description the legal code of the license
     */
    public void setDescription(byte[] description) {
        this.description = description;
    }

    /**
     * Return the url to the original source of the license
     *
     * @return the url to the original source of the license
     */
    public String getUrl() {
        return url;
    }

    /**
     * Stablish the url to the original source of the license
     *
     * @param url the url to the original source of the license
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return the redistribute value of the license
     *
     * @return the redistribute value of the license
     */
    public boolean isRedistribute() {
        return redistribute;
    }

    /**
     * Stablish the redistribute value of the license
     *
     * @param redistribute the redistribute value of the license
     */
    public void setRedistribute(boolean redistribute) {
        this.redistribute = redistribute;
    }

    /**
     * Return the attribute required value of the license
     *
     * @return the c value of the license
     */
    public boolean isAttributeRequired() {
        return attributeRequired;
    }

    /**
     * Stablish the attribute required value of the license
     *
     * @param attributeRequired the attribute required value of the license
     */
    public void setAttributeRequired(boolean attributeRequired) {
        this.attributeRequired = attributeRequired;
    }

    /**
     * Return the commercially use value of the license
     *
     * @return the commercially use value of the license
     */
    public boolean isCommerciallyUse() {
        return commerciallyUse;
    }

    /**
     * Stablish the commercially use value of the license
     *
     * @param commerciallyUse the commercially use value of the license
     */
    public void setCommerciallyUse(boolean commerciallyUse) {
        this.commerciallyUse = commerciallyUse;
    }

    /**
     * Return the adapt work value of the license
     *
     * @return the adapt work value of the license
     */
    public boolean isAdaptWork() {
        return adaptWork;
    }

    /**
     * Stablish the adapt work use value of the license
     *
     * @param adaptWork the adapt work use value of the license
     */
    public void setAdaptWork(boolean adaptWork) {
        this.adaptWork = adaptWork;
    }

    /**
     * Return the change license value of the license
     *
     * @return the change license use value of the license
     */
    public boolean isChangeLicense() {
        return changeLicense;
    }

    /**
     * Stablish the change license use value of the license
     *
     * @param changeLicense the change license use value of the license
     */
    public void setChangeLicense(boolean changeLicense) {
        this.changeLicense = changeLicense;
    }

}
