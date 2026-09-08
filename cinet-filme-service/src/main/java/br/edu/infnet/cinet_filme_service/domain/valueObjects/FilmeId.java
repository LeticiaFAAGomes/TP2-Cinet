package br.edu.infnet.cinet_filme_service.domain.valueObjects;

import java.util.Objects;
import java.util.UUID;

public record FilmeId(UUID id) {

    public FilmeId {
        Objects.requireNonNull(id, "O filme não pode ser nulo");
    }

    public static FilmeId novo() {
        return new FilmeId(UUID.randomUUID());
    }

    public static FilmeId de(UUID id) {
        return new FilmeId(id);
    }
}
