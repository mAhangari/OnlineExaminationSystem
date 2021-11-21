package ir.maktab56.repository;

import ir.maktab56.model.Professor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ProfessorRepository extends UserRepository<Professor> {

    @Modifying
    @Query("update Professor p set p.firstName = :firstName, p.lastName = :lastName, p.nationalCode = :nationalCode," +
            " p.personnelId = :userId, p.userType = 'PROFESSOR' where p.username = :username")
    void updateProfessor(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username
    );

    @EntityGraph(attributePaths = "courses")
    Optional<Professor> findProfessorByUsername(String username);
}
