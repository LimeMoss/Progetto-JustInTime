package com.justInTime.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justInTime.model.Feedback;
import com.justInTime.repository.FeedbackRepository;
import com.justInTime.service.FeedbackService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedBackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    // TC_2.1_1: Descrizione troppo lunga
    @Test
    public void LU1_stelle_1_descrizione_troppo_lunga() {
        String descrizioneFeedback = "Paperino è davvero un grandissimo personaggio della disney che ha un nome molto lungo ma mai quanto mary poppins".repeat(10);
        Integer stelle = 1;

        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(descrizioneFeedback, stelle));
    }

    // TC_2.1_2: Descrizione vuota
    @Test
    public void LU2_stelle_1_descrizione_vuota() {
        String descrizioneFeedback = "";
        Integer stelle = 1;

        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(descrizioneFeedback, stelle));
    }

    // TC_2.1_3: Descrizione valida e 5 stelle
    @Test
    public void LU3_stelle_5_descrizione_valida() {
        String descrizioneFeedback = "Descrizione valida";
        Integer stelle = 5;

        Feedback feedbackAtteso = new Feedback();
        feedbackAtteso.setFeedback(descrizioneFeedback);
        feedbackAtteso.setStars(stelle);

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedbackAtteso);

        Feedback feedbackReale = feedbackService.creaFeedback(descrizioneFeedback, stelle);

        assertEquals(descrizioneFeedback, feedbackReale.getFeedback());
        assertEquals(stelle, feedbackReale.getStars());

        verify(feedbackRepository).save(any(Feedback.class));
    }

    // TC_2.1_4: Stelle non selezionate
    @Test
    public void LU3_stelle_null_descrizione_valida() {
        String descrizioneFeedback = "Descrizione valida";
        Integer stelle = null;

        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(descrizioneFeedback, stelle));
    }

}
