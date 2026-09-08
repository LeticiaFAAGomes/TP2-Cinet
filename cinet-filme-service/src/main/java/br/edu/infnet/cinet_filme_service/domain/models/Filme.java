package br.edu.infnet.cinet_filme_service.domain.models;

import br.edu.infnet.cinet_filme_service.domain.valueObjects.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {
    private FilmeId id;
    private FilmeTitulo titulo;
    private Genero genero;
    private Duracao duracao;
    private DataLancamento dataLancamento;
}
