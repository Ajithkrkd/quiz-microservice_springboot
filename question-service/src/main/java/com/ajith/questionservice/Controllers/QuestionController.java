package com.ajith.questionservice.Controllers;

import com.ajith.questionservice.Services.QuestionService;
import com.ajith.questionservice.model.Question;
import com.ajith.questionservice.model.QuestionWrapper;
import com.ajith.questionservice.model.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<List< Question >> getAllQuestion(){
       List< Question > questions =  questionService.getAllQuestions();
        return ResponseEntity.status ( HttpStatus.OK ).body ( questions );
    }


    @PostMapping("/create")
    public String createQuestion(@RequestBody Question question){
        questionService.createQuestion ( question );
         return "successfully created";
    }

    @GetMapping("/{id}")
    public  ResponseEntity< Question > getQuestionById(@PathVariable ("id") Integer id){
       return  questionService.getQuestionById(id);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Question>> getALllQuestionsByCategory(@PathVariable String categoryName){
        return questionService.getALllQuestionsByCategory(categoryName);
    }

    //this endpoint will return the questions id with in the category for the quiz
    @GetMapping("/generate")

    public  ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName,@RequestParam Integer numberOfQuestions
    )
    {

       return questionService.getQuestionsForQuiz(categoryName,numberOfQuestions);
    }

    // we want a endpoint for return the questions for the quiz without right answer if the quiz service
    // given the ids then we can return the questions as per the id's
    @PostMapping("/getQuestionsById")
    public  List<QuestionWrapper>  getQuestionsById(
            @RequestBody List<Integer> questionsIds
    ){

        return questionService.getQuestionsById(questionsIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer>getScore(@RequestBody List< Response > responses)
    {
       return questionService.getScore(responses);
    }
}
