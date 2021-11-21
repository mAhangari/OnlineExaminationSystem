package ir.maktab56.repository;

import ir.maktab56.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends UserRepository<Student> {

    @Modifying
    @Query("update Student s set s.firstName = :firstName, s.lastName = :lastName, s.nationalCode = :nationalCode," +
            " s.StudentId = :userId, s.userType = 'STUDENT' where s.username = :username")
    void updateStudent(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username
        );

    @EntityGraph(attributePaths = "course")
    Optional<Student> findStudentByUsername(String username);
}
