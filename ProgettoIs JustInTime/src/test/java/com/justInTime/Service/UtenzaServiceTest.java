package com.justInTime.Service;

import com.justInTime.model.User;
import com.justInTime.repository.FeedbackRepository;
import com.justInTime.repository.UtenzaRepository;
import com.justInTime.service.FeedbackService;
import com.justInTime.service.UtenzaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UtenzaServiceTest {

    @Mock
    private UtenzaRepository utenzaRepository;

    @InjectMocks
    private UtenzaService utenzaService;

    // TC_1.1_1: Username troppo lungo
    @Test
    public void LUS1_username_troppo_lungo() {
        String username = "IlCorsaroMaestroSuperFantasticoInvincibileIncredibileDeLosMideliosRomagnolo";
        String email = "corsaromaster7@gmail.com";
        String password = "Castoro7!";
        String confirmPassword = "Castoro7!";
        String nome = "Corsaro";
        String cognome = "Master";
        String telefono = "1122334455";
        String paese = "Italia";

        assertThrows(IllegalArgumentException.class, () -> utenzaService.creaUtente( ));
    }

