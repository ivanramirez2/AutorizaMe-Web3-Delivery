package com.example.autorizame_api.service;

import java.util.List;
import com.example.autorizame_api.model.Repartidor;



public interface RepartidorService {

    List<Repartidor> findAll();

    Repartidor findById(Long id);

    Repartidor create(Repartidor repartidor);

    Repartidor update(Long id, Repartidor repartidor);

    void delete(Long id);

}

    

