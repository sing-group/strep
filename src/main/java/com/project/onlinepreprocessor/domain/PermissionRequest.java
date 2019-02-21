package com.project.onlinepreprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class PermissionRequest implements Serializable
{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PermissionRequestPK id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("user_username")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("permission_id")
    private Permission permission;


    private String status;

    protected PermissionRequest()
    {

    }

    public PermissionRequest(User user, Permission permission)
    {
        this.user = user;
        this.permission = permission;
        this.id = new PermissionRequestPK(permission.getId(), user.getUsername());
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PermissionRequestPK getId()
    {
        return this.id;
    }

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