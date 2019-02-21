package com.project.onlinepreprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PermissionRequestPK implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name="permission_id")
    private Long permission_id;

    @Column(name="user_username")
    private String user_username;

    protected PermissionRequestPK()
    {

    }

    public PermissionRequestPK(Long permission_id, String user_username)
    {
        this.permission_id = permission_id;
        this.user_username = user_username;
    }

    public Long getPermissionId()
    {
        return permission_id;
    }

    public void setId(Long permission_id)
    {
        this.permission_id = permission_id;
    }

    public String getUserUsername()
    {
        return user_username;
    }

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