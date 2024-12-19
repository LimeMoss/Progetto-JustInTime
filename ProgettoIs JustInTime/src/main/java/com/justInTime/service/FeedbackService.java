package com.justInTime.service;


@Service
public class FeedbackService {
    private final FeedbackService FeedbackService;

    public FeedbackService(FeedbackService FeedbackService){
        this.FeedbackService = FeedbackService;
    }

  public Feedback creaFeedback(String Feedback, int stars) {
        Feedback feedback = new Player(Feedback, Stars);
        return playerRepository.save(Feedback);
    }

}