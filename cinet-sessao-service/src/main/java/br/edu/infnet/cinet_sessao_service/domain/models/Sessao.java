package br.edu.infnet.cinet_sessao_service.domain.models;

import br.edu.infnet.cinet_sessao_service.domain.valueObjects.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    private SessaoId id;
    private FilmeId filmeId;
    private Sala sala;
    private ValorMonetario valor;
    private DataProgramada dataProgramada;
}
