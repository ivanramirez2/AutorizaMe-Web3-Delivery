package com.example.autorizame_api.service;

import java.util.List;
import com.example.autorizame_api.model.Autorizado;



public interface AutorizadoService {

    List<Autorizado> findAll();

    Autorizado findById(Long id);

    Autorizado create(Autorizado autorizado);

    Autorizado update(Long id, Autorizado autorizado);

    void delete(Long id);

    List<Autorizado> findByIds(List<Long> ids);


}

    

