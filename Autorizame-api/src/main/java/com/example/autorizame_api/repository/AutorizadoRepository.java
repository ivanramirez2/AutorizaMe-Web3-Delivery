package com.example.autorizame_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.autorizame_api.model.Autorizado;

@Repository
public interface AutorizadoRepository extends JpaRepository<Autorizado, Long> {
}
