package com.certus.petlove.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.certus.petlove.project.model.HistorialMedico;
import com.certus.petlove.project.model.Mascota;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.repository.HistorialMedicoRepository;
import com.certus.petlove.project.repository.MascotaRepository;
import com.certus.petlove.project.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialMedicoService {

    private final HistorialMedicoRepository hisotrialRepository;
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    public HistorialMedico registrar(Long mascotaId, Long veterinarioId, String diagnostico, String tratamiento, String medicamento, Double peso, LocalDate proximaVisita){
        Mascota mascota = mascotaRepository.findById(mascotaId)
        .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
        .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        HistorialMedico historial = new HistorialMedico();
        historial.setFecha(LocalDate.now());
        historial.setMascota(mascota);
        historial.setVeterinario(veterinario);
        historial.setDiagnostico(diagnostico);
        historial.setTratamiento(tratamiento);
        historial.setMedicamentos(medicamento);
        historial.setPeso(peso);
        historial.setProximaVisita(proximaVisita);

        return hisotrialRepository.save(historial);
    }

    public List<HistorialMedico> listarPorMascota(Long mascotaId){
        return hisotrialRepository.findByMascotaIdOrderByFechaDesc(mascotaId);
    }

    public HistorialMedico buscarPorId(Long id){
        return hisotrialRepository.findById(id).
        orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public List<HistorialMedico> listarPorVeterinario(Long veterinarioId){
        return hisotrialRepository.findByVeterinarioId(veterinarioId);
    }

    
}
