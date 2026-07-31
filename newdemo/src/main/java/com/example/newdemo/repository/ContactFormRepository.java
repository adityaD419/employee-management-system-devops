package com.example.newdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.newdemo.model.ContactForm;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
    // You can define custom query methods if needed.
}
