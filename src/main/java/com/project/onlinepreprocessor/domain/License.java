package com.project.onlinepreprocessor.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class License
{
    @Id
    private String name;

    @Lob
    private byte[] description;

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