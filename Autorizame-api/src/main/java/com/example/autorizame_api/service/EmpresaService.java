package com.example.autorizame_api.service;

import java.util.List;
import com.example.autorizame_api.model.Empresa;



public interface EmpresaService {

    List<Empresa> findAll();

    Empresa findById(Long id);

    Empresa create(Empresa empresa);

    Empresa update(Long id, Empresa empresa);

    void delete(Long id);

}

    

