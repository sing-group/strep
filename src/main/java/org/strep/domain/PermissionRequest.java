package org.strep.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
        this.id = new PermissionRequestPK(user, permission);
    }


    /**
     * Return the user who request the permission
     * @return the user who request the permission
     */
    public User getUser() {
        return this.id.getUser();
    }

    /**
     * Return the permission requested 
     * @return
     */
    public Permission getPermission() {
        return this.id.getPermission();
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PermissionRequest other = (PermissionRequest) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }


}