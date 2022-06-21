
package com.casjedcem.controller;

import com.casjedcem.model.Mail;
import com.casjedcem.model.PasswordForgot;
import com.casjedcem.model.PasswordResetToken;
import com.casjedcem.model.User;
import com.casjedcem.service.EmailService;
import com.casjedcem.service.PasswordResetTokenService;
import com.casjedcem.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgotpassword")
public class ForgotPasswordController {
    private final UserServices userService;
    private final MessageSource messageSource;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @Autowired
    public ForgotPasswordController(UserServices userService, MessageSource messageSource, PasswordResetTokenService passwordResetTokenService, EmailService emailService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @GetMapping
    public String viewPage(){
        return "lost-password";
    }

    @PostMapping
    public String processPasswordForgot(@Valid @ModelAttribute("passwordForgot") PasswordForgot passwordForgot,
                                        BindingResult result,
                                        Model model,
                                        RedirectAttributes attributes,
                                        HttpServletRequest request){
        if(result.hasErrors()){
            return "lost-password";
        }
        User user = userService.findByEmail(passwordForgot.getEmail());
        if(user == null){
            //model.addAttribute("emailError", messageSource.getMessage("EMAIL_NOT_FOUND", new Object[]{}, Locale.ENGLISH));
            return "lost-password";
        }
        // proceed to send email with link to reset password to this email address
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        token = passwordResetTokenService.save(token);
        if(token == null){
           // model.addAttribute("tokenError", messageSource.getMessage("TOKEN_NOT_SAVED", new Object[]{}, Locale.ENGLISH));
            return "lost-password";
        }
        Mail mail = new Mail();
        mail.setFrom("amandadjeunnang@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", token);
        mailModel.put("user", user);
        mailModel.put("signature", "http://bibliothèque.org");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailModel.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(mailModel);
        /* send email using emailService
        if email sent successfully redirect with flash attributes
         */
        emailService.send(mail);
        //attributes.addFlashAttribute("success", messageSource.getMessage("PASSWORD_RESET_TOKEN_SENT", new Object[]{}, Locale.ROOT));
        return "redirect:/forgotpassword";
    }

    @ModelAttribute("passwordForgot")
    public PasswordForgot passwordForgot(){
        return new PasswordForgot();
    }
}
