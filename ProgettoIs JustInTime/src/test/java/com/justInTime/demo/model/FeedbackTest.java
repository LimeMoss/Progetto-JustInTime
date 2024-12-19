import com.justInTime.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    private Feedback feedback;

    @BeforeEach
    void setUp() {
        // Inizializza l'istanza di Feedback
        feedback = new Feedback(5, "Ottimo servizio");
    }

    @Test
    void testGetFeedback() {
        // Verifica che il feedback iniziale sia corretto
        assertEquals("Ottimo servizio", feedback.getFeedback(), "Il feedback dovrebbe corrispondere al valore iniziale");
    }

    @Test
    void testSetFeedback() {
        // Modifica il feedback
        feedback.setFeedback("Servizio eccellente");
        // Verifica la modifica
        assertEquals("Servizio eccellente", feedback.getFeedback(), "Il feedback dovrebbe essere aggiornato correttamente");
    }

    @Test
    void testGetStars() {
        // Verifica che il numero di stelle iniziale sia corretto
        assertEquals(5, feedback.getStars(), "Le stelle iniziali dovrebbero corrispondere al valore iniziale");
    }

    @Test
    void testSetStars() {
        // Modifica il numero di stelle
        feedback.setStars(4);
        // Verifica la modifica
        assertEquals(4, feedback.getStars(), "Il numero di stelle dovrebbe essere aggiornato correttamente");
    }
}
