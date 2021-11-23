package ir.maktab56.repository;

import ir.maktab56.model.Answer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @EntityGraph(attributePaths = "answer", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Answer> findByStudent_Username(String username);

}
