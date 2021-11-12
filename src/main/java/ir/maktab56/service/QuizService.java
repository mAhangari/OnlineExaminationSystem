package ir.maktab56.service;

import ir.maktab56.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface QuizService {

    List<Quiz> findQuizzesByCourse_CourseId(String courseId);

    Optional<Quiz> findById(Long id);

    void deleteQuiz(Quiz quiz);

    void updateQuiz(List<Map<String, Object>> quiz);

    void createQuiz(List<Map<String, Object>> quiz);
}
