package com.justInTime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.justInTime.model.Feedback;
import com.justInTime.repository.FeedbackRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository){
        this.feedbackRepository= feedbackRepository;
    }


    /**
     * Crea un nuovo feedback e lo salva nel database.
     *
     * @param Feedback Il testo del feedback.
     * @param stars Il numero di stelle del feedback.
     * @return Il feedback appena creato.
     */
  public Feedback creaFeedback(String Feedback, int stars) {
        Feedback feedback = new Feedback();
        feedback.setFeedback(Feedback);
        feedback.setStars(stars);
        return feedbackRepository.save(feedback);
    }
    /**
     * Restituisce la lista di tutti i feedback.
     *
     * @return La lista di tutti i feedback.
     */

public List<Feedback> getAllFeedback() {
    return feedbackRepository.findAll();


}

}