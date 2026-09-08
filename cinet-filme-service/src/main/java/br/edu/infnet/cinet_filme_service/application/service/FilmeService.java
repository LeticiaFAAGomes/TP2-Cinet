package br.edu.infnet.cinet_filme_service.application.service;

import br.edu.infnet.cinet_filme_service.domain.models.Filme;
import br.edu.infnet.cinet_filme_service.infrastructure.persistence.entity.FilmeEntity;
import br.edu.infnet.cinet_filme_service.infrastructure.persistence.repository.FilmeRepository;
import br.edu.infnet.cinet_filme_service.interfaces.dtos.FilmeRequestDTO;
import br.edu.infnet.cinet_filme_service.interfaces.dtos.FilmeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public List<FilmeResponseDTO> listar() {
        return repository.findAll().stream().map(FilmeEntity::toDomain).map(FilmeResponseDTO::fromDomain).toList();
    }
    public FilmeResponseDTO criar(FilmeRequestDTO dto) {
        Filme filme = dto.toDomain();
        FilmeEntity entity = new FilmeEntity(filme);
        FilmeEntity salvo = repository.save(entity);

        return FilmeResponseDTO.fromDomain(salvo.toDomain());
    }
    public FilmeResponseDTO buscarPorId(UUID id) {
        FilmeEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        return FilmeResponseDTO.fromDomain(entity.toDomain());
    }
    public void deletar(UUID id) {
         if (!repository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado");
        }

        repository.deleteById(id);
    }
}
