package br.edu.infnet.cinet_sessao_service.infrastructure.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class FilmeClient {

    private final RestClient restClient;

    public FilmeClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://cinet-filme-service:8081")
                .build();
    }

    public boolean existe(UUID filmeId) {
        try {
            restClient.get()
                    .uri("/filmes/{id}", filmeId)
                    .retrieve()
                    .toBodilessEntity();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
