package org.strep.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

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
    @OneToOne(fetch= FetchType.LAZY)
    @MapsId("permission_id")
    private Permission permission;

    /**
     * The user who request the permission
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName="username")
    @MapsId("user_username")
    private User user;

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
    public PermissionRequestPK(User user, Permission permission)
    {
        this.user=user;
        this.permission=permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        PermissionRequestPK that = (PermissionRequestPK) o;
        return Objects.equals(getUser(), ((PermissionRequestPK)o).getUser()) &&
               Objects.equals(getPermission(), ((PermissionRequestPK)o).getPermission());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getPermission());
    }

    /**
     * Get the permission requested
     * @return The permission requested
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Stablish the permission
     * @param permission The permission requested
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Gets the user
     * @return The user that requested the permissions
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     * @param user The user that requested the permission
     */
    public void setUser(User user) {
        this.user = user;
    }
}