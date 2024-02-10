package com.ajith.questionservice.ServiceImpl;
import com.ajith.questionservice.Dao.QuestionDao;
import com.ajith.questionservice.Services.QuestionService;
import com.ajith.questionservice.model.Question;
import com.ajith.questionservice.model.QuestionWrapper;
import com.ajith.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Question createQuestion (Question question) {
        return questionDao.save(question);
    }

    @Override
    public  List < Question > getAllQuestions ( ) {
        return  questionDao.findAll ();
    }

    @Override
    public ResponseEntity<Question> getQuestionById (Integer id) {
        Optional < Question > question = questionDao.findById(id);
         return ResponseEntity.status ( HttpStatus.OK).body (question.get ());
    }

    @Override
    public ResponseEntity < List < Question > > getALllQuestionsByCategory (String categoryName) {
        return ResponseEntity.status ( HttpStatus.OK).body ( questionDao.findAllByCategory (categoryName));
    }

    @Override
    public ResponseEntity<List<Integer>> getQuestionsForQuiz (String categoryName, Integer numberOfQuestions) {
        List< Integer > questionIds = questionDao.findRandomQuestionsByCategory(categoryName, numberOfQuestions);
        return new  ResponseEntity<> ( questionIds ,HttpStatus.OK );
    }

    @Override
    public ResponseEntity < List < QuestionWrapper > > getQuestionsById (List < Integer > questionsIds) {
        List<QuestionWrapper>  wrappers = new ArrayList ();

        for(int id : questionsIds)
        {
            Optional < Question > question = questionDao.findById ( id );
            if (question.isPresent())
            {
                Question existQuestion = question.get();
                wrappers.add ( QuestionWrapper.builder()
                                .id ( existQuestion.getId () )
                                .question ( existQuestion.getQuestion ( ) )
                                .option1 ( existQuestion.getOption1 ( ) )
                                .option2 ( existQuestion.getOption2 ( ) )
                                .option3 ( existQuestion.getOption3 ( ) )
                                .option4 ( existQuestion.getOption4 () )
                        .build() );
            }

        }

        return new ResponseEntity <> ( wrappers, HttpStatus.OK );
    }

    @Override
    public ResponseEntity < Integer > getScore (List < Response > responses) {

        int right = 0;
        for(Response response : responses){
             Optional<Question>optionalQuestion = questionDao.findById ( response.getId ( ) );
             if( optionalQuestion.isPresent())
             {
                 Question question = optionalQuestion.get();
                 if(response.getResponse().equals(question.getRightAnswer()))
                     right++;
             }

        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
