package br.edu.infnet.cinet_sessao_service.interfaces.dtos;

import br.edu.infnet.cinet_sessao_service.domain.models.Sessao;
import br.edu.infnet.cinet_sessao_service.domain.valueObjects.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoRequestDTO {
    @NotNull
    private UUID filmeId;
    @NotNull
    private Sala sala;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    private LocalDateTime dataProgramada;


    public Sessao toDomain() {
        return new Sessao(
                SessaoId.novo(),
                new FilmeId(this.filmeId),
                sala,
                new ValorMonetario(this.valor),
                new DataProgramada(this.dataProgramada)
        );
    }
}
