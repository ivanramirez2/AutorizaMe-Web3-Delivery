package com.example.autorizame_api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String descripcion;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @NotNull
    @Column(name = "id_autorizado", nullable = false)
    private Long idAutorizado;

    @NotNull
    @Column(name = "id_repartidor", nullable = false)
    private Long idRepartidor;

    @NotNull
    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "estado")
    private String estado; // ( CREADO, EN_REPARTO, ENTREGADO )

    @Column(name = "blockchain_cid")
    private String blockchainCid;

    @Column(name = "blockchain_tx_hash")
    private String blockchainTxHash;

    @Column(name = "blockchain_token_id")
    private String blockchainTokenId;

    public Pedido() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdAutorizado() {
        return idAutorizado;
    }

    public void setIdAutorizado(Long idAutorizado) {
        this.idAutorizado = idAutorizado;
    }

    public Long getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Long idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBlockchainCid() {
        return blockchainCid;
    }

    public void setBlockchainCid(String blockchainCid) {
        this.blockchainCid = blockchainCid;
    }

    public String getBlockchainTxHash() {
        return blockchainTxHash;
    }

    public void setBlockchainTxHash(String blockchainTxHash) {
        this.blockchainTxHash = blockchainTxHash;
    }

    public String getBlockchainTokenId() {
        return blockchainTokenId;
    }

    public void setBlockchainTokenId(String blockchainTokenId) {
        this.blockchainTokenId = blockchainTokenId;
    }

}
