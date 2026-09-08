package br.edu.infnet.cinet_sessao_service.domain.valueObjects;

import java.util.Objects;
import java.util.UUID;

public record SessaoId(UUID id) {
    public SessaoId {
        Objects.requireNonNull(id, "A sessão não pode ser nula");
    }

    public static SessaoId novo() {
        return new SessaoId(UUID.randomUUID());
    }

    public static SessaoId de(UUID id) {
        return new SessaoId(id);
    }
}
