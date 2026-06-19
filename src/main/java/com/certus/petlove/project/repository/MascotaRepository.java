package com.certus.petlove.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certus.petlove.project.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByDuenoId(Long duenoId);

    List<Mascota> findByNombreContainingIgnoreCase(String nombre);

    List<Mascota> findByEspecie(String especie);

    List<Mascota> finfByDuenioIdAndEspecie(Long duenioId, String especie);
}
