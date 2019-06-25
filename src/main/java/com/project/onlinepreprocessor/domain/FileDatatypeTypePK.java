package com.project.onlinepreprocessor.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Embeddable key for FileDatatypeType class
 */
@Embeddable
public class FileDatatypeTypePK implements Serializable
{

    /**
     * The extension of the files
     */
    @NotNull
    @Size(max = 20)
    private String extension;

    /**
     * The type of the files
     */
    @NotNull
    @Size(max = 20)
    private String type;


    /**
     * The default constructor
     */
    public FileDatatypeTypePK()
    {

    }

    /**
     * Constructor for create instances of the object
     * @param extension the extension of the files
     * @param type the type of the files
     */
    public FileDatatypeTypePK(String extension, String type)
    {
        this.extension = extension;
        this.type = type;
    }

    /**
     * Return the extension of the file
     * @return the extension of the file
     */
    public String getExtension()
    {
        return this.extension;
    }

    /**
     * Stablish the extension of the file
     * @param extension the extension of the file
     */
    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    /**
     * Return the type of the file
     * @return the type of the file
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * Stablish the type of the file
     * @param type the type of the file
     */
    public void setType(String type)
    {
        this.type = type;
    }



}