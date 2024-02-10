package com.ajith.quizservice.Dao;

import lombok.Data;

@Data
public class QuizDto {
    private String title;
    private String category;
    private Integer numberOfQuestions;
}
