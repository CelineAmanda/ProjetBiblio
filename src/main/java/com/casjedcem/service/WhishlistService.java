package com.casjedcem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casjedcem.model.User;
import com.casjedcem.model.WishList;
import com.casjedcem.repository.WhishListRepository;

@Service
public class WhishlistService {
	@Autowired
	WhishListRepository whishListRepository;

	public List<WishList> getUser(User user) {
		return whishListRepository.findByUser(user);
	}

	public void deleteByUserAndDocument(long userId, long documentId) {
		whishListRepository.deleteByUserAndDocument(userId, documentId);
	}

}
