package com.project.onlinepreprocessor.forms;

import javax.validation.constraints.Size;

import javax.validation.constraints.Email;

//TODO: drop

/** 
 * Java bean for register form validation
 * @author Ismael Vázquez
 */
public class RegisterForm
{

    /**
     * The username of the user
     */
    @Size(min=5,max=20, message="Username must have at least 5 characters")
    private String username;

    /**
     * The email of the user
     */
    @Email(message="Provide a valid email.")
    @Size(min=15,max=50)
    private String email;

    /**
     * The password of the user
     */
    @Size(min=8, max=30,message="Password must have at least 8 characters")
    private String password;

    /**
     * The name of the user
     */
    @Size(min=1, max=30, message="Name must have at least 1 character")
    private String name;

    /**
     * The surname of the user
     */
    @Size(min=1, max=30, message="Surname must have at least 1 character")
    private String surname;

    /**
     * The default constructor
     */
    public RegisterForm() {
    }

    /**
     * Return the username of the user
     * @return the username of the user
     */
     public String getUsername() {
        return username;
    }

    /**
     * Stablish the username of the user
     * @param username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Stablish the email of the user
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Stablish the password of the user
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Stablish the name of the user
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the surname of the user
     * @return the surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Stablish the surname of the user
     * @param surname the surname of the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

}