package com.example.autorizame_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.autorizame_api.model.Autorizado;
import com.example.autorizame_api.repository.AutorizadoRepository;
import com.example.autorizame_api.service.AutorizadoService;
import com.example.autorizame_api.exception.RecursoNoEncontradoException;

@Service
public class AutorizadoServiceImpl implements AutorizadoService {

    private final AutorizadoRepository autorizadoRepository;

    public AutorizadoServiceImpl(AutorizadoRepository autorizadoRepository) {
        this.autorizadoRepository = autorizadoRepository;
    }

    @Override
    public List<Autorizado> findAll() {
        return autorizadoRepository.findAll();
    }

    @Override
    public Autorizado findById(Long id) {
        return autorizadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autorizado con id " + id + " no encontrado"));
    }

    @Override
    public Autorizado create(Autorizado autorizado) {
        return autorizadoRepository.save(autorizado);
    }

    @Override
    public Autorizado update(Long id, Autorizado autorizado) {
        Autorizado existente = findById(id);

        existente.setNombre(autorizado.getNombre());
        existente.setEmail(autorizado.getEmail());
        existente.setAddress(autorizado.getAddress());

        return autorizadoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!autorizadoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Autorizado con id " + id + " no encontrado");
        }
        autorizadoRepository.deleteById(id);
    }

    @Override
    public List<Autorizado> findByIds(List<Long> ids) {
        return autorizadoRepository.findAllById(ids);
    }
}
