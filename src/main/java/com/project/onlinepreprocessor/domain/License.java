package com.project.onlinepreprocessor.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class License
{
    @Id
    @NotNull(message = "Name of the license cannot be null")
    private String name;

    @Lob
    @Size(message = "The description of the license must have at least 100 characters", min=100)
    private byte[] description;

    @NotNull(message = "License url cannot be null")
    private String url;

    public License(String name, byte[] description, String url)
    {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public License()
    {
        
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getDescription()
    {
        return description;
    }

    public void setDescription(byte[] description)
    {
        this.description = description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


}