package ir.maktab56.repository;

import ir.maktab56.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findQuizzesByCourse_CourseId(String courseId);

}
