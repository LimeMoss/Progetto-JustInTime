package com.justInTime.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justInTime.DTO.PlayerRecord;
import com.justInTime.DTO.SinglePlayerDataDTO;
import com.justInTime.service.ClassificaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/classifica")
public class ClassificaController {

    private final ClassificaService classificaService;

    public ClassificaController(ClassificaService classificaService) {
        this.classificaService = classificaService;
    }


    @GetMapping("/locale")
    public List<PlayerRecord> getClassificaLocale(HttpSession session) {
        return classificaService.getClassificaLocale(session);
    }



    @GetMapping
    public List<PlayerRecord> getClassifica() {
        return classificaService.getClassifica();
    }

    @GetMapping("/singlePlayerData")
    public SinglePlayerDataDTO getSinglePlayerRecord(HttpSession session) {
        return classificaService.getSinglePlayer(session);
    }

}
