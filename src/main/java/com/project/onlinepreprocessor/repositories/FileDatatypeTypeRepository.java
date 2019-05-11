package com.project.onlinepreprocessor.repositories;

import java.util.ArrayList;
import java.util.Collection;

import com.project.onlinepreprocessor.domain.FileDatatypeType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FileDatatypeTypeRepository extends CrudRepository<FileDatatypeType, Integer>
{
    @Query(nativeQuery=true, value = "select count(f.id) as count, f.extension as extension, f.type as type from dataset d inner join dataset_files df on d.name=df.dataset_name inner join file f on df.file_id=f.id where d.name in(?1) group by f.extension, f.type")
    public ArrayList<FileDatatypeType> getFilesByExtensionAndType(Collection<String>datasetNames);
}