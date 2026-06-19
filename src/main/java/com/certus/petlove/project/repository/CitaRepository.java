package com.certus.petlove.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certus.petlove.project.model.Cita;
import com.certus.petlove.project.model.EstadoCita;
import java.time.LocalDateTime;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByMascotaId(Long mascotaId);

    List<Cita> findByVeterinarioId(Long veterinarioId);

    List<Cita> findByMascotaDuenioId(Long duenioId);

    List<Cita> findByEstado(EstadoCita estado);

    List<Cita> findByVeterinarioIdAndEstado(Long veterinarioId, EstadoCita estado);

    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Cita> findByVeterinarioIdAndFechaHoraBetween(
        Long veterinarioId,
        LocalDateTime inicio,
        LocalDateTime fin
    );
}
