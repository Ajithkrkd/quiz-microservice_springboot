package com.ajith.quizservice.Controllers;
import com.ajith.quizservice.Dao.QuizDto;
import com.ajith.quizservice.Services.QuizService;
import com.ajith.quizservice.model.QuestionWrapper;
import com.ajith.quizservice.model.Quiz;
import com.ajith.quizservice.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/quiz")
@CircuitBreaker ( name = "quizService" ,fallbackMethod = "fallbackMethod")
public class QuizController {


    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategory (), quizDto.getTitle (), quizDto.getNumberOfQuestions ());
    }
    @GetMapping ("get/{id}")
    public ResponseEntity< List < QuestionWrapper > > getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List< Response > responses){
        return quizService.calculateResult(id, responses);
    }

    @GetMapping("all")
    public ResponseEntity<List< Quiz >>getAllQuizzes(){
        return quizService.getAllQuizzes();
    }


    public ResponseEntity<String> fallbackMethod(Exception e)
    {
        return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body (
                "question service is not available right now please try again later"
        );
    }
}
