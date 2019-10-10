package org.strep.forms;

import javax.validation.constraints.Size;
//TODO: Drop
/**
 * Java bean for login form validation
 * @author Ismael Vázquez
 */
public class LoginForm
{
    /**
     * The username of the user
     */
    @Size(min=5,max=20,message="Username must have at least 5 characters")
    private String username;

    /**
     * The password of the user
     */
    @Size(min=8, max=30, message="Password must have at least 8 characters")
    private String password;

    /**
     * The default constructor
     */
    public LoginForm()
    {

    }

    /**
     * Return the username
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Return the password
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Stablish the username
     * @param username the username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Stablish the password
     * @param password the password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}