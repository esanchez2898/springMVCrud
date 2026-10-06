package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotEmpty
    @Size(max = 50)
    private String firstname;

    @NotEmpty
    @Size(max = 50)
    private String lastname;

    @NotEmpty
    @Email(regexp = ".+[@].+[\\.].+")
    @Size(max = 100)
    private String email;

    @NotEmpty
    @Size(max = 50)
    private String password;

    private byte enabled;

    private List<Integer> rolesIds;

    private String authToken;

    public UserDto() {
    }

    public UserDto(Integer id, String firstname, String lastname, String email, String password, byte enabled, List<Integer> rolesIds, String authToken) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.rolesIds = rolesIds;
        this.authToken = authToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public byte getEnabled() {
        return enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public List<Integer> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Integer> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
