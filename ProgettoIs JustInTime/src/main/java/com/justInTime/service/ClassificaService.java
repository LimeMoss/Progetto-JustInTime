package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.justInTime.model.Player;
import com.justInTime.model.PlayerRecord;
import com.justInTime.model.Utente;
import com.justInTime.repository.PlayerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ClassificaService {

    private final PlayerRepository playerRepository;

    public ClassificaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public List<PlayerRecord> getClassificaLocale(HttpSession Session) {
        List<Player> players = playerRepository.findAllPlayersOrderByCountryAndMaxScore();

        List<PlayerRecord> records = new ArrayList<>();

        Utente utente = (Utente) Session.getAttribute("utente");
        for (Player player : players) {
            if (player.getUtente().getPaese().equals(utente.getPaese())) {
                PlayerRecord record = new PlayerRecord(
                        player.getUtente().getPaese(),
                        player.getNome(),
                        player.getMaxScore());

                records.add(record);
            }

        }

        return records;

    }

    public List<PlayerRecord> getClassifica() {
        List<Player> players = playerRepository.findAllPlayersOrderByCountryAndMaxScore();

        List<PlayerRecord> records = new ArrayList<>();

        /*// Log per debug
        System.out.println("Player trovati nel repository:");
        players.forEach(player -> System.out.println(player.getNome() + " - " + player.getMaxScore()));

        System.out.println("JSON restituito: " + records);*/

        for (Player player : players) {
            PlayerRecord record = new PlayerRecord(
                    player.getUtente().getPaese(),
                    player.getNome(),
                    player.getMaxScore());
            records.add(record);
        }

        return records;

    }
}
