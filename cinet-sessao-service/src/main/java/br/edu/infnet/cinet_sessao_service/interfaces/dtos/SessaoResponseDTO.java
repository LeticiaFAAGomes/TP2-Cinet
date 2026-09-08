package br.edu.infnet.cinet_sessao_service.interfaces.dtos;

import br.edu.infnet.cinet_sessao_service.domain.models.Sessao;
import br.edu.infnet.cinet_sessao_service.domain.valueObjects.Sala;
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
public class SessaoResponseDTO {
    @NotNull
    private UUID id;
    @NotNull
    private UUID filmeId;
    @NotNull
    private Sala sala;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    private LocalDateTime dataProgramada;

    public static SessaoResponseDTO fromDomain(Sessao sessao) {
        return new SessaoResponseDTO(
        sessao.getId().id(),
        sessao.getFilmeId().id(),
        sessao.getSala(),
        sessao.getValor().valor(),
        sessao.getDataProgramada().data()
        );
    }
}
