package com.project.onlinepreprocessor.customrepositories;

import java.util.ArrayList;
import java.util.List;

import com.project.onlinepreprocessor.domain.Dataset;

public interface DatasetRepositoryCustom
{
    public List<Dataset> findFilteredDatasets(ArrayList<String> license, ArrayList<String> languages, ArrayList<String> datatypes);
}