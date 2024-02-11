package com.ajith.quizservice.event;

import lombok.*;




@Getter
@Setter
@ToString
public class QuizSubmitEvent {
    private int quiz_id;
    private int score;
}
