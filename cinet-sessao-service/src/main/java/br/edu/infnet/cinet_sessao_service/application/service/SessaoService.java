package br.edu.infnet.cinet_sessao_service.application.service;

import br.edu.infnet.cinet_sessao_service.domain.models.Sessao;
import br.edu.infnet.cinet_sessao_service.infrastructure.entities.SessaoEntity;
import br.edu.infnet.cinet_sessao_service.infrastructure.repositories.SessaoRepository;
import br.edu.infnet.cinet_sessao_service.interfaces.dtos.SessaoRequestDTO;
import br.edu.infnet.cinet_sessao_service.interfaces.dtos.SessaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository repository;

    public List<SessaoResponseDTO> listar() {
        return repository.findAll().stream().map(SessaoEntity::toDomain).map(SessaoResponseDTO::fromDomain).toList();
    }
    public SessaoResponseDTO criar(SessaoRequestDTO dto) {
        Sessao sessao = dto.toDomain();
        SessaoEntity entity = new SessaoEntity(sessao);
        SessaoEntity salvo = repository.save(entity);

        return SessaoResponseDTO.fromDomain(salvo.toDomain());
    }
    public SessaoResponseDTO buscarPorId(UUID id) {
        SessaoEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessao não encontrada."));

        return SessaoResponseDTO.fromDomain(entity.toDomain());
    }
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Sessao não encontrada.");
        }

        repository.deleteById(id);
    }
}
