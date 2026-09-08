package br.edu.infnet.cinet_sessao_service.infrastructure.repositories;

import br.edu.infnet.cinet_sessao_service.infrastructure.entities.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessaoRepository extends JpaRepository<SessaoEntity, UUID> {
}
