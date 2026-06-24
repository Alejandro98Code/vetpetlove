package com.certus.petlove.project.controller;

import com.certus.petlove.project.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService clienteService;

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nombre,
                           @RequestParam String apellido,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam(required = false) String telefono,
                           @RequestParam(required = false) String direccion,
                           Model model) {
        try {
            clienteService.registrar(nombre, apellido, email, password, telefono, direccion);
            return "redirect:/auth/login?registrado=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}