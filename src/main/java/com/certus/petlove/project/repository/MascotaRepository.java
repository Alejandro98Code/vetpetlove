package com.certus.petlove.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certus.petlove.project.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByDuenioId(Long duenioId);

    List<Mascota> findByNombreContainingIgnoreCase(String nombre);

    List<Mascota> findByEspecie(String especie);

    List<Mascota> findByDuenioIdAndEspecie(Long duenioId, String especie);
}
