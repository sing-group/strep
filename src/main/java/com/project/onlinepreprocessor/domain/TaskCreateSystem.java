package com.project.onlinepreprocessor.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TaskCreateSystem extends Task
{

    public TaskCreateSystem(Dataset dataset, String state, String message)
    {
        super(dataset, state, message);
    }

    public TaskCreateSystem()
    {
        super();
    }
}