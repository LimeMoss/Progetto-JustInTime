package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Controller
public class RegiststrazioneController {

    @Autowired
    private UtenzaService utenzaService;

    // Mostra la pagina di registrazione
    @GetMapping("/registrazione")
    public String register() {
        return "registrazione";
    }

    // Gestisce la registrazione
    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String nome,
                                      @RequestParam String cognome,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String passwordCheck,
                                      @RequestParam String phoneNumber,
                                      @RequestParam String birthDate,
                                      @RequestParam String country,
                                      @RequestParam String countryCode,
                                      Model model) {
        try {
            // Validazione lato server
            if (!isValidUsername(username)) {
                throw new RuntimeException("Username non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
            }

            if (!isValidName(nome)) {
                throw new RuntimeException("Nome non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
            }

            if (!isValidSurname(cognome)) {
                throw new RuntimeException("Cognome non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
            }

            if (!isValidEmail(email)) {
                throw new RuntimeException("Email non valida.");
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                throw new RuntimeException("Numero di telefono non valido. Deve essere nel formato 111 111 1111.");
            }

            if (!password.equals(passwordCheck)) {
                throw new RuntimeException("Le password non corrispondono.");
            }

            if (!isValidPassword(password)) {
                throw new RuntimeException("Password non valida. Deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.");
            }

            String birthDateFormatted = reformatDate(birthDate, "yyyy-MM-dd", "dd/MM/yyyy");
            Date birthDateObj = convertStringToDate(birthDateFormatted, "dd/MM/yyyy");

            Utente user = new Utente(nome, cognome, birthDateObj, country,
                    countryCode + " " + phoneNumber, email, password, username);

            utenzaService.registerUser(user);
            return "redirect:/login";
        } catch (RuntimeException | ParseException e) {
            model.addAttribute("error", e.getMessage());
            return "registrazione";
        }
    }
    private boolean isValidUsername(String username) {
        return Pattern.matches("^[A-z0-9]{2,30}$", username);
    }

    private boolean isValidName(String nome) {
        return Pattern.matches("^[A-zÀ-ù ‘-]{2,30}$", nome);
    }

    private boolean isValidSurname(String cognome) {
        return Pattern.matches("^[A-zÀ-ù ‘-]{2,30}$", cognome);
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,}$", email);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches("^[0-9]{3}\\s[0-9]{3}\\s[0-9]{4}$", phoneNumber);
    }

    private boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password);
    }

    public static Date convertStringToDate(String dateStr, String formatStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.parse(dateStr);
    }

    public static String reformatDate(String date, String inputFormatStr, String outputFormatStr) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputFormatStr);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputFormatStr);

        // Parsing della stringa di input nel formato di ingresso
        Date parsedDate = inputFormat.parse(date);

        // Formattazione della data nel formato di uscita e ritorno della data riformattata e convertita
        return outputFormat.format(parsedDate);
    }
}
