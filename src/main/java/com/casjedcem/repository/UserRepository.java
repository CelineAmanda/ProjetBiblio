package com.casjedcem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casjedcem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	public User findByVerificationCode(String code);
	
	
	//public long countByRoles(Role roles);
	
	
	public User findFirstByOrderByDateCreateAsc();
	
	//public List<User> findByRoles(Role role);
	
	
}
