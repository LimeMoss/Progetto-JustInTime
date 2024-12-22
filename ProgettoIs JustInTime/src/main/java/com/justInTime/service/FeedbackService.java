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

  public Feedback creaFeedback(String Feedback, int stars) {
        Feedback feedback = new Feedback();
        feedback.setFeedback(Feedback);
        feedback.setStars(stars);
        return feedbackRepository.save(feedback);
    }

public List<Feedback> getAllFeedback() {
    return feedbackRepository.findAll();


}

}