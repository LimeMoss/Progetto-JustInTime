package com.justInTime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.model.Feedback;
import com.justInTime.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;




    /**
     * Crea un nuovo feedback e lo salva nel database.
     *
     * @param feedbackDescription La descrizione del feedback.
     * @param stars Il numero di stelle del feedback.
     * @return Il feedback appena creato.
     *
     * @throws IllegalArgumentException Se la descrizione del feedback supera i 255
     *                                   caratteri o se le stelle sono un valore
     *                                   non compreso tra 1 e 5.
     */
    public Feedback creaFeedback(String feedbackDescription, Integer stars) {

    
        if (feedbackDescription == null || feedbackDescription.length() > 255 || feedbackDescription.isEmpty()) {
            throw new IllegalArgumentException("La descrizione non può essere vuota o superare i 255 caratteri.");
        }


        if (stars == null) {
            throw new IllegalArgumentException("Il valore delle stelle non può essere null.");
        }

        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere un valore tra 1 e 5.");
        }


        Feedback feedback = new Feedback();
        feedback.setFeedback(feedbackDescription);
        feedback.setStars(stars);

        return feedbackRepository.save(feedback);
    }


    /**
     * Restituisce una lista di tutti i feedback presenti nel database.
     *
     * @return Lista di feedback.
     */

public List<Feedback> getAllFeedback() {
    return feedbackRepository.findAll();


}

}