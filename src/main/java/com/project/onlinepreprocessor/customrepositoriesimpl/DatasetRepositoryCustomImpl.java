package com.project.onlinepreprocessor.customrepositoriesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.project.onlinepreprocessor.customrepositories.DatasetRepositoryCustom;
import com.project.onlinepreprocessor.domain.Dataset;

public class DatasetRepositoryCustomImpl implements DatasetRepositoryCustom 
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Dataset> findFilteredDatasets(ArrayList<String> licenses, ArrayList<String> languages, ArrayList<String> datatypes)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dataset> query = cb.createQuery(Dataset.class);
        Root<Dataset> dataset = query.from(Dataset.class);

        Path<String> licensePath = dataset.get("license").get("name");
        Path<Set<String>> languagesPath = dataset.get("language");

        In<String> inLicenseClause = cb.in(licensePath);

        for(String license : licenses)
        {
            inLicenseClause.value(license);
        }

        if(licenses.size()!=0 && languages.size()!=0)
        {
            query.select(dataset).where(inLicenseClause);
        }
        else
        {
            query.select(dataset);
        }
        
        return entityManager.createQuery(query).getResultList();
    }

    
}