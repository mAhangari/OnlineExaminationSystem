package ir.maktab56.service;

import ir.maktab56.model.Answer;
import ir.maktab56.model.QuestionSheet;
import ir.maktab56.service.dto.AnswerDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnswerService {

    Answer save(Answer answer);

    Optional<Answer> findById(Long id);

    Optional<Answer> findByStudent_Username(String username);

    Optional<Answer> findByStudent_UsernameAndQuestionSheet_Quiz_Id(String username, Long quizId);

    Optional<List<Answer>> findByQuestionSheet(QuestionSheet questionSheet);

    AnswerDTO setAnswer(List<Map<String, Object>> answer);

    List<Answer> findParticipatingAnswers(Long quizId);
}
