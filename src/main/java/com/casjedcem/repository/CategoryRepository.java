package com.casjedcem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casjedcem.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Optional<Category> findById(long id);
	void deleteById(long id);

}
