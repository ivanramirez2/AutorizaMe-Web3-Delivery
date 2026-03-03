package com.example.autorizame_api.controller;

import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.model.Cliente;
import com.example.autorizame_api.service.AutorizadoService;
import com.example.autorizame_api.service.ClienteService;
import com.example.autorizame_api.service.PedidoService;

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
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final AutorizadoService autorizadoService;


    public ClienteController(ClienteService clienteService,PedidoService pedidoService, AutorizadoService autorizadoService
    ) {
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.autorizadoService = autorizadoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        Cliente creado = clienteService.create(cliente);
        URI location = URI.create("/api/v1/clientes/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id,
                                        @Valid @RequestBody Cliente cliente) {
        Cliente actualizado = clienteService.update(id, cliente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCliente}/autorizados")
    public ResponseEntity<List<Autorizado>> getAutorizadosByCliente(@PathVariable Long idCliente) {

        // Verificamos que el cliente existe
        clienteService.findById(idCliente);

        // Obtenemos los IDs de autorizados desde los pedidos
        List<Long> autorizadosIds = pedidoService.findAutorizadosIdsByCliente(idCliente);

        // Convertimos IDs en objetos Autorizado
        List<Autorizado> autorizados = autorizadoService.findByIds(autorizadosIds);

        return ResponseEntity.ok(autorizados);
    }
}
