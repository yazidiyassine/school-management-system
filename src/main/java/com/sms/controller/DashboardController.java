package com.sms.controller;

import com.sms.model.Person;
import com.sms.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    private final PersonRepository personRepository;

    public DashboardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        session.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }

}
