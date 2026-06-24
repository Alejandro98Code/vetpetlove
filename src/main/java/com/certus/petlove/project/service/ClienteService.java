package com.certus.petlove.project.service;

import com.certus.petlove.project.model.*;
import com.certus.petlove.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(String nombre, String apellido, String email,
                             String password, String telefono,
                             String direccion) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        Role rolCliente = roleRepository.findByName(ERole.ROLE_CLIENTE)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Set<Role> roles = new HashSet<>();
        roles.add(rolCliente);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Usuario actualizarPerfil(Long id, String nombre,
                                    String telefono, String direccion) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);

        return usuarioRepository.save(usuario);
    }
}