package org.strep.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.Column;

/**
 * Embeddable key for FileDatatypeType class that stands for a view to see the number of files
 * belonnging to system datasets classified by class, filetype and dataset
 * 
 * Due to the criteria of the counts, a composed primary key is needed
 * 
 * @see FileDataType
 * @author Ismael Vázquez
 * @author José Ramón Méndez
 */
@Embeddable
public class FileDatatypeTypePK implements Serializable
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The extension of the files
     */
    @NotNull
    @Size(min=1, max=10)
    @Column(length = 10, columnDefinition="VARCHAR(10)")
    private String extension;

    /**
     * The type of the files (spam|ham)
     */
    @NotNull
    @Size(max = 5)
    @Column(length = 5, columnDefinition="VARCHAR(5)")
    private String type;

    /**
     * The dataset name
     */
    @NotNull    
    @Size(min=1, max=80)
    @Column(length = 80, columnDefinition="VARCHAR(80)")
    private String dataset;

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

    /**
     * Returns the dataset name
     * @return the dataset name
     */
    public String getDataset() {
        return dataset;
    }

    /**
     * Stablish the dataset name
     * @param dataset the dataset name
     */
    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

}