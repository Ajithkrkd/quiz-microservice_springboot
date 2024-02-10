package com.ajith.quizservice.feign;

import com.ajith.quizservice.model.QuestionWrapper;
import com.ajith.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    //this endpoint will return the questions id with in the category for the quiz
    @GetMapping ("/question/generate")
    public ResponseEntity < List <Integer> > getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numberOfQuestions
    );

    // we want a endpoint for return the questions for the quiz without right answer if the quiz service
    // given the ids then we can return the questions as per the id's
    @PostMapping ("/question/getQuestionsById")
    public ResponseEntity< List< QuestionWrapper > > getQuestionsById(
            @RequestBody List<Integer> questionsIds
    );

    @PostMapping("/question/getScore")
    public ResponseEntity<Integer>getScore(@RequestBody List< Response > responses);
}
