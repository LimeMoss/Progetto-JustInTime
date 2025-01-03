package com.justInTime.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpSession;


@Controller
public class PagesController {

    @GetMapping({"/", "/homepage"})
    public String viewHome(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "homepage";
        }
        return "redirect:/userHomepage";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/homepage"; 
        }
        return "login";
    }

    @GetMapping("/registrazione")
    public String register(HttpSession session) {
        if (SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/homepage";  
        }
        return "registrazione";
    }

    @GetMapping("/achievements")
    public String achievements(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login";  
        }
        return "achievements";
    }

    @GetMapping("/prepartita")
    public String prepartita(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login";  
        }
        return "prepartita";
    }

    @GetMapping("/rules")
    public String rules(HttpSession session) {
        return "rules";
    }

    @GetMapping("/classificaGlobale")
    public String classificaGlobale(HttpSession session) {
        /*if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login";  
        }*/
        return "classificaGlobale";
    }

    @GetMapping("/classificaLocale")
    public String classificaLocale(HttpSession session) {
        /*if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }*/
        return "classificaLocale";
    }

    @GetMapping("/consultazioneProfilo")
    public String consultazioneProfilo(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login";  
        }
        return "consultazioneProfilo";
    }

    @GetMapping("/userHomepage")
    public String userHomepage(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }
        return "userHomepage";
    }

    @GetMapping("/modifyaccount")
    public String modifyaccount(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }
        return "modifyaccount";
    }

    @GetMapping("/endmatch")
    public String endmatch(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }
        return "endmatch";
    }

    @GetMapping("/match")
    public String match(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }
        return "match";
    }

    @GetMapping("/startmatch")
    public String startmatch(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login"; 
        }
        return "startmatch";
    }

    @GetMapping("/feedbacks")
    public String feedback(HttpSession session) {
        if (!SessionUtil.isUtenteLoggato(session)) {
            return "redirect:/login";  
        }
        return "feedback";
    }
}
