package com.example.autorizame_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.autorizame_api.model.Cliente;
import com.example.autorizame_api.repository.ClienteRepository;
import com.example.autorizame_api.service.ClienteService;
import com.example.autorizame_api.exception.RecursoNoEncontradoException;

/**
 * Implementación del servicio de Clientes usando JPA Repository.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Devuelve todos los clientes del sistema.
     *
     * @return lista de clientes
     */
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws RecursoNoEncontradoException si no existe
     */
    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no encontrado"));
    }

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param cliente cliente a crear
     * @return cliente creado con ID asignado
     */
    @Override
    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param id      identificador del cliente
     * @param cliente nuevos datos del cliente
     * @return cliente actualizado
     * @throws RecursoNoEncontradoException si no existe
     */
    @Override
    public Cliente update(Long id, Cliente cliente) {
        Cliente existente = findById(id);

        existente.setNombre(cliente.getNombre());
        existente.setEmail(cliente.getEmail());
        existente.setAddress(cliente.getAddress());

        return clienteRepository.save(existente);
    }

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @throws RecursoNoEncontradoException si no existe
     */
    @Override
    public void delete(Long id) {
        Cliente existente = findById(id);
        clienteRepository.delete(existente);
    }
}
