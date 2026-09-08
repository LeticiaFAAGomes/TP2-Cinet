package br.edu.infnet.cinet_sessao_service.infrastructure.entities;

import br.edu.infnet.cinet_sessao_service.domain.models.Sessao;
import br.edu.infnet.cinet_sessao_service.domain.valueObjects.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "sessao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoEntity {
    @Id
    private UUID id;
    private UUID filmeId;
    @Enumerated(EnumType.STRING)
    private Sala sala;
    private BigDecimal valor;
    private LocalDateTime dataProgramada;

    public SessaoEntity(Sessao sessao) {
        this.id = sessao.getId().id();
        this.filmeId = sessao.getFilmeId().id();
        this.sala = sessao.getSala();
        this.valor = sessao.getValor().valor();
        this.dataProgramada = sessao.getDataProgramada().data();
    }

    public Sessao toDomain() {
        return new Sessao(
                SessaoId.de(this.id),
                FilmeId.de(this.filmeId),
                this.sala,
                new ValorMonetario(this.valor),
                new DataProgramada(this.dataProgramada)
                );
    }
}
