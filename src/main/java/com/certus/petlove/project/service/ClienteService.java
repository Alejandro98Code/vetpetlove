package com.certus.petlove.project.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.certus.petlove.project.model.ERole;
import com.certus.petlove.project.model.Role;
import com.certus.petlove.project.model.Usuario;
import com.certus.petlove.project.repository.RoleRepository;
import com.certus.petlove.project.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(String nombre, String email, String password, String telefono, String direccion){
        if(usuarioRepository.existsByEmail(email)){
            throw new RuntimeException("El email ya esta registrado");
        }

        Role rolCliente = roleRepository.finByName(ERole.ROLE_CLIENTE)
        .orElseThrow(()-> new RuntimeException("Role no encontrado"));

        Set<Role> roles = new HashSet<>();
        roles.add(rolCliente);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email).
        orElseThrow(()-> new RuntimeException("Email no encontraod"));
    }

    public Usuario actualizarPerfin(Long id, String nombre, String telefono, String direccion){
        Usuario usuario = usuarioRepository.findById(id).
        orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);

        return usuarioRepository.save(usuario);
    }







}
