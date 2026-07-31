package com.example.newdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.newdemo.model.ContactForm;
import com.example.newdemo.repository.ContactFormRepository;


@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    public ContactForm saveContactForm(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }
    

    // Fetch all messages from the database
    public List<ContactForm> getAllMessages() {
        return contactFormRepository.findAll();
    }
}
