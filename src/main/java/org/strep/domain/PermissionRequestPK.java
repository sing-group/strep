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

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
//import jakarta.persistence.OneToOne;

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
    @ManyToOne(fetch= FetchType.LAZY)
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
