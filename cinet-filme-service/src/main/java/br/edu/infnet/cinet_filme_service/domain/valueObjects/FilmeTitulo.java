package br.edu.infnet.cinet_filme_service.domain.valueObjects;

import java.util.Objects;

public record FilmeTitulo(String titulo) {
    public FilmeTitulo {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ser nulo");
        }
    }
}
