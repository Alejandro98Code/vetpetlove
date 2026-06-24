package com.certus.petlove.project.service;

import java.util.HashSet;
import java.util.List;
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
public class AdminService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodosUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario crearVeterinario(String nombre, String email, String password, String telefono){
        if(usuarioRepository.existsByEmail(email)){
            throw new RuntimeException("El email ya esta registrado");
        }

        Role rolVet = roleRepository.findByName(ERole.ROLE_VET)
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));

        Set<Role> roles = new HashSet<>();
        roles.add(rolVet);

        Usuario vet = new Usuario();
        vet.setNombre(nombre);
        vet.setEmail(email);
        vet.setPassword(passwordEncoder.encode(password));
        vet.setTelefono(telefono);
        vet.setRoles(roles);

        return usuarioRepository.save(vet);
    }

    public void cambiarRol(Long usuarioId, ERole nuevoRol){
        Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Role rol = roleRepository.findByName(nuevoRol)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Set<Role> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> listarVeterinario(){
        return usuarioRepository.findByRolesName(ERole.ROLE_VET);
    }
    
}
