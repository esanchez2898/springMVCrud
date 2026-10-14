package org.example.data;

import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadData {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoadData(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        addRolesData();
        addAdminData();
    }

    // ROLE_ADMIN, ROLE_USER

    public void addRolesData() {
        RoleEntity roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        RoleEntity roleUser = roleRepository.findByName("ROLE_USER").orElse(null);

        if (roleAdmin == null) {
            roleRepository.save(roleAdmin);
        }

        if (roleUser == null) {
            roleRepository.save(roleUser);
        }

    }

    public void addAdminData() {
        String email = "erick@gmail.com";
        UserEntity admin = userRepository.findByEmail(email).orElse(null);

        if (admin == null) {
            admin = new UserEntity("Erick", "Sanchez", email, "123", (byte)1);
            userRepository.save(admin);
        }

    }
}
