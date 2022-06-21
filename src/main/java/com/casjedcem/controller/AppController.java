package com.casjedcem.controller;

import com.casjedcem.model.User;
import com.casjedcem.repository.UserRepository;
import com.casjedcem.service.DocumentService;
import com.casjedcem.service.UserServices;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

	@Autowired
	private UserServices service;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private DocumentService documentService;

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String processRegister(User user, 
		HttpServletRequest request, 
		BindingResult result, 
		Model model) 
			throws UnsupportedEncodingException, MessagingException {
		
		User userExists = service.findByEmail(user.getEmail());
           if (userExists != null) {
            return "redirect:/registeration?error";
        }
        if(result.hasErrors()){
            return "registration";
        }

		service.register(user, getSiteURL(request));		
		return "registration-success";
	}
	
	
	//-------------GET URL -------------------------//
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}	
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (service.verify(code)) {
			return "index";
		} else {
			return "verify_fail";
		}
	}
	
	@GetMapping("/admin/users/delete/{id}")
	public String deleProduct(@PathVariable("id") Long id){
	   repo.deleteById(id);
	   return "redirect:users";
	   
	}
	
	@PostMapping("/admin/users/update")
	public String updateProducteur(@RequestParam("id") Long id,
		                       @RequestParam("newPemail") String email,
		                       @RequestParam("newPnom") String nom,
				       @RequestParam("newPprenom") String prenom
				       )
	{
		service.change(id, email, nom, prenom);
		return "redirect:/admin/users";
	}
	
	@GetMapping("/login")
	public String viewLoginPage(){
		return "login";
	}
	
	@PostMapping("/research")
	public String search(Model model, @RequestParam("keyword") String keyword) {
		model.addAttribute("documentsFounded",documentService.getByKeyword(keyword));
		return "search";
	}
}
