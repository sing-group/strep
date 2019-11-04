package org.strep.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
/**
 * JPA bean artificial entity to project queries, this table doesn't save objects
 * @author Ismael Vázquez
 */
@Entity
public class FileDatatypeType implements Serializable
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Number of files with the specified extension an type
     */
    private int count;

    /**
     * A combined key for the object
     */
    @EmbeddedId
    private FileDatatypeTypePK id;

    /**
     * The default constructor
     */
    public FileDatatypeType()
    {

    }

    /**
     * Constructor for create instances of this object
     * @param count the number of files with the specified extension and type
     * @param id the id of the object
     */
    public FileDatatypeType(int count, FileDatatypeTypePK id)
    {
        this.count = count;
        this.id = id;
    }

    /**
     * Return the count of the object
     * @return the count of the object
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * Stablish the count of the object
     * @param count the count of the object
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * Return the id of the object
     * @return the id of the object
     */
    public FileDatatypeTypePK getId()
    {
        return this.id;
    }

    /**
     * Stablish the id of the object
     * @param id the id of the object
     */
    public void setId(FileDatatypeTypePK id)
    {
        this.id = id;
    }
}