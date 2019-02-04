package com.project.onlinepreprocessor.domain;

import javax.persistence.*;
import com.project.onlinepreprocessor.domain.Permission;
import java.util.Set;

@Entity
public class User
{
    @Id
    private String username;

    @Column(unique=true)
    private String email;

    private String password;

    private String name;

    private String surname;

    private boolean confirmedAccount;

    private int hash;

    private byte[] photo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_perm", joinColumns= @JoinColumn(name = "user", referencedColumnName = "username"), 
    inverseJoinColumns = @JoinColumn(name="perm_id", referencedColumnName="id"))
    private Set<Permission> permissions;

    protected User()
    {

    }

    public User(String username, String email,int hash, String password, String name, String surname)
    {
        this.username = username;
        this.email = email;
        this.hash = hash;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname()
    {
        this.surname = surname;
    }

    public byte[] getPhoto()
    {
        return photo;
    }

    public void setPhoto(byte[] photo)
    {
        this.photo = photo;
    }

    
    public Set<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }

    public boolean getConfirmedAccount()
    {
        return confirmedAccount;
    }

    public void setConfirmedAccount(boolean confirmedAccount)
    {
        this.confirmedAccount = confirmedAccount;
    }

    public int getHash()
    {
        return hash;
    }

    public void setHash(int hash)
    {
        this.hash = hash;
    }
}