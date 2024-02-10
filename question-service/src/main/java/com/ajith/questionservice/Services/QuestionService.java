package com.ajith.questionservice.Services;



import com.ajith.questionservice.model.Question;
import com.ajith.questionservice.model.QuestionWrapper;
import com.ajith.questionservice.model.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {

    Question createQuestion (Question question);

     List< Question > getAllQuestions ( );

    ResponseEntity < Question> getQuestionById (Integer id);

    ResponseEntity< List< Question>> getALllQuestionsByCategory (String categoryName);

    ResponseEntity<List<Integer>> getQuestionsForQuiz (String categoryName, Integer numberOfQuestions);

    ResponseEntity< List< QuestionWrapper>> getQuestionsById (List< Integer> questionsIds);

    ResponseEntity< Integer> getScore (List< Response> responses);
}
