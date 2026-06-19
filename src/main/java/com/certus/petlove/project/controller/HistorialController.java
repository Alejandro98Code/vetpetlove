package com.certus.petlove.project.controller;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.service.ClienteService;
import com.certus.petlove.project.service.HistorialMedicoService;
import com.certus.petlove.project.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialMedicoService historialService;
    private final MascotaService mascotaService;
    private final ClienteService clienteService;

    @GetMapping("/mascota/{mascotaId}")
    public String verHistorial(@PathVariable Long mascotaId, Model model){
        model.addAttribute("mascota", mascotaService.buscarPorId(mascotaId));
        model.addAttribute("historial", historialService.listarPorMascota(mascotaId));
        return "historial/lista";
    }

    @GetMapping("/nuevo/{mascotaId}")
    public String formNuevo(@PathVariable Long mascotaId, Model model){
        model.addAttribute("mascota", mascotaService.buscarPorId(mascotaId));
        return "historial/formulario";
    }

    @PostMapping("/nuevo/{mascotaId}")
    public String registrar(@PathVariable Long mascotaId,
                                @AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam String diagnostico,
                        @RequestParam String tratamiento,
                    @RequestParam String medicamento,
                @RequestParam Double peso,
            @RequestParam LocalDate proximaVisita,
        Model model){
        try {
            Usuario vet = clienteService.buscarPorEmail(userDetails.getUsername());
            historialService.registrar(mascotaId, vet.getId(), diagnostico, tratamiento, medicamento, peso, proximaVisita);
            return "redirect:/historial/mascota/" + mascotaId + "?registrado=true";
        } catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            return "historial/formulario";
        }
    }
    
}
