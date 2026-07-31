package com.example.newdemo.repository;


import com.example.newdemo.model.AdminEntity;
import com.example.newdemo.model.TokenEntity;
import com.example.newdemo.model.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
	//  Optional<TokenEntity> findByUserId(UserEntity userId);
	 // Find token by AdminEntity
    Optional<TokenEntity> findByAdmin(AdminEntity admin);
    
    // Find token by adminId (adminId is now the foreign key to AdminEntity)
    Optional<TokenEntity> findByAdminId(Long adminId);
    
    // Find token by the token string itself
    Optional<TokenEntity> findByToken(String token);
}
