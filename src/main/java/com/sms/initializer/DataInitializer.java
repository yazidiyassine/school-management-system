package com.sms.initializer;

import com.sms.model.Person;
import com.sms.model.Roles;
import com.sms.repository.PersonRepository;
import com.sms.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolesRepository rolesRepository;

    private final PersonRepository personRepository;

    private final Environment env;

    @Autowired
    public DataInitializer(PersonRepository personRepository, RolesRepository rolesRepository, Environment env) {
        this.personRepository = personRepository;
        this.rolesRepository = rolesRepository;
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if ADMIN role is already present in the database
        Roles adminRole = rolesRepository.getByRoleName("ADMIN");
        // Check if ADMIN user is already present in the database
        Person adminUser = personRepository.readByEmail(env.getProperty("admin.email"));

        if (adminRole == null && adminUser == null) {
            // Create ADMIN role
            adminRole = new Roles();
            adminRole.setRoleName(env.getProperty("admin.roleName"));
            adminRole.setCreatedAt(LocalDateTime.now());
            adminRole.setCreatedBy("DBA");
            adminUser = new Person();
            adminUser.setName(env.getProperty("admin.name"));
            adminUser.setEmail(env.getProperty("admin.email"));
            adminUser.setConfirmEmail(env.getProperty("admin.email"));
            adminUser.setMobileNumber(env.getProperty("admin.mobileNumber"));
            adminUser.setPwd(env.getProperty("admin.password"));
            adminUser.setConfirmPwd(env.getProperty("admin.password"));
            adminUser.setRoles(adminRole);
            adminUser.setCreatedAt(LocalDateTime.now());
            adminUser.setCreatedBy("DBA");
            personRepository.save(adminUser);
            rolesRepository.save(adminRole);
        }

        // Create STUDENT role if it does not exist
        Roles studentRole = rolesRepository.getByRoleName("STUDENT");
        if (studentRole == null) {
            studentRole = new Roles();
            studentRole.setRoleName("STUDENT");
            studentRole.setCreatedAt(LocalDateTime.now());
            studentRole.setCreatedBy("DBA");
            rolesRepository.save(studentRole);
        }
    }
}
