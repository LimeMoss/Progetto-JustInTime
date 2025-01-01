

package com.justInTime.controller;

import com.justInTime.model.Utente;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    
    public static boolean isUtenteLoggato(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        return utente != null;
    }
}
