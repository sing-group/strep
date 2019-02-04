package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import com.project.onlinepreprocessor.domain.User;
import java.util.Set;

@Entity
public class Permission
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PERM_NAME")
    private String name;

    @Column(name = "PERM_DESCRIPTION")
    private String description;

    @ManyToMany(mappedBy="permissions")
    private Set<User> users;

    protected Permission()
    {

    }

    public Permission(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Set<User> getUsers()
    {
        return users;
    }

   
}