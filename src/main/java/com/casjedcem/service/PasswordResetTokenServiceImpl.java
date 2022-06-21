
package com.casjedcem.service;

import com.casjedcem.model.PasswordResetToken;
import com.casjedcem.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author menra
 */
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

   private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository= passwordResetTokenRepository;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }	


}
