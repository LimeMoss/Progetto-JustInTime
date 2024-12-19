package com.justInTime.model;


@Entity
public class Feedback{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    private String feedback;
    private long stars;

    public Feedback(long stars, String feedback){
        this.feedback=feedback;
        this.stars=stars;

    }

    public String getFeedback(){
        return this.feedback;
    }

    public long getStars()
    {
        return this.stars;
    }

    public void setFeedback(String feedback){

        this.feedback=feedback;

    }

    public void setStars(long stars){
        this.stars=stars;
    }

    
    


}