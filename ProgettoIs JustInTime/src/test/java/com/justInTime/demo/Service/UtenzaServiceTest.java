import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.justInTime.model.Utenza;
import com.justInTime.repository.UtenzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

class UtenzaServiceTest {

    @Mock
    private UtenzaRepository utenzaRepository; // Mock the repository

    @Mock
    private BCryptPasswordEncoder passwordEncoder; // Mock password encoder

    private UtenzaService utenzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        utenzaService = new UtenzaService(utenzaRepository);
        utenzaService.passwordEncoder = passwordEncoder; // Inject mock password encoder
    }

    @Test
    void testRegisterUser_WhenEmailAlreadyRegistered() {
        // Given
        String email = "test@example.com";
        String username = "testUser";
        Utenza newUser = new Utenza();
        newUser.setEmail(email);
        newUser.setUsername(username);
        
        when(utenzaRepository.existsByEmail(email)).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            utenzaService.registerUser(newUser);
        });

        assertEquals("Email already registered.", exception.getMessage());
        verify(utenzaRepository, times(0)).save(any(Utenza.class)); // Ensure save wasn't called
    }

    @Test
    void testRegisterUser_WhenUsernameAlreadyTaken() {
        // Given
        String email = "newuser@example.com";
        String username = "takenUsername";
        Utenza newUser = new Utenza();
        newUser.setEmail(email);
        newUser.setUsername(username);

        when(utenzaRepository.existsByEmail(email)).thenReturn(false);
        when(utenzaRepository.existsByUsername(username)).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            utenzaService.registerUser(newUser);
        });

        assertEquals("Username already taken.", exception.getMessage());
        verify(utenzaRepository, times(0)).save(any(Utenza.class)); // Ensure save wasn't called
    }

    @Test
    void testRegisterUser_Success() {
        // Given
        String email = "newuser@example.com";
        String username = "newUser";
        String password = "password123";
        Utenza newUser = new Utenza();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);

        when(utenzaRepository.existsByEmail(email)).thenReturn(false);
        when(utenzaRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("hashedPassword");
        when(utenzaRepository.save(any(Utenza.class))).thenReturn(newUser);

        // When
        Utenza savedUser = utenzaService.registerUser(newUser);

        // Then
        assertNotNull(savedUser);
        assertEquals("newuser@example.com", savedUser.getEmail());
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("hashedPassword", savedUser.getPassword()); // Ensure password is encoded
        verify(utenzaRepository, times(1)).save(any(Utenza.class)); // Ensure save was called once
    }

    @Test
    void testLogin_WhenUserNotFound() {
        // Given
        String usernameOrEmail = "nonexistentUser";
        String password = "password123";

        when(utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            utenzaService.login(usernameOrEmail, password);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(utenzaRepository, times(1)).findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    @Test
    void testLogin_WhenInvalidPassword() {
        // Given
        String usernameOrEmail = "testuser@example.com";
        String password = "wrongPassword";
        Utenza existingUser = new Utenza();
        existingUser.setUsername("testuser@example.com");
        existingUser.setPassword("encodedPassword");

        when(utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(password, existingUser.getPassword())).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            utenzaService.login(usernameOrEmail, password);
        });

        assertEquals("Invalid credentials.", exception.getMessage());
        verify(utenzaRepository, times(1)).findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    @Test
    void testLogin_Success() {
        // Given
        String usernameOrEmail = "testuser@example.com";
        String password = "password123";
        Utenza existingUser = new Utenza();
        existingUser.setUsername("testuser@example.com");
        existingUser.setPassword("encodedPassword");

        when(utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(password, existingUser.getPassword())).thenReturn(true);

        // When
        Utenza loggedInUser = utenzaService.login(usernameOrEmail, password);

        // Then
        assertNotNull(loggedInUser);
        assertEquals("testuser@example.com", loggedInUser.getUsername());
        verify(utenzaRepository, times(1)).findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }
}
