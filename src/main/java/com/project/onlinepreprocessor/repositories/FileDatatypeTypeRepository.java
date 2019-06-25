package com.project.onlinepreprocessor.repositories;

import java.util.ArrayList;
import java.util.Collection;

import com.project.onlinepreprocessor.domain.FileDatatypeType;
import com.project.onlinepreprocessor.domain.FileDatatypeTypePK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for FileDatatypeType objects
 * @author Ismael Vázquez
 */
public interface FileDatatypeTypeRepository extends CrudRepository<FileDatatypeType, FileDatatypeTypePK>
{
    /**
     * Return the count grouped by extension and type for the files of the specified datasets
     * @param datasetNames the names of the datasets
     * @return the count grouped by extension and type for the files of the specified datasets
     */
    @Query(nativeQuery=true, value = "select count(f.id) as count, f.extension as extension, f.type as type from dataset d inner join dataset_files df on d.name=df.dataset_name inner join file f on df.file_id=f.id where d.name in(?1) group by f.extension, f.type")
    public ArrayList<FileDatatypeType> getFilesByExtensionAndType(Collection<String>datasetNames);
}