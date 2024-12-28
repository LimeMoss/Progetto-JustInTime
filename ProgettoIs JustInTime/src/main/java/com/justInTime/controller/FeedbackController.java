package com.justInTime.controller;

import com.justInTime.model.Feedback;
import com.justInTime.service.FeedbackService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Endpoint per ottenere tutti i feedback.
     *
     * @return Lista di feedback.
     */
    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    /**
     * Endpoint per creare un nuovo feedback.
     *
     * @param feedbackDescription La descrizione del feedback.
     * @param stars Il numero di stelle del feedback (da 1 a 5).
     * @return Il feedback appena creato o un messaggio di errore.
     */
    @PostMapping
    public ResponseEntity<?> creaFeedback(
            @RequestParam String descrizione,
            @RequestParam Integer stars) {
        
        try {
            Feedback feedback = feedbackService.creaFeedback(descrizione, stars);
            return new ResponseEntity<>(feedback, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}


