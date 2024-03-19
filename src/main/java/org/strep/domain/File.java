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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Set;
import java.io.Serializable;
import java.util.Date;
import org.strep.domain.Dataset;

/**
 * JPA Bean for the File objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class File implements Serializable
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The id of the file
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The path of the file
     */
    @Column(length = 255, columnDefinition="VARCHAR(255)")
    private String path;

    /**
     * The type of the file: spam or ham
     */
    @Pattern(regexp = "^(spam|ham)$", message = "The type of the file can only be spam or ham")
    @Column(length = 5, columnDefinition="VARCHAR(5)")
    private String type;

    /**
     * The language of the file
     */
    @Size(min=1, max=4, message="The file language cannot have more than 4 characters")
    @Column(length = 4, columnDefinition="VARCHAR(4)")
    private String language;

    /**
     * The date of the file
     */
    @Basic
    @Temporal(TemporalType.DATE)    
    private Date date;

    /**
     * The extension of the file
     */
    @NotNull(message = "The extension of the file cannot be null")
    @Size(min=1, max=10, message="The datatype name must have beetween 1 and 10 characters")
    @Column(length = 10, columnDefinition="VARCHAR(10)")
    private String extension;

    /**
     * The datasets related with the 
     */
    @ManyToMany(mappedBy = "files")
    private Set<Dataset> datasets;

    /**
     * The default constructor
     */
    public File() {

    }

    /**
     * Returns the extension of the file
     * @return the file extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Stablish the extension of the file
     * @param extension the file extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Returns the path of the file
     * @return the path of the file
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Stablish the path of the file
     * @param path the path of the file
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Returns the type of the file
     * @return the type of the file
     */
    public String getType()
    {
        return type;
    }

    /**
     * Stablish the type of the file
     * @param type the type of the file
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Returns the language of the file
     * @return the language of the file
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Stablish the language of the file
     * @param language the language of the file
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }

    /**
     * Return the date of the file
     * @return the date of the file
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Stablish the date of the file
     * @param date the date of the file
     */
    public void setDate(Date date)
    {
        this.date = date;
    }





}
