package com.project.onlinepreprocessor.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class LoginForm
{
    @Size(min=5,max=20,message="Username must have at least 5 characters")
    private String username;

    @Size(min=8, max=30, message="Password must have at least 8 characters")
    private String password;

    public LoginForm()
    {

    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}