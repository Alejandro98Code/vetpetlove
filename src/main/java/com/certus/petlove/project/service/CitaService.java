package com.certus.petlove.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.certus.petlove.project.model.Cita;
import com.certus.petlove.project.model.EstadoCita;
import com.certus.petlove.project.model.Mascota;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.repository.CitaRepository;
import com.certus.petlove.project.repository.MascotaRepository;
import com.certus.petlove.project.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    public Cita agendar(Long mascotaId, Long veterinarioId, LocalDateTime fechaHora, String motivo){
        Mascota mascota = mascotaRepository.findById(veterinarioId)
        .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
        .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        Cita cita = new Cita();
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);
        cita.setFechaHora(fechaHora);
        cita.setMotivo(motivo);
        cita.setEstado(EstadoCita.PENDIENTE);

        return citaRepository.save(cita);
    }

    public Cita cambiarEstado(Long citaId, EstadoCita nueEstado){
        Cita cita = citaRepository.findById(citaId)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

            cita.setEstado(nueEstado);

            return citaRepository.save(cita);
    }

    public List<Cita> listarPorMascota(Long mascotaId){
        return citaRepository.findByMascotaId(mascotaId);
    }

    public List<Cita> listarPorVeterinario(Long veterinarioId){
        return citaRepository.findByVeterinarioId(veterinarioId);
    }

    public List<Cita> listarPorDueno(Long duenioId){
        return citaRepository.findByMascotaDuenioId(duenioId);
    }
    
    public List<Cita> listadTodas(){
        return citaRepository.findAll();
    }

    public void cancelar(Long citaId){
        cambiarEstado(citaId, EstadoCita.CANCELADA);
    }
    
}
