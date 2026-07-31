package com.example.newdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.newdemo.model.CompanyStatics;

@Repository
public interface CompanyStaticsRepository extends JpaRepository<CompanyStatics, Long> {
    
}
