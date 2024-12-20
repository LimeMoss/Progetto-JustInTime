package com.justInTime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe che rappresenta un feedback lasciato dagli utenti.
 * Ogni feedback ha un identificatore univoco, un testo e un punteggio (stelle).
 */
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  // Identificativo univoco del feedback
    
    private String feedback;  // Testo del feedback
    private long stars;       // Punteggio in stelle associato al feedback

    /**
     * Costruttore predefinito per creare un'istanza di Feedback.
     */
    public Feedback() {}

    /**
     * Costruttore per creare un'istanza di Feedback con testo e punteggio specificati.
     * 
     * @param stars Il numero di stelle del feedback.
     * @param feedback Il testo del feedback.
     */
    public Feedback(long stars, String feedback) {
        this.feedback = feedback;
        this.stars = stars;
    }

    /**
     * Restituisce il testo del feedback.
     * 
     * @return Il testo del feedback.
     */
    public String getFeedback() {
        return this.feedback;
    }

    /**
     * Restituisce il punteggio in stelle del feedback.
     * 
     * @return Il numero di stelle.
     */
    public long getStars() {
        return this.stars;
    }

    /**
     * Imposta il testo del feedback.
     * 
     * @param feedback Il nuovo testo del feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Imposta il punteggio in stelle del feedback.
     * 
     * @param stars Il nuovo numero di stelle.
     */
    public void setStars(long stars) {
        this.stars = stars;
    }
}
