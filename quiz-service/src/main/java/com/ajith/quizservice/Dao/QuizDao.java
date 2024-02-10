package com.ajith.quizservice.Dao;



import com.ajith.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository< Quiz,Integer> {
}