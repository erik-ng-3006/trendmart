package com.example.trendmart.data;

import com.example.trendmart.entities.User;
import com.example.trendmart.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final IUserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";

            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            user.setFirstName("The User");
            user.setLastName("User" + i);
            userRepository.save(user);

            System.out.println("Default user created: " + defaultEmail);

        }
    }
}
