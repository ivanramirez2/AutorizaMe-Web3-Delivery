package com.example.autorizame_api.controller;

import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.service.AutorizadoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

/*
    ✅ CRUD completo

    ✅ Verbos HTTP correctos

    ✅ ResponseEntity

    ✅ Validaciones

    ✅ Arquitectura por capas

    ✅ Pruebas reales con Postman

*/


@RestController
@RequestMapping("/api/v1/autorizados")
public class AutorizadoController {

    private final AutorizadoService autorizadoService;

    public AutorizadoController(AutorizadoService autorizadoService) {
        this.autorizadoService = autorizadoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Autorizado>> getAll() {
        List<Autorizado> Autorizados = autorizadoService.findAll();
        return ResponseEntity.ok(Autorizados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autorizado> getById(@PathVariable Long id) {
        Autorizado Autorizado = autorizadoService.findById(id);
        return ResponseEntity.ok(Autorizado);
    }

    @PostMapping
    public ResponseEntity<Autorizado> create(@Valid @RequestBody Autorizado Autorizado) {
        Autorizado creado = autorizadoService.create(Autorizado);
        URI location = URI.create("/api/v1/Autorizados/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autorizado> update(@PathVariable Long id,
                                        @Valid @RequestBody Autorizado Autorizado) {
        Autorizado actualizado = autorizadoService.update(id, Autorizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Autorizado> delete(@PathVariable Long id) {
        autorizadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
