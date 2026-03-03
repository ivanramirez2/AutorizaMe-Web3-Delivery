package com.example.autorizame_api.service;

import java.util.Map;
import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.model.Cliente;
import com.example.autorizame_api.model.Pedido;

public interface BlockchainService {
    
    /**
     * Sube el metadata del pedido a IPFS a través del microservicio ms_wrapper_ipfs.
     * @return El CID (Hash IPFS) del metadata subido.
     */
    String uploadMetadata(Pedido pedido, Cliente cliente, Autorizado autorizado);

    /**
     * Minta el NFT de autorización a través del microservicio ms_wrapper_sc.
     * @param cid El CID del metadata obtenido previamente.
     * @return Un array con [txHash, tokenId].
     */
    String[] mintNFT(Pedido pedido, Cliente cliente, Autorizado autorizado, String cid);

    /**
     * Recupera el metadata alojado en IPFS/Pinata a partir de un CID.
     */
    Map<String, Object> retrieveMetadata(String cid);

    /**
     * Transfiere un token ya mintado a la cuenta de la persona autorizada.
     * @return El hash de la transacción.
     */
    String transferNFT(String clientePrivateKey, String nuevoOwner, String tokenId);
}
