package com.casjedcem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.casjedcem.service.CategoryService;
import com.casjedcem.service.DocumentService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	DocumentService documentService;

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		// model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("category", categoryService.getAllCategory());
		// model.addAttribute("cartProduct", GlobalData.cart);

		return "index";
	}

	@GetMapping("/catalog")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("documents", documentService.getAllDocument());
		// model.addAttribute("cartCount", GlobalData.cart.size());
		return "catalog";
	}

	@GetMapping("/catalog/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		// model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("documents", documentService.getAllDocumentByCategoryId(id));
		return "catalog";
	}

	@GetMapping("/catalog/viewdocument/{id}")
	public String ViewProduct(Model model, @PathVariable Long id) {
		model.addAttribute("documents", documentService.getDocumentById(id));
		// model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}

}
