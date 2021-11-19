package ir.maktab56.repository;

import ir.maktab56.model.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.FetchType;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @EntityGraph(attributePaths = "scores", type = EntityGraph.EntityGraphType.LOAD)
    List<Question> findByProfessor_Username(String username);

}
