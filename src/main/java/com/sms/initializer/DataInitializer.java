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
        // Check if roles are already present in the database
        if (rolesRepository.count() == 0) {
            // Create ADMIN role
            Roles adminRole = new Roles();
            adminRole.setRoleName(env.getProperty("admin.roleName"));
            adminRole.setCreatedAt(LocalDateTime.now());
            adminRole.setCreatedBy("DBA");

            if (personRepository.count() == 0) {
                // Create ADMIN user
                Person admin = new Person();
                admin.setName(env.getProperty("admin.name"));
                admin.setEmail(env.getProperty("admin.email"));
                admin.setConfirmEmail(env.getProperty("admin.email"));
                admin.setMobileNumber(env.getProperty("admin.mobileNumber"));
                admin.setPwd(env.getProperty("admin.password"));
                admin.setConfirmPwd(env.getProperty("admin.password"));
                admin.setRoles(adminRole);
                admin.setCreatedAt(LocalDateTime.now());
                admin.setCreatedBy("DBA");
                personRepository.save(admin);
            }
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
