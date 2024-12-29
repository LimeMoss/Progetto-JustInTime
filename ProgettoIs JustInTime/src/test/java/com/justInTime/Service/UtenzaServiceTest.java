package com.justInTime.Service;

import com.justInTime.model.Feedback;
import com.justInTime.model.Utente;

import com.justInTime.repository.UtenzaRepository;

import com.justInTime.service.UtenzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtenzaServiceTest {

    @Mock
    private UtenzaRepository utenzaRepository;

    @InjectMocks
    private UtenzaService utenzaService;

    private Utente utente;
    private String confirmPassword;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        utente = new Utente();
        utente.setUsername("IlCorsaro");
        utente.setEmail("corsaromaster7@gmail.com");
        utente.setPassword("Castoro7!");
        confirmPassword="Castoro7!";
        utente.setNome("Corsaro");
        utente.setCognome("Master");
        utente.setTelefono("+39 112 233 4455");
        utente.setPaese("Italia");
        utente.setDataNascita(new Date(2004, Calendar.JANUARY,11));

    }
    //----------------REGISTRAZIONE UTENTE---------------------//

    // TC_1.1_1: Username troppo lungo
    @Test
    public void LUS1_username_troppo_lungo_registra() {
        utente.setUsername("IlCorsaroMaestroSuperFantasticoInvincibileIncredibileDeLosMideliosRomagnolo");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setUsername("IlCorsaro");
    }

    // TC_1.1_2: Username errato
    @Test
    public void FUS1_username_errato_registra() {
        utente.setUsername("IlCorsaro!");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setUsername("IlCorsaro");
    }

    // TC_1.1_3: Email non corretta
    @Test
    public void FE1_email_non_corretta_registra() {
        utente.setEmail("corsaromaster7@gmail");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setEmail("corsaromaster7@gmail.com");
    }

    // TC_1.1_4 Password troppo breve
    @Test
    public void LP1_password_troppo_corta_registra() {
        utente.setPassword("C");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setPassword("Castoro7!");
    }

    // TC_1.1_5: Password non corretta
    @Test
    public void FP1_password_non_corretta_registra() {
        utente.setPassword("Castoro7");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setPassword("Castoro7!");
    }

    // TC_1.1_6: Conferma password errata
    @Test
    public void MCP1_conferma_password_non_corretta_registra() {
        confirmPassword="Castoro";

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        confirmPassword="Castoro7!";
    }

    // TC_1.1_7: Nome non corretto
    @Test
    public void FNO1_nome_non_corretto_registra() {
        utente.setNome("C");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setNome("Corsaro");
    }

    // TC_1.1_8: Cognome non corretto
    @Test
    public void FCO1_cognome_non_corretto_registra() {
        utente.setCognome("M");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setCognome("Master");
    }

    // TC_1.1_9: Numero di telefono non corretto
    @Test
    public void FNT1_numero_di_telefono_non_corretto_registra() {
        utente.setTelefono("+39 11223");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setTelefono("+39 112 233 4455");
    }

    // TC_1.1_10: Inserimento di un paese
    @Test
    public void SP1_inserimento_paese_richiesto_registra() {
        utente.setPaese("");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setPaese("Italia");
    }

    // TC_1.1_11: Data di nascita non può essere vuota
    @SuppressWarnings("deprecation")
    @Test
    public void PR1_data_nascita_vuota_registra() {
        utente.setDataNascita(null);

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

        utente.setDataNascita(new Date(2004, Calendar.JANUARY,11));
    }

    // TC_1.1_12: Username associato ad un altro account
    @Test
    public void ES1_username_altro_account_registra() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        // Salva il primo utente
        utenzaService.registerUser(utente, confirmPassword);
        utente.setEmail("corsaromaster7@gmail.com");
        when(utenzaRepository.existsByUsername(utente.getUsername())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> utenzaService.registerUser(utente,confirmPassword));

    }

    // TC_1.1_13: Email associato ad un altro account
    @Test
    public void ESE1_email_altro_account_registra() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        // Salva il primo utente
        utenzaService.registerUser(utente, confirmPassword);
        utente.setUsername("IlCorsaro1");
        when(utenzaRepository.existsByUsername(utente.getUsername())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> utenzaService.registerUser(utente,confirmPassword));

    }

    // TC_1.1_14: Corretto!
    @Test
    public void PR2_corretto_registra() {
        utenzaService.registerUser(utente, confirmPassword);

        verify(utenzaRepository).save(any(Utente.class));
    }

    //----------------MODIFICA UTENTE---------------------//

    // TC_1.2_1: Username troppo lungo
    @Test
    public void LUS1_username_troppo_lungo_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        // Salva l'utente iniziale
        utenzaService.registerUser(utente, confirmPassword);

        // Simula il reperimento dell'utente salvato, che nel database esista un utente con ID 1L
        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));

        // Aggiorna l'username con un valore troppo lungo
        utente.setUsername("IlCorsaroMaestroSuperFantasticoInvincibileIncredibileDeLosMideliosRomagnolo");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setUsername("IlCorsaro");
    }

    // TC_1.2_2: Username errato
    @Test
    public void FUS1_username_errato_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setUsername("IlCorsaro!");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setUsername("IlCorsaro");
    }

    // TC_1.2_3: Email non corretta
    @Test
    public void FE1_email_non_corretta_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setEmail("corsaromaster7@gmail");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setEmail("corsaromaster7@gmail.com");
    }

    // TC_1.2_4 Password troppo breve
    @Test
    public void LP1_password_troppo_corta_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setPassword("C");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setPassword("Castoro7!");
    }

    // TC_1.2_5: Password non corretta
    @Test
    public void FP1_password_non_corretta_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setPassword("Castoro7");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setPassword("Castoro7!");
    }

    // TC_1.2_6: Conferma password errata
    @Test
    public void MCP1_conferma_password_non_corretta_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        confirmPassword="Castoro";

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        confirmPassword="Castoro7!";
    }

    // TC_1.2_7: Nome non corretto
    @Test
    public void FNO1_nome_non_corretto_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setNome("C");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setNome("Corsaro");
    }

    // TC_1.2_8: Cognome non corretto
    @Test
    public void FCO1_cognome_non_corretto_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setCognome("M");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setCognome("Master");
    }

    // TC_1.2_9: Numero di telefono non corretto
    @Test
    public void FNT1_numero_di_telefono_non_corretto_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setTelefono("+39 11223");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setTelefono("+39 112 233 4455");
    }

    // TC_1.2_10: Inserimento di un paese
    @Test
    public void SP1_inserimento_paese_richiesto_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setPaese("");

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setPaese("Italia");
    }

    // TC_1.2_11: Data di nascita non può essere vuota
    @Test
    public void PR1_data_nascita_vuota_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);

        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setDataNascita(null);

        assertThrows(IllegalArgumentException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));

        utente.setDataNascita(new Date(2004,1,11));
    }

    // TC_1.2_12: Username associato ad un altro account
    @Test
    public void ES1_username_altro_account_modifica() {
        // Simula il salvataggio dell'utente
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        utenzaService.registerUser(utente, confirmPassword);
        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));

        // Crea un altro utente con la stessa email
        Utente altroUtente = new Utente();
        altroUtente.setId(2L);
        altroUtente.setUsername("IlCorsaroMaster");
        when(utenzaRepository.existsByUsername("IlCorsaroMaster")).thenReturn(true);

        // Prova ad aggiornare l'utente con lo stesso username di un altro utente
        utente.setUsername("IlCorsaroMaster");
        assertThrows(RuntimeException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));
    }

    // TC_1.2_13: Email associato ad un altro account
    @Test
    public void ESE1_email_altro_account_modifica() {
        // Simula il salvataggio dell'utente
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        // Salva l'utente iniziale
        utenzaService.registerUser(utente, confirmPassword);

        // Simula il reperimento dell'utente salvato
        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));

        // Crea un altro utente con la stessa email
        Utente altroUtente = new Utente();
        altroUtente.setId(2L);
        altroUtente.setEmail("corsaromaster@gmail.com");
        when(utenzaRepository.existsByEmail("corsaromaster@gmail.com")).thenReturn(true);

        // Prova ad aggiornare l'utente con l'email esistente
        utente.setEmail("corsaromaster@gmail.com");
        assertThrows(RuntimeException.class, () -> utenzaService.aggiornaUtente(utente.getId(), utente, confirmPassword));
    }

    // TC_1.2_14: Corretto!
    @Test
    public void PR2_corretto_modifica() {
        when(utenzaRepository.save(any(Utente.class))).thenAnswer(invocation -> {
            Utente savedUtente = invocation.getArgument(0);
            savedUtente.setId(1L); // Imposta l'ID utente
            return savedUtente;
        });

        // Salva l'utente iniziale
        utenzaService.registerUser(utente, confirmPassword);
        // Simula il reperimento dell'utente salvato
        when(utenzaRepository.findById(1L)).thenReturn(Optional.of(utente));
        utente.setEmail("corsaromaster@gmail.com");
        utente.setUsername("IlCorsaro1");
        utente.setPassword("Castoro7!!");
        String confermaPassword2="Castoro7!!";
        utente.setNome("Corsarone");
        utente.setCognome("Masterino");
        utente.setTelefono("+39 111 133 4455");
        utente.setPaese("Germania");
        utente.setDataNascita(new Date(2004, Calendar.FEBRUARY,4));

        Utente updatedUtente = utenzaService.aggiornaUtente(utente.getId(), utente, confermaPassword2);

        assertEquals("corsaromaster@gmail.com", updatedUtente.getEmail());
        assertEquals("IlCorsaro1", updatedUtente.getUsername());
        assertEquals("Castoro7!!", updatedUtente.getPassword());
        assertEquals("Corsarone", updatedUtente.getName());
        assertEquals("Masterino", updatedUtente.getCognome());
        assertEquals("+39 111 133 4455", updatedUtente.getTelefono());
        assertEquals("Germania", updatedUtente.getPaese());

    }
}

