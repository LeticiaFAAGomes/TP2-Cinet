package br.edu.infnet.cinet_sessao_service.domain.valueObjects;

import java.time.LocalDateTime;
import java.util.Objects;

public record DataProgramada(LocalDateTime data) {
    public DataProgramada {
        Objects.requireNonNull(data, "A data não pode ser nula");
    }
}
