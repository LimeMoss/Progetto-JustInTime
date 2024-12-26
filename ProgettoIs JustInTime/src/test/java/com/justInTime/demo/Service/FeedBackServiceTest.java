package com.justInTime.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justInTime.model.Feedback;
import com.justInTime.repository.FeedbackRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    public void testCreaFeedback_ValidInput() {
        // Arrange
        String feedbackDescription = "Valid feedback description";
        Integer stars = 3;
        Feedback expectedFeedback = new Feedback();
        expectedFeedback.setFeedback(feedbackDescription);
        expectedFeedback.setStars(stars);

        when(feedbackRepository.save(expectedFeedback)).thenReturn(expectedFeedback);

        // Act
        Feedback actualFeedback = feedbackService.creaFeedback(feedbackDescription, stars);

        // Assert
        assertEquals(expectedFeedback, actualFeedback);
    }

    @Test
    public void testCreaFeedback_NullFeedbackDescription() {
        // Arrange
        String feedbackDescription = null;
        Integer stars = 3;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(feedbackDescription, stars));
    }

    @Test
    public void testCreaFeedback_FeedbackDescriptionTooLong() {
        // Arrange
        String feedbackDescription = "Feedback description that is longer than 255 characters".repeat(10);
        Integer stars = 3;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(feedbackDescription, stars));
    }

    @Test
    public void testCreaFeedback_NullStars() {
        // Arrange
        String feedbackDescription = "Valid feedback description";
        Integer stars = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(feedbackDescription, stars));
    }

    @Test
    public void testCreaFeedback_StarsLessThan1() {
        // Arrange
        String feedbackDescription = "Valid feedback description";
        Integer stars = 0;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(feedbackDescription, stars));
    }

    @Test
    public void testCreaFeedback_StarsGreaterThan5() {
        // Arrange
        String feedbackDescription = "Valid feedback description";
        Integer stars = 6;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedbackService.creaFeedback(feedbackDescription, stars));
    }
}