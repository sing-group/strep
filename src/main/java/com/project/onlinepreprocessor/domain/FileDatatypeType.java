package com.project.onlinepreprocessor.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FileDatatypeType
{
    @Id
    private int count;

    private String extension;

    private String type;

    public FileDatatypeType()
    {

    }

    public FileDatatypeType(int count, String extension, String type)
    {
        this.count = count;
        this.extension = extension;
        this.type = type;
    }

    public int getCount()
    {
        return this.count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getExtension()
    {
        return this.extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}