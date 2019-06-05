package com.project.onlinepreprocessor.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class FileDatatypeTypePK implements Serializable
{

    @NotNull
    @Size(max = 20)
    private String extension;

    @NotNull
    @Size(max = 20)
    private String type;


    public FileDatatypeTypePK()
    {

    }

    public FileDatatypeTypePK(String extension, String type)
    {
        this.extension = extension;
        this.type = type;
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