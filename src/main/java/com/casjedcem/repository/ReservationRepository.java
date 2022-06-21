package com.casjedcem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	List<Reservation> findByUser(User user);
	
	@Query("DELETE FROM Reservation c WHERE c.user.id = ?1 AND c.document.id = ?2")
	@Modifying
	public void deleteByUserAndDocument(Long userId, long documentId);
}
