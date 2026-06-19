package com.certus.petlove.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.certus.petlove.project.model.Mascota;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.repository.MascotaRepository;
import com.certus.petlove.project.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    public Mascota crear(String nombre, String especie, String raza, LocalDate fechaNacimiento, Double peso, Mascota.Sexo sexo, Long duenioId){
        Usuario dueno = usuarioRepository.findById(duenioId)
        .orElseThrow(() -> new RuntimeException("Dueno no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setPeso(peso);
        mascota.setSexo(sexo);
        mascota.setDuenio(dueno);

        return mascotaRepository.save(mascota);
    }
    
    public List<Mascota> listarPorDueno(Long duenioId){
        return mascotaRepository.findByDuenoId(duenioId);
    }

    public Mascota buscarPorId(Long id){
        return mascotaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    public Mascota actualizar(Long id, String nombre, String especie, String raza, Double peso){
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setPeso(peso);

        return mascotaRepository.save(mascota);
    }

    public void eliminar(Long id){
        mascotaRepository.deleteById(id);
    }

    public List<Mascota> listarTodas(){
        return mascotaRepository.findAll();
    }
    
    
}
