package com.example.restapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restapi.model.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    // Buscar todos los check-ins de una reserva específica
    List<CheckIn> findByReservaId(Long reservaId);

    // Buscar check-ins por fecha de check-in
    @Query("SELECT c FROM CheckIn c WHERE c.fechaCheckIn = :fecha")
    List<CheckIn> findByFechaCheckIn(@Param("fecha") LocalDate fechaCheckIn);

    // Buscar check-ins por documento del huésped
    List<CheckIn> findByDocumentoNumero(String documentoNumero);

}