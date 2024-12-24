package com.justInTime.controller;

import com.justInTime.service.UtenzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.justInTime.model.Utente;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UtenzaService utenzaService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String usernameOrEmail,
                               @RequestParam String password,
                               Model model) {
        try {
            Utente utente = utenzaService.login(usernameOrEmail, password);
            model.addAttribute("utente", utente);
            return "redirect:/userHomepage";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/login";
        }
    }


    @GetMapping("/userHomepage")
    public String userHomepage() {
        return "userHomepage";
    }
}
