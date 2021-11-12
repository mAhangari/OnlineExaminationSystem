package ir.maktab56.service.impl;

import ir.maktab56.model.Course;
import ir.maktab56.model.Quiz;
import ir.maktab56.repository.QuizRepository;
import ir.maktab56.service.CourseService;
import ir.maktab56.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repository;
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public List<Quiz> findQuizzesByCourse_CourseId(String courseId) {
        return repository.findQuizzesByCourse_CourseId(courseId);
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        repository.delete(quiz);
    }

    @Override
    public void updateQuiz(List<Map<String, Object>> quiz) {
        String title = (String) quiz.get(0).get("title");
        String description = (String) quiz.get(1).get("description");
        System.out.println((String) quiz.get(1).get("description"));
        Long quizTime = Long.parseLong(quiz.get(2).get("quizTime").toString());

        Quiz existsQuiz = findById(Long.parseLong(quiz.get(3).get("quizId").toString())).orElseThrow();
        if (!title.isBlank()) {
            existsQuiz.setTitle(title);
        }
        existsQuiz.setDescription(description);
        existsQuiz.setQuizTime(quizTime);

        repository.save(existsQuiz);
    }

    @Override
    public void createQuiz(List<Map<String, Object>> quiz) {
        String title = (String) quiz.get(0).get("title");
        String description = (String) quiz.get(1).get("description");
        Long quizTime = Long.parseLong(quiz.get(2).get("quizTime").toString());

        Course course = courseService.findCoursesByCourseId((String) quiz.get(3).get("courseId"));

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(title);
        newQuiz.setDescription(description);
        newQuiz.setQuizTime(quizTime);
        newQuiz.setCourse(course);
        repository.save(newQuiz);
    }
}
