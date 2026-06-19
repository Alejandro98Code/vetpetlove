package com.certus.petlove.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.model.ERole;
import com.certus.petlove.project.service.AdminService;
import com.certus.petlove.project.service.CitaService;
import com.certus.petlove.project.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MascotaService mascotaService;
    private final CitaService citaService;

    @GetMapping("dashboard")
    public String dashboard(Model model){
        model.addAttribute("totalUsuarios", adminService.listarTodosUsuarios().size());
        model.addAttribute("totalMascotas", mascotaService.listarTodas().size());
        model.addAttribute("totalCitas", citaService.listadTodas().size());
        model.addAttribute("veterinarios", adminService.listarTodosUsuarios().size());
        return "admin/dashboard";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", adminService.listarTodosUsuarios());
        return "admin/usuarios";
    }

    @GetMapping("/veterinario/nuevo")
    public String formNuevoVeterinario(){
        return "admin/veterinario-form";
    }

    @PostMapping("/veterinario/nuevo")
    public String crearVeterinario(@RequestParam String nombre,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    @RequestParam String telefono,
                                    Model model){
        try{
            adminService.crearVeterinario(nombre, email, password, telefono);
            return "redirect:/admin/usuarios?creado=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/veterinario-form";
        }
    }

    @PostMapping("/usuario/{id}/rol")
    public String cambiarRol(@PathVariable Long id, @RequestParam ERole rol){
        adminService.cambiarRol(id, rol);
        return "redirect:/admin/usuarios?rolActualizado=true";
    }

    @PostMapping("/usuario/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id){
        adminService.eliminarUsuario(id);
        return "redirect:/admin/usuarios?eliminado=true";
    }

    @GetMapping("/mascotas")
    public String listarMascotas(Model model){
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "admin/mascotas";
    }

    @GetMapping("/citas")
    public String listarCitas(Model model){
        model.addAttribute("citas", citaService.listadTodas());
        return "admin/citas";
    }
    
}
