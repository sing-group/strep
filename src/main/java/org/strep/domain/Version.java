package org.strep.domain;

import javax.persistence.*;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;

/**
 * JPA Bean for the Version objects managed by application
 * @author Ismael Vázqez
 * @author José Ramón Méndez
 */
@Entity
public class Version
{
    /**
     * The name of the version
     */
    @Id
    @NotNull(message = "Name of the version cannot be null")
    @Size(min=1, max=50, message="The name of the version must have beetween 1 and 50 characters")
    @Column(length = 50, columnDefinition="VARCHAR(50)")
    private String name;

    /**
     * The description of the version
     */
    @Lob
    private String description;

    /**
     * The date of the version
     */
    private Date date;

    /**
     * The default constructor of the version
     */
    protected Version()
    {

    }

    /**
     * Create instances of the version
     * @param name the name of the version
     * @param description the description of the version
     * @param date the date of the version
     */
    public Version(String name, String description, Date date)
    {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    /**
     * Return the name of the version
     * @return the name of the version
     */
    public String getName()
    {
        return name;
    }

     /**
      * Stablish the name of the version
      * @param name the name of the version
      */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return the description of the version
     * @return the description of the version
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Stablish the description of the version
     * @param description the description of the version
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Return the date of the version
     * @return the date of the version
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Stablish the date of the version
     * @param date the date of the version
     */
    public void setDate(Date date)
    {
        this.date = date;
    }
}