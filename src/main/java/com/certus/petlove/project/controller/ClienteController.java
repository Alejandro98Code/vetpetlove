package com.certus.petlove.project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.service.CitaService;
import com.certus.petlove.project.service.ClienteService;
import com.certus.petlove.project.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final CitaService citaService;

    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("cliente", cliente);
        return "cliente/perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam String nombre,
                                    @RequestParam String telefono,
                                    @RequestParam String direccion,
                                    Model model){
        try {
            Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
            clienteService.actualizarPerfil(cliente.getId(), nombre, telefono, direccion);
            return "redirect:/cliente/perfil?actualizado=true";
        } catch(RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "cliente/perfil";
        }
    }

    @GetMapping("/mascotas")
    public String mascotas(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("mascotas", mascotaService.listarPorDueno(cliente.getId()));
        return "cliente/mascotas";
    }

    @GetMapping("/citas")
    public String citas(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
        model.addAttribute("citas", citaService.listarPorDueno(cliente.getId()));
        return "cita/lista";
    }
}