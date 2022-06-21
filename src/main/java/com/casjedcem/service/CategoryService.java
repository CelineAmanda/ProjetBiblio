package com.casjedcem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casjedcem.model.Category;
import com.casjedcem.repository.CategoryRepository;
import com.casjedcem.repository.DocumentRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	DocumentRepository repo;

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	public void addCategory(Category category) {
		categoryRepository.save(category);
	}

	public void removeCategoryById(long id) {
		categoryRepository.deleteById(id);

	}
	
	public Optional<Category> getCategoryById(long id) {
		return categoryRepository.findById(id);
	}
	
	
}
