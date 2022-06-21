package com.casjedcem.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.casjedcem.dto.DocumentDTO;
import com.casjedcem.dto.LendDTO;
import com.casjedcem.model.Category;
import com.casjedcem.model.Document;
import com.casjedcem.model.Lend;
import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;
import com.casjedcem.service.CategoryService;
import com.casjedcem.service.DocumentService;
import com.casjedcem.service.LendService;
import com.casjedcem.service.ReservationService;
import com.casjedcem.service.UserServices;

@Controller
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/documentImages";
	@Autowired
	CategoryService categoryService;

	@Autowired
	DocumentService documentService;

	@Autowired
	UserServices userService;
	
	@Autowired
	LendService lendService;
	
	@Autowired
	ReservationService reservationService;

	@GetMapping("/admin")
	public String adminHome(Model model) {
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		//model.addAttribute("lastWeekCommand", commandService.getLastWeek());
		//model.addAttribute("thisMonthCommand", commandService.getLast30Days());
		//model.addAttribute("lastMonthCommand", commandService.getLastMonth());
		// model.addAttribute("produitRecent", documentService.getLast10Product());

		return "dashboard";
	}
	// gestion des categories

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		return "add-book-category";
	}

	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}

	@Transactional
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@PostMapping("/admin/categories/update/{id}")
	public String UpdateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "add-book-category";
		}
		return "484";
	}

	// gestion des documents

	@GetMapping("/documents")
	public String documents(Model model) {
		model.addAttribute("documents", documentService.getAllDocument());
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		return "get-all-book";
	}

	@GetMapping("/documents/add")
	public String DocumentAddGet(Model model) {
		model.addAttribute("documentDTO", new DocumentDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "add";
	}

	@PostMapping("/admin/documents/add")
	public String documentAddPost(@ModelAttribute("documentDTO") DocumentDTO documentDTO,
			@RequestParam(name = "documentImage") MultipartFile file, @RequestParam(name = "imgName") String imgName)
			throws IOException {
		Document document = new Document();
		document.setId(documentDTO.getId());
		document.setTitre(documentDTO.getTitre());
		document.setCategory(categoryService.getCategoryById(documentDTO.getCategoryID()).get());
		document.setAuteur(documentDTO.getAuteur());
		document.setISBN(documentDTO.getISBN());
		document.setDescription(documentDTO.getDescription());
		document.setQuantitéDispo(documentDTO.getQuantitéInit());
		document.setQuantitéInit(documentDTO.getQuantitéInit());
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		document.setImageName(imageUUID);

		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.HOUR_OF_DAY, 0);
		//cal.set(Calendar.MINUTE, 0);
		//cal.set(Calendar.SECOND, 0);
		//cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
		document.setDateCreate(date);
		documentService.addDocument(document);

		return "redirect:/documents";

	}
	@Transactional
	@GetMapping("/document/delete/{id}")
	public String deletedocument(@PathVariable long id) {
		documentService.removeDocumentById(id);
		return "redirect:/documents";
	}

	@GetMapping("/document/update/{id}")
	public String UpdateDocument(@PathVariable long id, Model model) {
		Document document = documentService.getDocumentById(id).get();
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setId(document.getId());
		documentDTO.setTitre(documentDTO.getTitre());
		documentDTO.setCategoryID(document.getCategory().getId());
		documentDTO.setAuteur(document.getAuteur());
		documentDTO.setISBN(document.getISBN());
		documentDTO.setDescription(document.getDescription());
		documentDTO.setQuantitéDispo(document.getQuantitéInit());
		documentDTO.setQuantitéInit(document.getQuantitéInit());
		documentDTO.setImageName(document.getImageName());

		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("documentDTO", documentDTO);
		return "redirect:/admin/documents/add";

	}
	
	@GetMapping("/catalogue")
	public String listDocumen(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("documents", documentService.getAllDocument());
		
		return "categorie";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userService.findUsersWithRoleUser();
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());

		return "get-all-users";
	}
	
	@GetMapping("/block-user/{id}")
	public String blockUser(@PathVariable long id , Model model) {
		User user = userService.findById(id).get();
		System.out.println(user);
		System.out.println(user.getEnable());
		user.setEnable(false);
		userService.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/unblock-user/{id}")
	public String unblockUser(@PathVariable long id, Model model) {
		User user = userService.findById(id).get();
		
		user.setEnable(true);
		return "redirect:/users";
	}
	
	@GetMapping("/lend")
	public String listLends(Model model) {
		//List<Lend> listLends = lendService.findAll();
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		model.addAttribute("listLends", lendService.findAll());
       return "get-all-lends";
	}
	
	@GetMapping("/lend/add")
	public String addLend(Model model) {
		model.addAttribute("lastUser", userService.getLastUser());
		model.addAttribute("countDocument", documentService.countDocument());
		model.addAttribute("numberOfLend", lendService.countLend());
		model.addAttribute("numberOfReservation", reservationService.countReservation());
		model.addAttribute("countUser", userService.countUsersWithRoleUser());
		model.addAttribute("lendDTO", new LendDTO());
		return "add-lend";
	}
	@PostMapping("/lend/add")
	public String addLendForm(@ModelAttribute("lendDTO") LendDTO lendDTO, 
			                      @RequestParam(name ="titre") String titre,
			                      @RequestParam( name = "ISBN") String ISBN) {
		
		User user = userService.findByEmail(lendDTO.getEmail());
		//System.out.println(user);
		Document document = documentService.findByISBN(lendDTO.getISBN());
	if (document.getQuantitéDispo()==0) {
		 return " document indisponible";
	} else {
		int qtté = document.getQuantitéDispo()-1;
		document.setQuantitéDispo(qtté);
		documentService.addDocument(document);
		//System.out.println(document);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		//System.out.println(date);
		Lend lend = new Lend();
		lend.setDateCreated(date);
		//System.out.println(lendDTO.getDateRetour());
		lend.setDateRetour(lendDTO.getDateRetour());
		lend.setDocument(document);
		lend.setUser(user);
		
		lendService.addLend(lend);
		return "add-lend";}
	}
	
	@GetMapping("/lend/add/{id}")
	public String getLendByUser(@PathVariable("id") long documentId) {
		//model.addAttribute("lendDTO", new LendDTO());
		return "add-lend";
	}
	

}
