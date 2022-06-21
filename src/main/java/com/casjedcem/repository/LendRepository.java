package com.casjedcem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casjedcem.model.Document;
import com.casjedcem.model.Lend;
import com.casjedcem.model.User;

public interface LendRepository extends JpaRepository<Lend, Long>{

	public List<Lend> findByUser(User user);
	
	
}
