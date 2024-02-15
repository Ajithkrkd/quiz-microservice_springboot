package com.ajith.quizservice.Services;

import com.ajith.quizservice.Dao.QuizDao;
import com.ajith.quizservice.event.QuizSubmitEvent;
import com.ajith.quizservice.feign.QuizInterface;
import com.ajith.quizservice.kafka.KafkaJsonProducer;
import com.ajith.quizservice.model.QuestionWrapper;
import com.ajith.quizservice.model.Quiz;
import com.ajith.quizservice.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizService {


    private final QuizDao quizDao;
    private final QuizInterface quizInterface;
    private final KafkaJsonProducer producer;
    private final KafkaTemplate<String,QuizSubmitEvent> kafkaTemplate;

    public ResponseEntity <String> createQuiz(String category,  String title,int numQ) {

        List < Integer > questions = quizInterface.getQuestionsForQuiz (category, numQ).getBody ();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds (questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }


    @Transactional(readOnly = true)
    public List< QuestionWrapper >getQuizQuestions(Integer id) {

        Optional<Quiz> optionalQuiz = quizDao.findById ( id );
        if ( optionalQuiz.isPresent() )
        {
            Quiz quiz = optionalQuiz.get();
            List<Integer> questionIds = quiz.getQuestionsIds ();

            return quizInterface.getQuestionsById(questionIds).getBody();

        }
        else {
            return new ArrayList <> (  );
        }


    }

    public ResponseEntity<Integer> calculateResult(Integer id, List< Response > responses) {
        int right = quizInterface.getScore ( responses ).getBody ();
        QuizSubmitEvent event = new QuizSubmitEvent();
        Quiz quiz = quizDao.findById( id ).orElseThrow ( );
        event.setQuiz_name (quiz.getTitle ());
        event.setTotal_question ( String.valueOf ( quiz.getQuestionsIds ().size () ) );
        event.setScore ( right );
        event.setUserEmail ( "ajith2255iti@gmail.com" );
        producer.sendMessage ( event );
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public ResponseEntity< List< Quiz>> getAllQuizzes ( ) {
        return ResponseEntity.ok ( quizDao.findAll () );
    }
}