package et.gov.atlas.quizservice.controller;

import et.gov.atlas.quizservice.model.QuestionWrapper;
import et.gov.atlas.quizservice.model.QuizDto;
import et.gov.atlas.quizservice.model.QuizResponse;
import et.gov.atlas.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){

        return quizService.createQuiz(quizDto.getCategoryName(),
                                      quizDto.getNumberOfQuestions(),
                                      quizDto.getTitle());

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){

        return quizService.getQuizQuestions(id);

    }

    //submit Quiz  and get score
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,  @RequestBody List<QuizResponse> responses){
        return quizService.calculateResult(id,responses);
    }
}
