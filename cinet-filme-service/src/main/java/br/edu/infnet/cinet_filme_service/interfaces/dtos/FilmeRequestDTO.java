package br.edu.infnet.cinet_filme_service.interfaces.dtos;

import br.edu.infnet.cinet_filme_service.domain.models.Filme;
import br.edu.infnet.cinet_filme_service.domain.valueObjects.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeRequestDTO {
    @NotBlank
    private String titulo;
    @NotNull
    private Genero genero;
    @NotNull
    @Positive
    private Integer duracao;
    @NotNull
    private LocalDate dataLancamento;

    public Filme toDomain() {
        return new Filme(
                FilmeId.novo(),
                new FilmeTitulo(titulo),
                genero,
                new Duracao(duracao),
                new DataLancamento(dataLancamento)
        );
    }
}
