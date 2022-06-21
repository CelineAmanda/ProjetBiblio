package com.casjedcem.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casjedcem.model.Role;
import com.casjedcem.model.User;
import com.casjedcem.repository.RoleRepository;
import com.casjedcem.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServices {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private RoleRepository roleRepository;

	public List<User> listAll() {
		return repo.findAll();
	}
	
	public int countUsersWithRoleUser(){
		int numbOfUsersWithUserRole = 0;
		final Role roleUser = roleRepository.findByRole("USER");
		List<User> allUsers =  repo.findAll();
		for(User user:allUsers) {
			if(user.getRoles().contains(roleUser)) {
				numbOfUsersWithUserRole++;
			}
		}
		return numbOfUsersWithUserRole;
	}
	public List<User> findUsersWithRoleUser(){
		final Role roleUser = roleRepository.findByRole("USER");
		List<User> allUsers =  repo.findAll();
		List<User> usersWithUserRole = new ArrayList<User>();
		for(User user:allUsers) {
			if(user.getRoles().contains(roleUser)) {
				usersWithUserRole.add(user);
			}
		}
		return usersWithUserRole;
	}

	
	public void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role roleUser = roleRepository.findByRole("USER");
		// Date date = new Date();
		// user.addRole(
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		Calendar cal = Calendar.getInstance();
		/*
		 * cal.add(Calendar.DATE, -8); cal.set(Calendar.HOUR_OF_DAY, 0);
		 * cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0);
		 * cal.set(Calendar.MILLISECOND, 0);
		 */
		Date now = cal.getTime();
		user.setDateCreate(now);
		user.addRole(roleUser);
		user.setEnabled(false);

		repo.save(user);

		sendVerificationEmail(user, siteURL);
	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "amandadjeunnang@gmail.com";
		String senderName = "CASJEDCEM";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "CASJEDCEM.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFullName());
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}

	public boolean verify(String verificationCode) {
		User user = repo.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			repo.save(user);

			return true;
		}

	}

	public void change(Long id, String email, String nom, String prenom) {
		User user = repo.findById(id).get();
		user.setEmail(email);
		user.setFirstName(nom);
		user.setLastName(prenom);
		repo.save(user);
	}

	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return repo.findById(id);
	}

	@Modifying
	public void updatePassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.save(user);
	}

	public User getLastUser() {

		return repo.findFirstByOrderByDateCreateAsc();
	}

	/*public long countUser() {
		return repo.countByRoles(Role.USER);
	}*/

	public User getCurrentlyLoggedInUser(Authentication authentication) {
		if (authentication == null)
			return null;

		User user = null;
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails) {
			user = repo.findByEmail(((UserDetails) principal).getUsername());
		}

		return user;
	}

	//public List<User> findAllUser() {
		//return repo.findByRoles(Role.USER);
	//}

	public void save(User user) {
		repo.save(user);
	}

}
