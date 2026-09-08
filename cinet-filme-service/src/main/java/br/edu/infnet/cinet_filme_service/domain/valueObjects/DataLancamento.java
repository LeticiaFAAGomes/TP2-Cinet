package br.edu.infnet.cinet_filme_service.domain.valueObjects;

import java.time.LocalDate;
import java.util.Objects;

public record DataLancamento(LocalDate data) {
    public DataLancamento {
        Objects.requireNonNull(data, "A data não pode ser nula");
    }
}
