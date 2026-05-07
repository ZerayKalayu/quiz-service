package et.gov.atlas.quizservice.service;


import et.gov.atlas.quizservice.dao.QuizDao;
import et.gov.atlas.quizservice.feign.QuizInterface;
import et.gov.atlas.quizservice.model.QuestionWrapper;
import et.gov.atlas.quizservice.model.Quiz;
import et.gov.atlas.quizservice.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

//    @Autowired
//    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();

        List<Integer> questionIds = quiz.getQuestionsIds();
        //Navin's code
//        ResponseEntity<List<QuestionWrapper>> questions =  quizInterface.getQuestionsFromId(questionIds);

        //IntelliJ suggestion
        return quizInterface.getQuestionsFromId(questionIds);
    }

    /*public ResponseEntity<Integer> calculateResult(Integer id, List<QuizResponse> responses) {

        Quiz quiz = quizDao.findById(id).get();

        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i=0;

        for(QuizResponse response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return  new ResponseEntity<>(right, HttpStatus.OK);
    }*/
    public ResponseEntity<Integer> calculateResult(Integer id, List<QuizResponse> responses) {

        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return score;
    }


}
