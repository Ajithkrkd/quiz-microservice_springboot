package com.ajith.quizservice.event;

import lombok.*;




@Getter
@Setter
@ToString
public class QuizSubmitEvent {
    private String quiz_name;
    private String total_question;
    private int score;
    private String userEmail;
}
