package ir.maktab56.service;

import ir.maktab56.model.Question;
import ir.maktab56.service.dto.QuestionDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionService {

    Question save(Question question);

    List<Question> findByProfessor();

    List<QuestionDTO> convertQuestionsToQuestionDTOs();

    Optional<Question> findById(Long id);

    List<QuestionDTO> convertQuestionsOfQuestionSheetToQuestionDTO(Long quizId);

    void applyScore(List<Map<String, Object>> question);

    void remove(Long questionId, Long quizId);
}
