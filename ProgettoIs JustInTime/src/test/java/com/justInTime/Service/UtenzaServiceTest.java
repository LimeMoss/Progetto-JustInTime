package com.justInTime.Service;

import com.justInTime.model.Utente;

import com.justInTime.repository.UtenzaRepository;

import com.justInTime.service.UtenzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        utente.setDataNascita(new Date(2004,1,11));
    }


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

        utente.setDataNascita(new Date(2004,1,11));
    }

    // TC_1.1_12: Corretto!
    @Test
    public void PR2_corretto_registra() {

        assertThrows(IllegalArgumentException.class, () -> utenzaService.registerUser(utente,confirmPassword));

    }
}

