package com.example.autorizame_api.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.model.Cliente;
import com.example.autorizame_api.model.Pedido;
import com.example.autorizame_api.service.BlockchainService;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    private final RestClient restClient;

    @Value("${microservices.ipfs.url}")
    private String ipfsUrl;

    @Value("${microservices.sc.url}")
    private String scUrl;

    public BlockchainServiceImpl(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @Override
    public String uploadMetadata(Pedido pedido, Cliente cliente, Autorizado autorizado) {
        Map<String, Object> body = new HashMap<>();
        body.put("idPedido", pedido.getId().toString());
        body.put("addressCliente", cliente.getAddress());
        body.put("addressAutorizado", autorizado.getAddress());
        body.put("timestamp", pedido.getFecha().atStartOfDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        @SuppressWarnings("unchecked")
        Map<String, String> response = restClient.post()
                .uri(ipfsUrl + "/subirMetadata")
                .body(body)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("cid")) {
            return response.get("cid");
        }
        throw new RuntimeException("Error al subir metadata a IPFS");
    }

    @Override
    public String[] mintNFT(Pedido pedido, Cliente cliente, Autorizado autorizado, String cid) {
        Map<String, Object> body = new HashMap<>();
        body.put("idPedido", pedido.getId());
        body.put("cliente", cliente.getAddress());
        body.put("autorizado", autorizado.getAddress());
        body.put("tokenURI", "https://gateway.pinata.cloud/ipfs/" + cid);

        @SuppressWarnings("unchecked")
        Map<String, String> response = restClient.post()
                .uri(scUrl + "/mintarAutorizacion")
                .body(body)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("txHash") && response.containsKey("tokenId")) {
            return new String[] { response.get("txHash"), response.get("tokenId") };
        }
        throw new RuntimeException("Error al mintar el NFT");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> retrieveMetadata(String cid) {
        System.out.println("[Spring] Recuperando metadata de IPFS para CID: " + cid);
        return restClient.get()
                .uri(ipfsUrl + "/recuperarMetadata/" + cid)
                .retrieve()
                .body(Map.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String transferNFT(String clientePrivateKey, String nuevoOwner, String tokenId) {
        System.out.println("[Spring] Transfiriendo NFT " + tokenId + " a " + nuevoOwner);
        Map<String, Object> body = new HashMap<>();
        body.put("clientePrivateKey", clientePrivateKey);
        body.put("nuevoOwner", nuevoOwner);
        body.put("tokenId", tokenId);

        Map<String, String> response = restClient.post()
                .uri(scUrl + "/transferirAutorizacion")
                .body(body)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("txHash")) {
            return response.get("txHash");
        }
        throw new RuntimeException("Error al transferir el NFT");
    }
}
