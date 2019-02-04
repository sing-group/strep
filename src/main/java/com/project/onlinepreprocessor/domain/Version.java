package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Version
{
    @Id
    private String name;

    private String description;

    private Date date;

    protected Version()
    {

    }

    public Version(String name, String description, Date date)
    {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}