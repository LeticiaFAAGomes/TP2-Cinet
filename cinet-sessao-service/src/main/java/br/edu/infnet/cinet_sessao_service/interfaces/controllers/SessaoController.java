package br.edu.infnet.cinet_sessao_service.interfaces.controllers;

import br.edu.infnet.cinet_sessao_service.application.service.SessaoService;
import br.edu.infnet.cinet_sessao_service.interfaces.dtos.SessaoRequestDTO;
import br.edu.infnet.cinet_sessao_service.interfaces.dtos.SessaoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService service;

    @GetMapping
    public ResponseEntity<List<SessaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> criar(@Valid @RequestBody SessaoRequestDTO dto) {
        SessaoResponseDTO sessao = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        SessaoResponseDTO sessao = service.buscarPorId(id);
        return ResponseEntity.ok(sessao );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
