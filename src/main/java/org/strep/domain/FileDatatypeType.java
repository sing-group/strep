package org.strep.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

/**
 * Determine the number of files included in system datasets classified by the
 * following criteria: dataset, extension and type (spam|ham)
 * 
 * This Entity queries to this view: CREATE OR REPLACE VIEW file_datatype_type
 * AS SELECT dataset.name as dataset, file.extension as extension, file.type as
 * type, count(file.id) as count FROM file,dataset, dataset_files WHERE
 * file.id=dataset_files.file_id and dataset_files.dataset_name=dataset.name and
 * dataset.type='systemdataset' group by file.extension,dataset.name,file.type;
 * 
 * @author Ismael Vázquez
 * @author José Ramón Méndez
 */
@Entity
@Table(name = "file_datatype_type")
@Immutable
@Subselect(
    "SELECT dataset.name as dataset, file.extension as extension, file.type as "
    +"type, count(file.id) as count FROM file,dataset, dataset_files WHERE "
    +"file.id=dataset_files.file_id and dataset_files.dataset_name=dataset.name and "
    +"dataset.type='systemdataset' group by file.extension,dataset.name,file.type"
)
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
     * Return the id of the object
     * @return the id of the object
     */
    public FileDatatypeTypePK getId()
    {
        return this.id;
    }
}