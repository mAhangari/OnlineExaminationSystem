package ir.maktab56.service;

import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService extends UserService<Student> {

    void updateStudent(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username
    );

    Optional<Student> findStudentByUsername(String username);

    void changeStudentFields(List<Map<String, String>> user, Student student);

    void changeProfessorToStudent(List<Map<String, String>> user, Professor exProfessor);
}
