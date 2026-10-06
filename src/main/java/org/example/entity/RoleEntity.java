package org.example.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private List<UserEntity> users;

    @Override
    public @Nullable String getAuthority() {
        return roleName;
    }

    public RoleEntity() {
    }

    public RoleEntity(Integer id, String roleName, List<UserEntity> users) {
        this.id = id;
        this.roleName = roleName;
        this.users = users;
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

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
