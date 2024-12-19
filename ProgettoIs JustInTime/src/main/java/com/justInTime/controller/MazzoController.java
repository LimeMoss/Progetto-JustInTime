package com.justInTime.controller;

import com.justInTime.model.Carta;
import com.justInTime.service.MazzoPescaService;
import com.justInTime.service.MazzoScartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST che gestisce le operazioni sui mazzi di carte.
 * Fornisce endpoint per la gestione del mazzo di pesca e del mazzo degli scarti.
 */
@RestController
@RequestMapping("/api/mazzo")
public class MazzoController {

    // Service per la gestione del mazzo di pesca
    private final MazzoPescaService mazzoPescaService;
    // Service per la gestione del mazzo degli scarti
    private final MazzoScartoService mazzoScartoService;

    /**
     * Costruttore che inizializza i service necessari tramite dependency injection
     * @param mazzoPescaService service per gestire il mazzo di pesca
     * @param mazzoScartoService service per gestire il mazzo degli scarti
     */
    @Autowired
    public MazzoController(MazzoPescaService mazzoPescaService, MazzoScartoService mazzoScartoService) {
        this.mazzoPescaService = mazzoPescaService;
        this.mazzoScartoService = mazzoScartoService;
    }

    /**
     * Pesca una carta dal mazzo di pesca
     * @return ResponseEntity contenente la carta pescata se presente, NOT_FOUND se il mazzo è vuoto
     */
    @GetMapping("/pesca")
    public ResponseEntity<Carta> pescaCarta() {
        Carta carta = mazzoPescaService.pescaCarta();
        if (carta != null) {
            return ResponseEntity.ok(carta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Recupera l'ultima carta scartata dal mazzo degli scarti
     * @return ResponseEntity contenente l'ultima carta scartata se presente, NOT_FOUND se il mazzo è vuoto
     */
    @GetMapping("/scarto/ultima")
    public ResponseEntity<Carta> ultimaCartaScartata() {
        Carta carta = mazzoScartoService.ultimaCartaScartata();
        if (carta != null) {
            return ResponseEntity.ok(carta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Aggiunge una carta al mazzo degli scarti
     * @param carta la carta da scartare
     * @return ResponseEntity vuoto con stato OK
     */
    @PostMapping("/scarta")
    public ResponseEntity<Void> scartaCarta(@RequestBody Carta carta) {
        mazzoScartoService.aggiungiCarta(carta);
        return ResponseEntity.ok().build();
    }

    /**
     * Recupera una carta specifica dal mazzo di pesca in base all'indice
     * @param index posizione della carta nel mazzo
     * @return ResponseEntity contenente la carta se presente, NOT_FOUND se l'indice non è valido
     */
    @GetMapping("/pesca/{index}")
    public ResponseEntity<Carta> getCartaPesca(@PathVariable int index) {
        Carta carta = mazzoPescaService.getCarta(index);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Recupera una carta specifica dal mazzo degli scarti in base all'indice
     * @param index posizione della carta nel mazzo
     * @return ResponseEntity contenente la carta se presente, NOT_FOUND se l'indice non è valido
     */
    @GetMapping("/scarto/{index}")
    public ResponseEntity<Carta> getCartaScarto(@PathVariable int index) {
        Carta carta = mazzoScartoService.getCarta(index);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Aggiunge una carta al mazzo di pesca
     * @param carta la carta da aggiungere
     * @return ResponseEntity vuoto con stato OK
     */
    @PostMapping("/pesca/aggiungi")
    public ResponseEntity<Void> aggiungiCartaPesca(@RequestBody Carta carta) {
        mazzoPescaService.aggiungiCarta(carta);
        return ResponseEntity.ok().build();
    }

    /**
     * Rimuove una carta specifica dal mazzo di pesca
     * @param carta la carta da rimuovere
     * @return ResponseEntity vuoto con stato OK
     */
    @DeleteMapping("/pesca/rimuovi")
    public ResponseEntity<Void> rimuoviCartaPesca(@RequestBody Carta carta) {
        mazzoPescaService.rimuoviCarta(carta);
        return ResponseEntity.ok().build();
    }

    /**
     * Rimuove una carta specifica dal mazzo degli scarti
     * @param carta la carta da rimuovere
     * @return ResponseEntity vuoto con stato OK
     */
    @DeleteMapping("/scarto/rimuovi")
    public ResponseEntity<Void> rimuoviCartaScarto(@RequestBody Carta carta) {
        mazzoScartoService.rimuoviCarta(carta);
        return ResponseEntity.ok().build();
    }
}