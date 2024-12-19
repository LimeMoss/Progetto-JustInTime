import com.justInTime.controller.FeedbackController;
import com.justInTime.model.Feedback;
import com.justInTime.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private List<Feedback> feedbacks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedbacks = Arrays.asList(new Feedback("Great service"), new Feedback("Could be improved"));
    }

    @Test
    void testGetAllFeedback() {
        // Mock the service call
        when(feedbackService.getAllFeedback()).thenReturn(feedbacks);

        // Call the method to get all feedback
        List<Feedback> response = feedbackController.getAllFeedback();

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Great service", response.get(0).getComment());
    }
}
