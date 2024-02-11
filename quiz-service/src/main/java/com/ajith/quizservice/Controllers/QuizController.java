package com.ajith.quizservice.Controllers;

import com.ajith.quizservice.Dao.QuizDto;
import com.ajith.quizservice.Services.QuizService;
import com.ajith.quizservice.model.QuestionWrapper;
import com.ajith.quizservice.model.Quiz;
import com.ajith.quizservice.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping ("/quiz")
public class QuizController {


    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){

        return quizService.createQuiz(quizDto.getCategory (), quizDto.getTitle (), quizDto.getNumberOfQuestions ());
    }
    int count = 1;
    @GetMapping ("get/{id}")
    @CircuitBreaker ( name = "quizService")
    @TimeLimiter(name = "quizService")
    @Retry ( name = "quizService" ,fallbackMethod = "fallbackMethod")
    public CompletableFuture <List<QuestionWrapper>>getQuizQuestions(@PathVariable Integer id) {
        System.out.println("retrying create method  -------       " + count++ + "   +------------------------    times            " + new Date());
        return CompletableFuture.supplyAsync (()->quizService.getQuizQuestions(id));
    }
    public CompletableFuture<List<QuestionWrapper>> fallbackMethod(Exception e) {
        List<QuestionWrapper> dummyData = new ArrayList<>();
        dummyData.add(new QuestionWrapper(1,"service not available","1","2","3" ,"4"));
        return CompletableFuture.supplyAsync (()->dummyData);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List< Response > responses){
        return quizService.calculateResult(id, responses);
    }

    @GetMapping("all")
    public ResponseEntity<List< Quiz >>getAllQuizzes(){
        return quizService.getAllQuizzes();
    }



}
