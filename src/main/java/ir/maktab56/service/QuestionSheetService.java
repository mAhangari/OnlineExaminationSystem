package ir.maktab56.service;

import ir.maktab56.model.QuestionSheet;
import ir.maktab56.service.dto.QuestionSheetDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionSheetService {

    Optional<QuestionSheet> findById(Long id);

    Optional<QuestionSheet> findByQuiz_Id(Long quizId);

    QuestionSheet save(QuestionSheet questionSheet);

    // convert questionSheet to Question SheetDTO by Quiz id
    QuestionSheetDTO convertQuestionSheetToQuestionSheetDTO(Long quizId);

    void addQuestion(List<Map<String, Object>> question);
}
