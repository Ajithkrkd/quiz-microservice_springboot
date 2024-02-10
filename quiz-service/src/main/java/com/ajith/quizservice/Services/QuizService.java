package com.ajith.quizservice.Services;

import com.ajith.quizservice.Dao.QuizDao;
import com.ajith.quizservice.feign.QuizInterface;
import com.ajith.quizservice.model.QuestionWrapper;
import com.ajith.quizservice.model.Quiz;
import com.ajith.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity <String> createQuiz(String category,  String title,int numQ) {

        List < Integer > questions = quizInterface.getQuestionsForQuiz (category, numQ).getBody ();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds (questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List< QuestionWrapper >> getQuizQuestions(Integer id) {

        Optional<Quiz> optionalQuiz = quizDao.findById ( id );
        if ( optionalQuiz.isPresent() )
        {
            Quiz quiz = optionalQuiz.get();
            List<Integer> questionIds = quiz.getQuestionsIds ();
            List<QuestionWrapper> questionWrappers = quizInterface.getQuestionsById ( questionIds ).getBody ();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(new ArrayList <> (  ),HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<Integer> calculateResult(Integer id, List< Response > responses) {
        int right = quizInterface.getScore ( responses ).getBody ();
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public ResponseEntity< List< Quiz>> getAllQuizzes ( ) {
        return ResponseEntity.ok ( quizDao.findAll () );
    }
}