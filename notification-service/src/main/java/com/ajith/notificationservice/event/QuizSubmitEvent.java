package com.ajith.notificationservice.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuizSubmitEvent {
    private int quiz_id;
    private int score;
}

