package com.casjedcem.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.casjedcem.model.Document;
import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;
import com.casjedcem.model.WishList;
import com.casjedcem.service.DocumentService;
import com.casjedcem.service.LendService;
import com.casjedcem.service.ReservationService;
import com.casjedcem.service.UserServices;

@Controller
public class ReservationController {

	@Autowired
	ReservationService reservationService;

	@Autowired
	UserServices userService;

	@Autowired
	DocumentService documentService;
	
	@Autowired
	LendService lendService;

	@GetMapping("/reservation")
	public String viewFavories(Model model
			//@AuthenticationPrincipal Authentication authentication
			) {

		/*model.addAttribute("pageTitle", "Reservation");
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}

		User user = userService.getCurrentlyLoggedInUser(authentication);
		if (user == null)
			return "/login";

		List<Reservation> reservation = reservationService.findByUser(user);
		model.addAttribute("reservation", reservation);*/
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
        model.addAttribute("reservations", reservationService.getAllReservation());
		return "get-all-reservation";

	}

	@GetMapping("/reservation/add/{id}")
	public String reserveDocument(@PathVariable("id") long documentId
			//@AuthenticationPrincipal Authentication authentication
			) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentlyLoggedInUser(authentication);
		Document document = documentService.findById(documentId).get();
	/*	if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			System.out.println("cas 1");
			return "login";
		}*/

	//	User user = userService.getCurrentlyLoggedInUser(authentication);
		System.out.println(user);
		if (user == null) {
			System.out.println(user);
			return "login";
		}
		if (document.getQuantitéDispo() == 0) {
			return "docIndisponible";
		} else if (!user.getEnable()) {
			return "Vous n'etes pas en regle";
		} else {
			Calendar cal = Calendar.getInstance();
			//cal.set(Calendar.HOUR_OF_DAY, 0);
			//cal.set(Calendar.MINUTE, 0);
			//cal.set(Calendar.SECOND, 0);
			//cal.set(Calendar.MILLISECOND, 0);
			Date date = cal.getTime();
			
			Reservation reservation = new Reservation();
			reservation.setDateCreate(date);
			reservation.setDocument(document);
			reservation.setUser(user);
			reservationService.addCategory(reservation);
		}
		return "categorie";

	}

	@GetMapping("/reservation/remove/{id}")
	public String remove(@PathVariable("id") long reservationId
			//, @AuthenticationPrincipal Authentication authentication
			) {
		// Document Document=productRepository.findById(productId).get();
		/*Document document = documentService.findById(documentId).get();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		User user = userService.getCurrentlyLoggedInUser(authentication);

		if (user == null) {
			return "login";
		}

		reservationService.deleteReservation(document.getId(), user.getId());*/
		System.out.println(reservationId);
		
		reservationService.removeReservationById(reservationId);

		return "profile";

	}
}
