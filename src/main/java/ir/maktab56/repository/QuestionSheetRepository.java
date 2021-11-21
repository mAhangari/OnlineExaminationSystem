package ir.maktab56.repository;

import ir.maktab56.model.QuestionSheet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionSheetRepository extends JpaRepository<QuestionSheet, Long> {

    @EntityGraph(attributePaths = "questions", type = EntityGraph.EntityGraphType.LOAD)
    Optional<QuestionSheet> findByQuiz_Id(Long quizId);
}
