package com.project.onlinepreprocessor.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TaskCreateSdataset extends Task
{

    public TaskCreateSdataset(Dataset dataset, String state, String message)
    {
        super(dataset, state, message);
    }

    public TaskCreateSdataset()
    {
        super();
    }
}