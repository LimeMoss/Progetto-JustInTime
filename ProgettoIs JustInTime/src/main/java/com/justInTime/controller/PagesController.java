package com.justInTime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {




    @GetMapping({"/", "/homepage"})
    public String viewHome() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registrazione")
    public String register() {
        return "registrazione";
    }

    
}
