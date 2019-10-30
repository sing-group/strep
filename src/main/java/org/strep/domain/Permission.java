package org.strep.domain;

import javax.persistence.*;
import org.strep.domain.User;

import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * JPA Bean for the Permission objects managed by application
 * @author Ismael Vázquez
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
    @NaturalId
    private String name;

    /**
     * The description of the permission
     */
    private String description;

    /**
     * The users wich have the permission
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    /**
     * The permission requests associated to this permission
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PermissionRequest> permissionRequests = new HashSet<PermissionRequest>();

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
     * Return the id of the permission
     * @return the id of the permission
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Stablish the id of the permission
     * @param id the id of the permission
     */
    public void setId(Long id)
    {
        this.id = id;
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

    /**
     * Return the users wich have the permission
     * @return the users wich have the permission
     */
    public Set<PermissionRequest> getPermissionRequests()
    {
        return permissionRequests;
    }

    /**
     * Stablish the users wich have the permission
     * @param users the users wich have the permission
     */
    public void setPermissionRequests(Set<PermissionRequest> permissionRequests)
    {
        this.permissionRequests = permissionRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission permission = (Permission) o;
        return Objects.equals(id, permission.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
   
}