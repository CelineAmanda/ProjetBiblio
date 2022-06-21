package com.casjedcem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casjedcem.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
 
	public Role findByRole(String role);
}
