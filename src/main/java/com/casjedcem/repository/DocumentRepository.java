package com.casjedcem.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.casjedcem.model.Category;
import com.casjedcem.model.Document;

public interface DocumentRepository extends JpaRepository<Document,Long>{

	List<Document> findAllByCategoryId(long id);
	
	List<Document> findAllByCategory(Category category);
	
	public Document findById(int id);

	public Optional<Document> findByTitre(String name);

	@Query("from Document where titre like %:keyword%")
	public List<Document> findByKeyword(@Param("keyword") String keyword); 
	
	public List<Document> findByDateCreateGreaterThan(Date dateCreate);
	
	public List<Document> findByDateCreateBetween(Date start, Date end);
	
	public List<Document> findByDateCreate(Date dateCreate);
	
	public Document findByISBN(String ISBN);
	
}
