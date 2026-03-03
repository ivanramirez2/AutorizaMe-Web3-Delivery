package com.example.autorizame_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.autorizame_api.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    long countByIdCliente(Long idCliente);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT p.idAutorizado FROM Pedido p WHERE p.idCliente = :idCliente")
    java.util.List<Long> findAutorizadosIdsByCliente(
            @org.springframework.data.repository.query.Param("idCliente") Long idCliente);
}
