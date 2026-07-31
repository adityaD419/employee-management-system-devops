package com.example.newdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.newdemo.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
 // You can add custom queries here if needed
}
