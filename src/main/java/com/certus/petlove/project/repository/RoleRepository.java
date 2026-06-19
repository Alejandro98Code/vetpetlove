package com.certus.petlove.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certus.petlove.project.model.ERole;
import com.certus.petlove.project.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> finByName(ERole name);
    
}
