
package com.casjedcem.controller;

import com.casjedcem.model.Reservation;
import com.casjedcem.model.User;
import com.casjedcem.service.CategoryService;
import com.casjedcem.service.LendService;
import com.casjedcem.service.ReservationService;
import com.casjedcem.service.UserServices;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyAccountController {
	
	@Autowired
	UserServices userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private LendService  lendService;
	
	    //--------------- MY ACCOUNT ---------------------//
    @GetMapping("/my-account")
    public String myAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentlyLoggedInUser(authentication);
        model.addAttribute("user", user);
      //  model.addAttribute("reservations", reservationService.getAllReservation());
       // List<Reservation> reservation = reservationService.findByUser(user);
        model.addAttribute("lends", lendService.findByUser(user));
		model.addAttribute("reservations", reservationService.findByUser(user));
        return "profile";
    }

    @PostMapping("/my-account/{id}")
    public String myAccountSave(@ModelAttribute User user,
    		@RequestParam Long id) {
       /* User user = userService.findById(id).get();
        System.out.println(user);
        if(email!=user.getEmail()) {
        	User userExist = userService.findByEmail(email);
        	if(userExist!=null)
        		return "account-edit";
        }*/
      System.out.println(user.getLastName());
        user.setEnable(true);
        userService.save(user);
        
        return "redirect:/my-account";
        
  
    }
    //--------------- END MY ACCOUNT ---------------------//
/*
    @RequestMapping("/order-history")
    public String orderHistory(Model model, Principal principal) {
        model.addAttribute("classActiveMyAccount", "home active");

        Customer customer = customerService.findByUsername(principal.getName());

        //customer orders
        model.addAttribute("orders", customer.getOrders());

        return "/my-account/order-history";
    }
    @RequestMapping("/order-details")
    public String orderDetails(@RequestParam("id") Long id, Principal principal, Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        Customer customer = customerService.findByUsername(principal.getName());

        Order order;
        try {
            order = orderService.get(id);

            //invalid order id
            if(order == null){
                model.addAttribute("error", "Order does not exits.");
                return "/my-account/order-details";
            }
            if(!order.getCustomer().equals(customer)){
                model.addAttribute("error", "Order did not belongs to you.");
                return "/my-account/order-details";
            }

            model.addAttribute("order", order);
            return "/my-account/order-details";

        }catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "/my-account/order-details";
        }
    }
*/
    @RequestMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("pageTitle", "Change password");

        return "/my-account/change-password";
    }
    @PostMapping("/change-password")
    public String saveChangePassword(@Valid
                                         @ModelAttribute("old_password") String oldPassword
                                        , @ModelAttribute("new_password") String newPassword
                                        , @ModelAttribute("confirm_password") String confirm_password
                                        , Principal principal
                                        , BindingResult result
                                        , Model model) {
        model.addAttribute("PageTitle", "Change Password");

        User user = userService.findByEmail(principal.getName());

        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            //match successfully
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);

            userService.save(user);
        }else {
            return "redirect:/change-password?error";
        }

        return "redirect:/change-password?success";
    }
}
