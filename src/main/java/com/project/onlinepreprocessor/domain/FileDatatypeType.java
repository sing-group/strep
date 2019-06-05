package com.project.onlinepreprocessor.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileDatatypeType
{
    private int count;

    @EmbeddedId
    private FileDatatypeTypePK id;

    public FileDatatypeType()
    {

    }

    public FileDatatypeType(int count, FileDatatypeTypePK id)
    {
        this.count = count;
        this.id = id;
    }

    public int getCount()
    {
        return this.count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public FileDatatypeTypePK getId()
    {
        return this.id;
    }

    public void setId(FileDatatypeTypePK id)
    {
        this.id = id;
    }
}