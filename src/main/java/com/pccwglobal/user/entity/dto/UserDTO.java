package com.pccwglobal.user.entity.dto;

import com.pccwglobal.email.intreface.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @UniqueEmail
    private String email;

    private boolean active;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

