package com.example.trendmart.data;

import com.example.trendmart.entities.Role;
import com.example.trendmart.entities.User;
import com.example.trendmart.repositories.IRoleRepository;
import com.example.trendmart.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_USER", "ROLE_ADMIN");

        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER")
            .stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ROLE_USER not found"));

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";

            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }


            User user = new User();
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("The User");
            user.setLastName("User" + i);
            
            // Create a new HashSet and add the role
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
            
            // Save the user
            User savedUser = userRepository.save(user);
            
            // Add the user to the role's users set
            userRole.getUsers().add(savedUser);
            roleRepository.save(userRole);
            
            System.out.println("Default user created: " + defaultEmail);
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
            .stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@example.com";

            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("The Admin");
            user.setLastName("Admin" + i);
            
            // Create a new HashSet and add the role
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);
            
            // Save the user
            User savedUser = userRepository.save(user);
            
            // Add the user to the role's users set
            adminRole.getUsers().add(savedUser);
            roleRepository.save(adminRole);
            
            System.out.println("Default admin created: " + defaultEmail);
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.forEach(role -> {
            if (roleRepository.findByName(role).isEmpty()) {
                Role newRole = new Role(role);
                newRole.setUsers(new HashSet<>());
                roleRepository.save(newRole);
            }
        });
    }
}
