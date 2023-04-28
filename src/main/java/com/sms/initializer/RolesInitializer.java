package com.sms.initializer;


import com.sms.model.Roles;
import com.sms.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RolesInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles are already present in the database
        if (rolesRepository.count() == 0) {
            // Create ADMIN role
            Roles adminRole = new Roles();
            adminRole.setRoleName("ADMIN");
            adminRole.setCreatedAt(LocalDateTime.now());
            adminRole.setCreatedBy("DBA");
            rolesRepository.save(adminRole);

            // Create STUDENT role
            Roles studentRole = new Roles();
            studentRole.setRoleName("STUDENT");
            studentRole.setCreatedAt(LocalDateTime.now());
            studentRole.setCreatedBy("DBA");
            rolesRepository.save(studentRole);
        }
    }
}
