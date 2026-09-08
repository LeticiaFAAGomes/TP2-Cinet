package br.edu.infnet.cinet_filme_service.interfaces.controllers;

import br.edu.infnet.cinet_filme_service.application.service.FilmeService;
import br.edu.infnet.cinet_filme_service.interfaces.dtos.FilmeRequestDTO;
import br.edu.infnet.cinet_filme_service.interfaces.dtos.FilmeResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping
    public ResponseEntity<List<FilmeResponseDTO>> listar() {
         return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<FilmeResponseDTO> criar(@Valid @RequestBody FilmeRequestDTO dto) {
        FilmeResponseDTO filme = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(filme);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> buscarPorId(@PathVariable UUID id) {
        FilmeResponseDTO filme = service.buscarPorId(id);
        return ResponseEntity.ok(filme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
