package com.certus.petlove.project.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.model.EstadoCita;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.service.AdminService;
import com.certus.petlove.project.service.CitaService;
import com.certus.petlove.project.service.ClienteService;
import com.certus.petlove.project.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;
    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final AdminService adminService;

    // GET /cita/agendar  Y también /citas/nueva (alias para compatibilidad con el HTML)
    @GetMapping({"/agendar", "/nueva"})
    public String formAgendar(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(required = false) Long mascotaId,
                              Model model) {
        Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("mascotas", mascotaService.listarPorDueno(cliente.getId()));
        model.addAttribute("veterinarios", adminService.listarVeterinario());
        model.addAttribute("mascotaIdSeleccionada", mascotaId);
        return "cita/agendar";
    }

    @PostMapping({"/agendar", "/nueva"})
    public String agendar(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam Long mascotaId,
                          @RequestParam Long veterinarioId,
                          @RequestParam LocalDateTime fechaHora,
                          @RequestParam String motivo,
                          Model model) {
        try {
            citaService.agendar(mascotaId, veterinarioId, fechaHora, motivo);
            return "redirect:/cliente/citas?agendada=true";
        } catch (RuntimeException e) {
            Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
            model.addAttribute("mascotas", mascotaService.listarPorDueno(cliente.getId()));
            model.addAttribute("veterinarios", adminService.listarVeterinario());
            model.addAttribute("error", e.getMessage());
            return "cita/agendar";
        }
    }

    @GetMapping("/todas")
    public String todasLasCitas(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario vet = clienteService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("citas", citaService.listarPorVeterinario(vet.getId()));
        return "cita/lista";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam EstadoCita estado) {
        citaService.cambiarEstado(id, estado);
        return "redirect:/cita/todas?actualizada=true";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return "redirect:/cliente/citas?cancelada=true";
    }
}