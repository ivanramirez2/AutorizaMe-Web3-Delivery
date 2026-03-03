package com.example.autorizame_api.controller;

import com.example.autorizame_api.model.Repartidor;
import com.example.autorizame_api.service.RepartidorService;

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
@RequestMapping("/api/v1/repartidores")
public class RepartidorController {

    private final RepartidorService RepartidorService;

    public RepartidorController(RepartidorService repartidorService) {
        this.RepartidorService = repartidorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Repartidor>> getAll() {
        List<Repartidor> repartidor = RepartidorService.findAll();
        return ResponseEntity.ok(repartidor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repartidor> getById(@PathVariable Long id) {
        Repartidor repartidor = RepartidorService.findById(id);
        return ResponseEntity.ok(repartidor);
    }

    @PostMapping
    public ResponseEntity<Repartidor> create(@Valid @RequestBody Repartidor repartidor) {
        Repartidor creado = RepartidorService.create(repartidor);
        URI location = URI.create("/api/v1/Repartidors/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repartidor> update(@PathVariable Long id,
                                        @Valid @RequestBody Repartidor repartidor) {
        Repartidor actualizado = RepartidorService.update(id, repartidor);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Repartidor> delete(@PathVariable Long id) {
        RepartidorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
