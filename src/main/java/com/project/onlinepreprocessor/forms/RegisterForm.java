package com.project.onlinepreprocessor.forms;

import javax.validation.constraints.Size;

import javax.validation.constraints.Email;

public class RegisterForm
{

    @Size(min=5,max=20, message="Username must have at least 5 characters")
    private String username;

    @Email(message="Provide a valid email.")
    @Size(min=15,max=50)
    private String email;

    @Size(min=8, max=30,message="Password must have at least 8 characters")
    private String password;

    @Size(min=1, max=30, message="Name must have at least 1 character")
    private String name;

    
    @Size(min=1, max=30, message="Surname must have at least 1 character")
    private String surname;

    public RegisterForm() {
    }

     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}