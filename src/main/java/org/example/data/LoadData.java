package org.example.data;

import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.exception.exceptions.InstanceNotFoundException;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadData {// implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoadData(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        addRolesData();
        addAdminData();
    }

//    @Override
//    public void run(String... args) {
//        addRolesData();
//        addAdminData();
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        addRolesData();
//        addAdminData();
//    }




    public void addRolesData() {

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            RoleEntity roleAdmin = new RoleEntity("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            RoleEntity  roleUser = new RoleEntity("ROLE_USER");
            roleRepository.save(roleUser);
        }

    }

    public void addAdminData() {
        String email = "erick@gmail.com";

        if (userRepository.findByEmail(email).isEmpty()) {

            String password = passwordEncoder.encode("123");

            UserEntity admin = new UserEntity("Erick", "Sanchez", email, password, (byte)1);
            RoleEntity roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new InstanceNotFoundException("Role was not found"));

            List<RoleEntity> roles = new ArrayList<>();
            roles.add(roleAdmin);

            admin.setRoles(roles);
            userRepository.save(admin);
        }

    }



}
