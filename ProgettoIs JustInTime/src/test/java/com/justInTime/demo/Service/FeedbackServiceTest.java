import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository; // Mock the repository

    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        feedbackService = new FeedbackService(feedbackRepository); // Inject mocked repository
    }

    @Test
    void testCreaFeedback() {
        // Given
        String feedbackText = "Great Game!";
        long stars = 5;

        Feedback feedbackToSave = new Feedback(stars, feedbackText);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedbackToSave); // Mock repository method

        // When
        Feedback savedFeedback = feedbackService.creaFeedback(feedbackText, stars);

        // Then
        assertNotNull(savedFeedback);
        assertEquals(feedbackText, savedFeedback.getFeedback());
        assertEquals(stars, savedFeedback.getStars());
        verify(feedbackRepository, times(1)).save(any(Feedback.class)); // Verify that save was called once
    }
}
