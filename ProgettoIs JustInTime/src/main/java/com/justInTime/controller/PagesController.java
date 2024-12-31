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

    @GetMapping("/achievements")
    public String achievements() {
        return "achievements";
    }

    @GetMapping("/prepartita")
    public String prepartita() {
        return "prepartita";
    }

    @GetMapping("/rules")
    public String rules() {
        return "rules";
    }

    @GetMapping("/classificaGlobale")
    public String classificaGlobale() {
        return "classificaGlobale";
    }

    @GetMapping("/classificaLocale")
    public String classificaLocale() {
        return "classificaLocale";
    }

    @GetMapping("/consultazioneProfilo")
    public String consultazioneProfilo() {
        return "consultazioneProfilo";
    }

    @GetMapping("/userHomepage")
    public String userHomepage() {
        return "userHomepage";
    }

    @GetMapping("/modifyaccount") //??//
    public String modifyaccount() {
        return "modifyaccount";
    }
    @GetMapping("/endmatch")
    public String endmatch() {
        return "endmatch";
    }

    @GetMapping("/match")
    public String match() {
        return "match";
    }

    @GetMapping("/startmatch")
    public String startmatch() {
        return "startmatch";
    }

    @GetMapping("/feedbacks")
    public String feedback() {
        return "feedback";
    }

}
