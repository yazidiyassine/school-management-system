package com.sms.controller;

import com.sms.model.Person;
import com.sms.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    private final PersonRepository personRepository;

    private final Environment environment;

    public DashboardController(PersonRepository personRepository, Environment environment) {
        this.personRepository = personRepository;
        this.environment = environment;
    }

    @Value("${sms.pageSize}")
    private int defaultPageSize;

    @Value("${sms.successMsg}")
    private String successMsg;


    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(null !=person.getEazyClass() && null != person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        logMessages();
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.info("defaultPageSize value with @Value is : "+defaultPageSize);
        log.info("successMsg value with @Value is : "+successMsg);

        log.info("defaultPageSize value with Environment is : "+environment.getProperty("sms.pageSize"));
        log.info("successMsg value with Environment is : "+environment.getProperty("sms.contact.successMsg"));
        log.info("successMsg value with Environment is : "+environment.getProperty("JAVA_HOME"));
    }
}
