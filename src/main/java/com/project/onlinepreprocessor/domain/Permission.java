package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import com.project.onlinepreprocessor.domain.User;
import java.util.Set;

/**
 * JPA Bean for the Permission objects managed by application
 * @author Ismael Vázqez
 */
@Entity
public class Permission
{
    /**
     * The id of the permission
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the permission
     */
    private String name;

    /**
     * The description of the permission
     */
    private String description;

    /**
     * The users wich have the permission
     */
    @ManyToMany(mappedBy="permissions")
    private Set<User> users;

    /**
     * The default constructor
     */
    protected Permission()
    {

    }

    /**
     * Creates an instance of the permission
     * @param name the name of the permission
     * @param description the description of the permission
     */
    public Permission(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    /**
     * Return the name of the permission
     * @return the name of the permission
     */
    public String getName()
    {
        return name;
    }

    /**
     * Stablish the name of the permission
     * @param name the name of the permission
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return the description of the permission
     * @return the description of the permission
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Stablish the description of the permission
     * @param description the description of the permission
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Return the users wich have the permission
     * @return the users wich have the permission
     */
    public Set<User> getUsers()
    {
        return users;
    }

    /**
     * Stablish the users wich have the permission
     * @param users the users wich have the permission
     */
    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

   
}