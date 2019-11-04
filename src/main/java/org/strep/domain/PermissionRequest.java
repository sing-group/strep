package org.strep.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
public class PermissionRequest implements Serializable
{
    /**
     * Version number of the serializable class
     */
    private static final long serialVersionUID = 1L;

    /**
     * The composed primary key for the permission request
     */
    @EmbeddedId
    private PermissionRequestPK id;

    /**
     * The user who request the permission
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName="username")
    @MapsId("user_username")
    private User user;

    /**
     * The permission id
     */
    @OneToOne(fetch= FetchType.LAZY)
    @MapsId("permission_id")
    private Permission permission;


    /**
     * The state of the request
     */
    @NotNull
    @Column(length = 9, columnDefinition="VARCHAR(9)")
    @Pattern(regexp = "^(pending|accepted)$", message = "The status can be pending or accepted")
    private String status;

    /**
     * The default constructor
     */
    protected PermissionRequest()
    {
    }

    /**
     * Creates an instance of permission request
     * @param user the user who request the permission
     * @param permission the requested permission
     */
    public PermissionRequest(User user, Permission permission)
    {
        this.user = user;
        this.permission = permission;
        this.id = new PermissionRequestPK(permission.getId(), user.getUsername());
    }


    /**
     * Return the user who request the permission
     * @return the user who request the permission
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Stablish the user who request the permission
     * @param user the user who request the permission
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Return the permission requested 
     * @return
     */
    public Permission getPermission() {
        return this.permission;
    }

    /**
     * Stablish the permission requested
     * @param permission the permission requested
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Return the state of the permission request
     * @return the state of the permission request
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Stablish the state of the permission request
     * @param status the state of the permission request
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Return the composed key of the permission request
     * @return the composed key of the permission request
     */
    public PermissionRequestPK getId()
    {
        return this.id;
    }

    /**
     * Stablish the composed key of the permission request
     * @param id the composed key of the permission request
     */
    public void setId(PermissionRequestPK id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        PermissionRequest that = (PermissionRequest) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(permission, that.permission);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, permission);
    }

}