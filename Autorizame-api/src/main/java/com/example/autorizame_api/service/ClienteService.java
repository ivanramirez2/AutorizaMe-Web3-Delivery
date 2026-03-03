package com.example.autorizame_api.service;

import java.util.List;

import com.example.autorizame_api.model.Cliente;



public interface ClienteService {

    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente create(Cliente cliente);

    Cliente update(Long id, Cliente cliente);

    void delete(Long id);

}

    

