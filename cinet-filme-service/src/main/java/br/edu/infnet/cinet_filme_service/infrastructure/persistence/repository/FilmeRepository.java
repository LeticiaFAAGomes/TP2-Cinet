package br.edu.infnet.cinet_filme_service.infrastructure.persistence.repository;

import br.edu.infnet.cinet_filme_service.infrastructure.persistence.entity.FilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilmeRepository extends JpaRepository<FilmeEntity, UUID> {
}
