package br.edu.infnet.cinet_filme_service.domain.valueObjects;

public record Duracao(Integer minutos) {
    public Duracao {
        if (minutos == null || minutos <- 0) {
            throw new IllegalArgumentException("A duração deve ser maior que 0.");
        }
    }
}
