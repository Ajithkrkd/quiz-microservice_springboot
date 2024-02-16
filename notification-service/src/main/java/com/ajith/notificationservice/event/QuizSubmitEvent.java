package com.ajith.notificationservice.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuizSubmitEvent {
    private String quiz_name;
    private String total_question;
    private int score;
    private String userEmail;
}

