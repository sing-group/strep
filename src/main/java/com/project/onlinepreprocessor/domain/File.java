package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import java.util.Set;
import java.util.Date;
import com.project.onlinepreprocessor.domain.Dataset;

@Entity
public class File
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String path;

    //Spam or ham
    private String type;

    private String language;

    private Date date;

    //Extension of the file .tsms, .ytbid, etc
    private String extension;

    @ManyToMany(mappedBy="files")
    private Set<Dataset> datasets;

    public File()
    {

    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
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