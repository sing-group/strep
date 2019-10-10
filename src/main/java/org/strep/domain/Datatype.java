package org.strep.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class Datatype
{
     /**
      * The name of the datatype
      */
    @Id
    @NotNull
    @Size(min=1, max=10, message="The datatype name must have beetween 1 and 10 characters")
    @Column(length = 10, columnDefinition="VARCHAR(10)")
    private String datatype;

    /**
     * Datasets associated to this datatype
     */
    @ManyToMany(mappedBy="datatypes")
    private Set<Dataset> datasets;

    /**
     * The default constructor
     */
    protected Datatype()
    {

    }

    /**
     * Return the datasets associated to this datatype
     * @return datasets associated to this datatype
     */
    public Set<Dataset> getDatasets()
    {
        return this.datasets;
    }

    /**
     * Stablish the datasets associated to this datatype
     * @param datasets datasets associated to this datatype
     */
    public void setDatasets(Set<Dataset> datasets)
    {
        this.datasets = datasets;
    }

    /**
     * Return the name of the datatype
     * @return the name of the datatype
     */
    public String getDatatype()
    {
        return this.datatype;
    }

    /**
     * Stablish the name of the datatype
     * @param datatype the name of the datatype
     */
    public void setDatatype(String datatype)
    {
        this.datatype = datatype;
    }
}