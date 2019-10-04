package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Set;
import java.util.Date;
import com.project.onlinepreprocessor.domain.Dataset;

/**
 * JPA Bean for the File objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class File
{
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
    private String type;

    /**
     * The language of the file
     */
    @Size(min=1, max=4, message="The dataset language cannot have more than 4 characters")
    @Column(length = 4, columnDefinition="VARCHAR(4)")
    private String language;

    /**
     * The date of the file
     */
    private Date date;

    /**
     * The extension of the file
     */
    @NotNull(message = "The extension of the file cannot be null")
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