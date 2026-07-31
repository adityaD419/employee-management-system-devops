package com.example.newdemo.service;

import com.example.newdemo.configuration.JwtUtil;
import com.example.newdemo.model.AdminEntity;
import com.example.newdemo.model.TokenEntity;

import com.example.newdemo.repository.AdminRepository;
import com.example.newdemo.repository.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private JwtUtil jwtUtil; // This is your utility class for generating JWTs

	@Transactional
	public String generateAndSaveToken(Long adminId, String email) {
		try {
			// Generate JWT token
			String token = jwtUtil.generateToken(adminId, email);
			System.out.println("Generated Token: " + token);

			// Fetch the AdminEntity from the database
			Optional<AdminEntity> adminEntityOptional = adminRepository.findById(adminId);
			if (adminEntityOptional.isPresent()) {
				AdminEntity admin = adminEntityOptional.get();

				// Check if a token already exists for the admin
				Optional<TokenEntity> existingTokenOptional = tokenRepository.findByAdminId(adminId);

				if (existingTokenOptional.isPresent()) {
					// Update the existing token entity
					TokenEntity existingTokenEntity = existingTokenOptional.get();
					existingTokenEntity.setToken(token);
					existingTokenEntity.setExpiredAt(jwtUtil.getExpirationDate(token));
					existingTokenEntity.setUpdatedAt(LocalDateTime.now()); // Update timestamp

					// Save the updated token to the database
					tokenRepository.save(existingTokenEntity);
				} else {
					// Create a new TokenEntity if no existing token is found
					TokenEntity newTokenEntity = new TokenEntity();
					newTokenEntity.setToken(token);
					newTokenEntity.setExpiredAt(jwtUtil.getExpirationDate(token));
					newTokenEntity.setCreatedAt(LocalDateTime.now());
					newTokenEntity.setUpdatedAt(LocalDateTime.now());
					newTokenEntity.setAdmin(admin); // Associate with the admin

					// Save the new token entity to the database
					tokenRepository.save(newTokenEntity);
				}

				return token;
			} else {
				throw new RuntimeException("Admin not found");
			}
		} catch (Exception e) {
			// Log the exception
			System.err.println("Error generating or saving token: " + e.getMessage());
			throw e; // Rethrow to handle in the calling code
		}
	}

	public void saveToken(Long adminId, String token) {
		Optional<AdminEntity> adminOptional = adminRepository.findById(adminId);
		if (adminOptional.isPresent()) {
			AdminEntity admin = adminOptional.get();

			TokenEntity tokenEntity = new TokenEntity();
			tokenEntity.setToken(token);
			tokenEntity.setExpiredAt(jwtUtil.getExpirationDate(token)); // Set expiration date from the token
			tokenEntity.setAdmin(admin); // Set the user associated with the token

			tokenRepository.save(tokenEntity); // Save the token in the database
		}
	}

	public boolean validateToken(String token) {
		try {
			// Extract user email from the token
			String email = jwtUtil.extractEmail(token);

			// Fetch token entity from the database
			Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByToken(token);
			if (tokenEntityOptional.isPresent()) {
				TokenEntity tokenEntity = tokenEntityOptional.get();

				// Validate the token with JWT utility and check if it's not expired
				return jwtUtil.ValidateToken(token, email) && !tokenEntity.isExpired();
			}

			// Token not found in the database
			return false;
		} catch (Exception e) {
			// Handle exception (logging, etc.)
			return false;
		}
	}

}
