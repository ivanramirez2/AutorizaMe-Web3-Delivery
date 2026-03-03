package com.example.autorizame_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.autorizame_api.model.Repartidor;
import com.example.autorizame_api.repository.RepartidorRepository;
import com.example.autorizame_api.service.RepartidorService;
import com.example.autorizame_api.exception.RecursoNoEncontradoException;

@Service
public class RepartidorServiceImpl implements RepartidorService {

    private final RepartidorRepository repartidorRepository;

    public RepartidorServiceImpl(RepartidorRepository repartidorRepository) {
        this.repartidorRepository = repartidorRepository;
    }

    @Override
    public List<Repartidor> findAll() {
        return repartidorRepository.findAll();
    }

    @Override
    public Repartidor findById(Long id) {
        return repartidorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Repartidor con id " + id + " no encontrado"));
    }

    @Override
    public Repartidor create(Repartidor repartidor) {
        return repartidorRepository.save(repartidor);
    }

    @Override
    public Repartidor update(Long id, Repartidor repartidor) {
        Repartidor existente = findById(id);

        existente.setNombre(repartidor.getNombre());
        existente.setEmail(repartidor.getEmail());
        existente.setDireccion(repartidor.getDireccion());

        return repartidorRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        Repartidor existente = findById(id);
        repartidorRepository.delete(existente);
    }
}
