package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class RoleDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Size(max = 100, min = 5, message = "The size has to be between 5 and 100")
    @NotEmpty(message = "roleName can not be empty")
    private String roleName;

    @NotNull
    @Positive
    private List<Integer> usersIds;

    public RoleDto() {
    }

    public RoleDto(Integer id, String roleName, List<Integer> usersIds) {
        this.id = id;
        this.roleName = roleName;
        this.usersIds = usersIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(List<Integer> usersIds) {
        this.usersIds = usersIds;
    }
}
