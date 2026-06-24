package com.certus.petlove.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.model.Cita;
import com.certus.petlove.project.model.Mascota;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.service.CitaService;
import com.certus.petlove.project.service.ClienteService;
import com.certus.petlove.project.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;
    private final CitaService citaService;

    @GetMapping("/nueva")
    public String formNueva(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascota/form_mascota";
    }

    @PostMapping("/nueva")
    public String crear(@AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam String nombre,
                        @RequestParam String especie,
                        @RequestParam String raza,
                        @RequestParam LocalDate fechaNacimiento,
                        @RequestParam Double peso,
                        @RequestParam Mascota.Sexo sexo,
                        Model model) {
        try {
            Usuario cliente = clienteService.buscarPorEmail(userDetails.getUsername());
            mascotaService.crear(nombre, especie, raza, fechaNacimiento, peso, sexo, cliente.getId());
            return "redirect:/cliente/mascotas?creada=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("mascota", new Mascota());
            return "mascota/form_mascota";
        }
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Mascota mascota = mascotaService.buscarPorId(id);
        List<Cita> citas = citaService.listarPorMascota(id);
        model.addAttribute("mascota", mascota);
        model.addAttribute("citas", citas);
        return "mascota/lista_mascota";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mascota/form_mascota";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @RequestParam String nombre,
                             @RequestParam String especie,
                             @RequestParam String raza,
                             @RequestParam Double peso,
                             Model model) {
        try {
            mascotaService.actualizar(id, nombre, especie, raza, peso);
            return "redirect:/mascotas/" + id + "?actualizada=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("mascota", mascotaService.buscarPorId(id));
            return "mascota/form_mascota";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return "redirect:/cliente/mascotas?eliminada=true";
    }
}