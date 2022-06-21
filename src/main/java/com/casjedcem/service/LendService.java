package com.casjedcem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casjedcem.model.Document;
import com.casjedcem.model.Lend;
import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;
import com.casjedcem.repository.LendRepository;

@Service
public class LendService {

	@Autowired
	LendRepository lendRepository;
	
	public void addLend(Lend lend) {
		lendRepository.save(lend);
	}
	public List<Lend> findByUser(User user) {
		return lendRepository.findByUser(user);
	}
	
	public int countLend() {
		List<Lend> liste = lendRepository.findAll();
		return liste.size();
	}
	
	public List<Lend> findAll(){
		return lendRepository.findAll()
;	}
}
