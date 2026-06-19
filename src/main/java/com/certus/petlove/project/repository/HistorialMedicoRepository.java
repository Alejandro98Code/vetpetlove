package com.certus.petlove.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certus.petlove.project.model.HistorialMedico;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long>{

    List<HistorialMedico> findByMascotaIdOrderByFechaDesc(Long mascotaId);

    List<HistorialMedico> findByVeterinarioId(Long veterinarioId);

    List<HistorialMedico> findByMascotaIdAndFechaBetween(
        Long mascotaId,
        LocalDate inicio,
        LocalDate fin
    );
    
}
