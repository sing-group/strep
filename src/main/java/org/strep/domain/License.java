package org.strep.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class License
{
    /**
     * The name of the license
     */
    @Id
    @NotNull(message = "Name of the license cannot be null")
    @Size(min=1, max=100, message="Name must have beetween 1 and 100 characters")
    @Column(length = 100, columnDefinition="VARCHAR(100)")    
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
    @Column(length = 255, columnDefinition="VARCHAR(255)")
    private String url;

    /**
     * Stablish the restriction level of the license
     */
    private int restrictionLevel;

    /**
     * Creates an instance of the license
     * @param name the name of the license
     * @param description the legal code of the license
     * @param url the url to the original source of the license
     */
    public License(String name, byte[] description, String url)
    {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    /**
     * The default constructor
     */
    public License()
    {
        
    }

    /**
     * Return the name of the license
     * @return the name of the license
     */
    public String getName()
    {
        return name;
    }

    /**
     * Stablish the name of the license
     * @param name the name of the license
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return the legal code of the license
     * @return
     */
    public byte[] getDescription()
    {
        return description;
    }

    /**
     * Stablish the legal code of the license
     * @param description the legal code of the license
     */
    public void setDescription(byte[] description)
    {
        this.description = description;
    }

    /**
     * Return the url to the original source of the license
     * @return the url to the original source of the license
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Stablish the url to the original source of the license
     * @param url the url to the original source of the license
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * Returns the restriction level of the license
     * @return
     */
    public int getRestrictionLevel()
    {
        return this.restrictionLevel;
    }

    /**
     * Stablish the restriction level of the license
     * @param restrictionLevel
     */
    public void setRestrictionLevel(int restrictionLevel)
    {
        this.restrictionLevel = restrictionLevel;
    }


}