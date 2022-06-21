package com.casjedcem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casjedcem.model.Category;
import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;
import com.casjedcem.repository.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;

	public List<Reservation> findByUser(User user) {
		return reservationRepository.findByUser(user);
	}
	public int countReservation() {
		List<Reservation> liste = reservationRepository.findAll();
		return liste.size();
	}
	
	public Reservation addCategory(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	public void deleteReservation(long documentId , long userId) {
		reservationRepository.deleteByUserAndDocument(documentId, userId);
	}
	
	public List<Reservation> getAllReservation() {
		return reservationRepository.findAll();
	}
	
	public void removeReservationById(long id) {
		reservationRepository.deleteById(id);

	}
	

}
