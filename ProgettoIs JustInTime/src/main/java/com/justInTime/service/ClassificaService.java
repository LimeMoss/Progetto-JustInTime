package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.DTO.PlayerRecord;
import com.justInTime.DTO.SinglePlayerDataDTO;
import com.justInTime.model.Player;
import com.justInTime.model.Utente;
import com.justInTime.repository.PlayerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ClassificaService {
    @Autowired
    private PlayerRepository playerRepository;

    public List<PlayerRecord> getClassificaLocale(HttpSession Session) {
        List<Player> players = playerRepository.findAllPlayersOrderByCountryAndMaxScore();

        List<PlayerRecord> records = new ArrayList<>();

        Utente utente = (Utente) Session.getAttribute("utente");
        for (Player player : players) {
            if (player.getUtente().getPaese().equals(utente.getPaese())) {
                PlayerRecord record = new PlayerRecord(
                        player.getUtente().getPaese(),
                        player.getUtente().getUsername(),
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
                    player.getUtente().getUsername(),
                    player.getMaxScore());
            records.add(record);
        }

        return records;

    }

 

    /**
     * Ritorna i dati del giocatore loggato, se presente fra quelli presenti nel sistema.
     * @param session l'oggetto HttpSession del giocatore loggato
     * @return un oggetto SinglePlayerDataDTO contenente i dati del giocatore,
     *         o null se non viene trovato alcun giocatore con lo username corrispondente
     */
     public SinglePlayerDataDTO getSinglePlayer(HttpSession session) {
        List<Player> players = playerRepository.findAllPlayersOrderByCountryAndMaxScore();
    
        Utente utente = (Utente) session.getAttribute("utente");
    
        for (Player player : players) {
            if (player.getUtente().getUsername().equals(utente.getUsername())) { 
                return new SinglePlayerDataDTO(
                    player.getPartiteGiocate(),
                    player.getVittorie(),
                    player.getMaxScore()
                );
            }
        }
    
        return null;
    }
    
}
