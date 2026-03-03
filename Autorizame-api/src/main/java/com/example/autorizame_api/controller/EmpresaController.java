package com.example.autorizame_api.controller;

import com.example.autorizame_api.model.Empresa;
import com.example.autorizame_api.service.EmpresaService;

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
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final EmpresaService EmpresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.EmpresaService = empresaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Empresa>> getAll() {
        List<Empresa> Empresa = EmpresaService.findAll();
        return ResponseEntity.ok(Empresa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable Long id) {
        Empresa Empresa = EmpresaService.findById(id);
        return ResponseEntity.ok(Empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> create(@Valid @RequestBody Empresa Empresa) {
        Empresa creado = EmpresaService.create(Empresa);
        URI location = URI.create("/api/v1/Empresas/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable Long id,
                                        @Valid @RequestBody Empresa Empresa) {
        Empresa actualizado = EmpresaService.update(id, Empresa);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> delete(@PathVariable Long id) {
        EmpresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
