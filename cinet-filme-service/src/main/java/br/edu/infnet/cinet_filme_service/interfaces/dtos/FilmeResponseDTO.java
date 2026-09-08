package br.edu.infnet.cinet_filme_service.interfaces.dtos;

import br.edu.infnet.cinet_filme_service.domain.models.Filme;
import br.edu.infnet.cinet_filme_service.domain.valueObjects.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeResponseDTO {
    @NotNull
    private UUID id;
    @NotBlank
    private String titulo;
    @NotNull
    private Genero genero;
    @NotNull
    @Positive
    private Integer duracao;
    @NotNull
    private LocalDate dataLancamento;

    public static FilmeResponseDTO fromDomain(Filme filme) {
        return new FilmeResponseDTO(
                filme.getId().id(),
                filme.getTitulo().titulo(),
                filme.getGenero(),
                filme.getDuracao().minutos(),
                filme.getDataLancamento().data()
        );
    }
}
