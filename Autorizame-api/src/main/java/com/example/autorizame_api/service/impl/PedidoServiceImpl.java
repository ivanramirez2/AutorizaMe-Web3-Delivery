package com.example.autorizame_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.autorizame_api.model.Pedido;
import com.example.autorizame_api.repository.PedidoRepository;
import com.example.autorizame_api.service.PedidoService;
import com.example.autorizame_api.service.BlockchainService;
import com.example.autorizame_api.model.Cliente;
import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.repository.ClienteRepository;
import com.example.autorizame_api.repository.AutorizadoRepository;
import com.example.autorizame_api.exception.RecursoNoEncontradoException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final BlockchainService blockchainService;
    private final ClienteRepository clienteRepository;
    private final AutorizadoRepository autorizadoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, 
                             BlockchainService blockchainService,
                             ClienteRepository clienteRepository,
                             AutorizadoRepository autorizadoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.blockchainService = blockchainService;
        this.clienteRepository = clienteRepository;
        this.autorizadoRepository = autorizadoRepository;
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido con id " + id + " no encontrado"));
    }

    @Override
    public Pedido create(Pedido pedido) {
        if (pedido.getEstado() == null) {
            pedido.setEstado("CREADO");
        }
        
        // 1. Guardar primero para tener el ID
        System.out.println("\n[Spring API] Paso 1: Guardando pedido inicial en BBDD...");
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        System.out.println("[Spring API] Pedido guardado con ID: " + pedidoGuardado.getId());

        try {
            // 2. Obtener datos de cliente y autorizado para el metadata
            System.out.println("[Spring API] Paso 2: Recuperando datos de Cliente y Autorizado para el flujo Blockchain...");
            Cliente cliente = clienteRepository.findById(pedidoGuardado.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
            Autorizado autorizado = autorizadoRepository.findById(pedidoGuardado.getIdAutorizado())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autorizado no encontrado"));

            // 3. Subir metadata a IPFS
            System.out.println("[Spring API] Paso 3: Invocando Microservicio IPFS para subir metadata...");
            String cid = blockchainService.uploadMetadata(pedidoGuardado, cliente, autorizado);
            System.out.println("[Spring API] IPFS respondió con CID: " + cid);
            pedidoGuardado.setBlockchainCid(cid);

            // 4. Mintar NFT
            System.out.println("[Spring API] Paso 4: Invocando Microservicio de Smart Contracts para mintar NFT...");
            String[] blockchainData = blockchainService.mintNFT(pedidoGuardado, cliente, autorizado, cid);
            System.out.println("[Spring API] Smart Contract respondió - TxHash: " + blockchainData[0] + ", TokenID: " + blockchainData[1]);
            pedidoGuardado.setBlockchainTxHash(blockchainData[0]);
            pedidoGuardado.setBlockchainTokenId(blockchainData[1]);

            // 5. Actualizar pedido con datos de blockchain
            System.out.println("[Spring API] Paso 5: Actualizando pedido en BBDD con la información de Blockchain recibida...");
            Pedido finalizado = pedidoRepository.save(pedidoGuardado);
            System.out.println("[Spring API] ¡Persistencia completada correctamente!\n");
            return finalizado;

        } catch (Exception e) {
            System.err.println("[Spring API] ERROR en el flujo de blockchain: " + e.getMessage());
            e.printStackTrace();
            return pedidoGuardado;
        }
    }

    @Override
    public Pedido update(Long id, Pedido pedido) {
        Pedido existente = findById(id);

        existente.setDescripcion(pedido.getDescripcion());
        existente.setFecha(pedido.getFecha());
        existente.setIdCliente(pedido.getIdCliente());
        existente.setIdAutorizado(pedido.getIdAutorizado());
        existente.setIdRepartidor(pedido.getIdRepartidor());
        existente.setIdEmpresa(pedido.getIdEmpresa());
        existente.setEstado(pedido.getEstado());

        return pedidoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        Pedido existente = findById(id);
        pedidoRepository.delete(existente);
    }

    @Override
    public Long countByCliente(Long idCliente) {
        return pedidoRepository.countByIdCliente(idCliente);
    }

    @Override
    public List<Long> findAutorizadosIdsByCliente(Long idCliente) {
        return pedidoRepository.findAutorizadosIdsByCliente(idCliente);
    }

    @Override
    public Autorizado findAutorizadoByPedido(Pedido pedido) {
        return autorizadoRepository.findById(pedido.getIdAutorizado())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autorizado no encontrado para el pedido"));
    }
}
