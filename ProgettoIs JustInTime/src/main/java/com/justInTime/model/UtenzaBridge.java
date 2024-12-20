package com.justInTime.model;

/**
 * L'interfaccia UtenzaBridge definisce un contratto per ottenere il nome e l'ID
 * di un'entità. Le classi che la implementano devono fornire una definizione per questi metodi.
 */
public interface UtenzaBridge {

    /**
     * Restituisce il nome dell'entità.
     *
     * @return il nome dell'entità come {@code String}.
     */
    String getName();

    /**
     * Restituisce l'ID dell'entità.
     *
     * @return l'ID dell'entità come {@code Long}.
     */
    Long getId();
}
