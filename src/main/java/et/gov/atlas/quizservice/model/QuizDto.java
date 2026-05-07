package et.gov.atlas.quizservice.model;

import lombok.Data;

@Data
public class QuizDto {
    private String categoryName;
    private int numberOfQuestions;
    private String title;
}
