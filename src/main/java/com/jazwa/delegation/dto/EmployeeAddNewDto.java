package com.jazwa.delegation.dto;

import com.jazwa.delegation.model.Department;

import javax.validation.constraints.*;

public class EmployeeAddNewDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String position;
    @Email(message = "Invalid email format")
    private String email;
    @Min(value = 1)
    private Integer departmentId;
    @NotBlank
    private String role;
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 2,max = 32)
    private String password;

    public EmployeeAddNewDto() {
    }

    public EmployeeAddNewDto(String firstName, String lastName, String position, String email, Integer departmentId, String role, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.departmentId = departmentId;
        this.role = role;
        this.username = username;
        this.password = password;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
