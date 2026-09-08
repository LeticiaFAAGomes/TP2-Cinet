package br.edu.infnet.cinet_filme_service.infrastructure.persistence.entity;

import br.edu.infnet.cinet_filme_service.domain.models.Filme;
import br.edu.infnet.cinet_filme_service.domain.valueObjects.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "filme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeEntity {
    @Id
    private UUID id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private Integer duracao;
    private LocalDate dataLancamento;

    public FilmeEntity(Filme filme) {
        this.id = filme.getId().id();
        this.titulo = filme.getTitulo().titulo();
        this.genero = filme.getGenero();
        this.duracao = filme.getDuracao().minutos();
        this.dataLancamento = filme.getDataLancamento().data();
    }

    public Filme toDomain() {
    return new Filme(
            FilmeId.de(this.id),
            new FilmeTitulo(this.titulo),
            this.genero,
            new Duracao(this.duracao),
            new DataLancamento(this.dataLancamento)
    );
}

}
