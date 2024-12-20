package com.justInTime.controller;

import com.justInTime.model.Player;
import com.justInTime.service.ClassificaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classifica")
public class ClassificaController {

    private final ClassificaService classificaService;

    public ClassificaController(ClassificaService classificaService) {
        this.classificaService = classificaService;
    }

    /**
     * Endpoint per ottenere la classifica dei giocatori ordinata per maxScore.
     *
     * @return Lista di giocatori ordinata.
     */
    @GetMapping
    public List<Player> getClassifica() {
        return classificaService.getClassifica();
    }
}
