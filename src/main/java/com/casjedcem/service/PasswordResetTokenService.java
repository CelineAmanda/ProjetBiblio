
package com.casjedcem.service;

import com.casjedcem.model.PasswordResetToken;

/**
 *
 * @author menra
 */
public interface PasswordResetTokenService {

    PasswordResetToken findByToken(String token);
    PasswordResetToken save(PasswordResetToken passwordResetToken);	
}
