package com.casjedcem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.casjedcem.model.Document;
import com.casjedcem.model.User;
import com.casjedcem.model.WishList;
import com.casjedcem.repository.WhishListRepository;
import com.casjedcem.service.DocumentService;
import com.casjedcem.service.UserServices;

@Controller
public class WhisListController {

	@Autowired
	WhishListRepository whishListRepository;

	@Autowired
	DocumentService documentService;

	@Autowired
	UserServices userService;

	@GetMapping("/favorite")
	public String viewFavories(Model model, @AuthenticationPrincipal Authentication authentication) {

		model.addAttribute("pageTitle", "Favori");
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}

		User user = userService.getCurrentlyLoggedInUser(authentication);
		if (user == null)
			return "/login";

		List<WishList> whishList = whishListRepository.findByUser(user);
		model.addAttribute("favorites", whishList);

		return "whishList";

	}

	@PostMapping("/favorite/add/{id}")
	public String addDocumentToFavorie(@PathVariable("pi") long documentId,
			@AuthenticationPrincipal Authentication authentication) {
		Document document = documentService.findById(documentId).get();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Vous devez vous authentifier pour ajouter un document en favoris.";
		}

		User user = userService.getCurrentlyLoggedInUser(authentication);

		if (user == null) {
			return "Vous devez vous authentifier pour ajouter un document aux favoris.";
		}

		WishList favoris = new WishList();
		favoris.setDocument(document);
		favoris.setUser(user);

		whishListRepository.save(favoris);

		return " document ajouté aux favoris.";

	}

	@PostMapping("/favorite/remove/{pid}")
	public String removeItem(@PathVariable("pid") long documentId,
			@AuthenticationPrincipal Authentication authentication) {
		// Document Document=productRepository.findById(productId).get();
		Document document = documentService.findById(documentId).get();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Vous devez vous authentifier pour supprimer un document.";
		}

		User user = userService.getCurrentlyLoggedInUser(authentication);

		if (user == null)
			return "Vous devez vous authentifier pour supprimer un document.";
		whishListRepository.deleteByUserAndDocument(user.getId(), document.getId());

		return "Le document a été supprimé avec succes";

	}
}
