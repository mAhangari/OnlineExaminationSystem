package ir.maktab56.service;

import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProfessorService extends UserService<Professor> {

    void updateProfessor(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username
    );

    Optional<Professor> findProfessorByUsername(String username);

    void changeProfessorFields(List<Map<String, String>> user, Professor professor);

    void changeStudentToProfessor(List<Map<String, String>> user, Student exStudent);

    void addNewProfessor(Map<String, String> user);
}
