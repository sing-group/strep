/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.domain;

import javax.persistence.*;
import org.strep.domain.User;

import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * JPA Bean for the Permission objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class Permission implements Serializable
{
    /**
     * View permission
     */
    public static final String VIEW = "View";
    
    /**
     * Create corpus permission
     */
    public static final String CREATE_CORPUS = "CreateCorpus";
    
    /**
     * Upload permission
     */
    public static final String UPLOAD = "Upload";
    
    /**
     * Administer permission
     */
    public static final String ADMINISTER = "Administer";
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    
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
