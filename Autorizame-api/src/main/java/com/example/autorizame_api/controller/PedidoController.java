package com.example.autorizame_api.controller;

import com.example.autorizame_api.model.Pedido;
import com.example.autorizame_api.service.PedidoService;

import jakarta.validation.Valid;
import java.util.HashMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final com.example.autorizame_api.service.BlockchainService blockchainService;

    public PedidoController(PedidoService pedidoService, 
                            com.example.autorizame_api.service.BlockchainService blockchainService) {
        this.pedidoService = pedidoService;
        this.blockchainService = blockchainService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> create(@Valid @RequestBody Pedido pedido) {
        Pedido creado = pedidoService.create(pedido);
        URI location = URI.create("/api/v1/pedidos/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id,
                                         @Valid @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.update(id, pedido);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/cliente/{idCliente}/total")
    public ResponseEntity<Map<String, Object>> totalPorCliente(@PathVariable Long idCliente) {

        long total = pedidoService.countByCliente(idCliente);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("idCliente", idCliente);
        response.put("totalPedidos", total);

        return ResponseEntity.ok(response);
    }

    // --- NUEVOS ENDPOINTS BLOCKCHAIN ---

    @GetMapping("/blockchain/metadata/{cid}")
    public ResponseEntity<Map<String, Object>> getMetadata(@PathVariable String cid) {
        System.out.println("[Spring] Endpoint: Recuperar metadata de " + cid);
        return ResponseEntity.ok(blockchainService.retrieveMetadata(cid));
    }

    @PostMapping("/{id}/transferir")
    public ResponseEntity<Map<String, String>> transferir(@PathVariable Long id, 
                                                         @RequestBody Map<String, String> body) {
        String privateKey = body.get("privateKey");
        if (privateKey == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Falta privateKey"));
        }

        Pedido pedido = pedidoService.findById(id);
        if (pedido.getBlockchainTokenId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El pedido no tiene un token mintado"));
        }

        // Buscamos al autorizado para obtener su address
        com.example.autorizame_api.model.Autorizado aut = pedidoService.findAutorizadoByPedido(pedido);
        
        String txHash = blockchainService.transferNFT(privateKey, aut.getAddress(), pedido.getBlockchainTokenId());
        
        // Actualizamos estado del pedido
        pedido.setEstado("ENTREGADO / TRANSFERIDO");
        pedidoService.update(id, pedido);

        return ResponseEntity.ok(Map.of("txHash", txHash, "estado", pedido.getEstado()));
    }
}
