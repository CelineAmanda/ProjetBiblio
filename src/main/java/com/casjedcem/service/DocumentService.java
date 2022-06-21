package com.casjedcem.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casjedcem.model.Document;
import com.casjedcem.repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	public List<Document> getAllDocument() {
		return documentRepository.findAll();
	}
	public Document findByISBN(String ISBN) {
		return documentRepository.findByISBN(ISBN);
	}

	public void addDocument(Document document) {
		documentRepository.save(document);
	}

	public void removeDocumentById(Long id) {
		documentRepository.deleteById(id);

	}
	
	public int countDocument() {
		List<Document> liste = documentRepository.findAll();
		return liste.size();
	}
	
	public List<Document> getByKeyword(String keyword){
		  return documentRepository.findByKeyword(keyword);
		 }

	public Optional<Document> getDocumentById(Long id) {
		return documentRepository.findById(id);
	}

	public List<Document> getAllDocumentByCategoryId(long id) {
		return documentRepository.findAllByCategoryId(id);
	}

	public Optional<Document> getDocumentByTitre(String name) {
		return documentRepository.findByTitre(name);
	}

	public List<Document> getLast7Days() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dateBefore7Days = cal.getTime();

		return documentRepository.findByDateCreateGreaterThan(dateBefore7Days);

	}

	public List<Document> getLast30Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dateBefore30Days = cal.getTime();

		return documentRepository.findByDateCreateGreaterThan(dateBefore30Days);
	}

	public List<Document> getLastMonth() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date end = cal.getTime();

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -60);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Date start = cal1.getTime();

		return documentRepository.findByDateCreateBetween(start, end);

	}

	public List<Document> getLastWeek() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -8);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date end = cal.getTime();

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -14);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Date start = cal1.getTime();

		return documentRepository.findByDateCreateBetween(start, end);
	}

	public List<Document> getToday() {
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -8);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();

		return documentRepository.findByDateCreateGreaterThan(today);
	}

	public Optional<Document> findById(long productId) {

		return documentRepository.findById(productId);

	}

}
