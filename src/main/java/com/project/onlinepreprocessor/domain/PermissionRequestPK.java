package com.project.onlinepreprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Embeddable
public class PermissionRequestPK implements Serializable
{
    /**
     * Number of version of the serializable class
     */
    private static final long serialVersionUID = 1L;

    /**
     * The permission id
     */
    @Column(name="permission_id")
    private Long permission_id;

    /**
     * The user who request the permission
     */
    @Column(name="user_username")
    private String user_username;

    /**
     * The default constructor
     */
    protected PermissionRequestPK()
    {

    }

    /**
     * Creates an instance of PermissionRequestPK
     * @param permission_id the permission id
     * @param user_username the user who request the permission
     */
    public PermissionRequestPK(Long permission_id, String user_username)
    {
        this.permission_id = permission_id;
        this.user_username = user_username;
    }

    /**
     * Returns the permission id
     * @return the permission id
     */
    public Long getPermissionId()
    {
        return permission_id;
    }

    /**
     * Stablis the id of the permission
     * @param permission_id the id of the permission
     */
    public void setId(Long permission_id)
    {
        this.permission_id = permission_id;
    }

    /**
     * Return the user who request the permission
     * @return the user who request the permission
     */
    public String getUserUsername()
    {
        return user_username;
    }

    /**
     * Stablish the user who request the permission
     * @param user_username the user who request the permission
     */
    public void setUserUsername(String user_username)
    {
        this.user_username = user_username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        PermissionRequestPK that = (PermissionRequestPK) o;
        return Objects.equals(permission_id, that.permission_id) &&
               Objects.equals(user_username, that.user_username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(permission_id, user_username);
    }
}