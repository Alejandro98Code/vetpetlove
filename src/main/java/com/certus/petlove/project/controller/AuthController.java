package com.certus.petlove.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certus.petlove.project.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService clienteService;

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nombre,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String telefono,
                            @RequestParam String direccion,
                            Model model){
        try {
            clienteService.registrar(nombre, email, password, telefono, direccion);
            return "redirect:/auth/login?registrado=true";
        } catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
    
}
