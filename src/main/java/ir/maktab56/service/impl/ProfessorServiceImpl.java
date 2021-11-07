package ir.maktab56.service.impl;

import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.repository.ProfessorRepository;
import ir.maktab56.service.CourseService;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.RoleService;
import ir.maktab56.service.StudentService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfessorServiceImpl extends UserServiceImpl<Professor> implements ProfessorService {

    private final ProfessorRepository repository;
    private final RoleService roleService;
    private final CourseService courseService;
    private final StudentService studentService;

    public ProfessorServiceImpl(ProfessorRepository repository, RoleService roleService,
                                CourseService courseService, StudentService studentService) {
        super(repository);
        this.repository = repository;
        this.roleService = roleService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public void updateProfessor(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username) {

        repository.updateProfessor(firstName, lastName, nationalCode, userId, username);
    }

    @Override
    public Optional<Professor> findProfessorByUsername(String username) {
        return repository.findProfessorByUsername(username);
    }

    @Override
    public void changeProfessorFields(List<Map<String, String>> user, Professor exProfessor) {

        String firstName = user.get(0).get("firstName");
        String lastName = user.get(1).get("lastName");
        String nationalCode = user.get(2).get("nationalCode");
        Long personnelId = Long.parseLong(user.get(4).get("userId"));
        String username = user.get(5).get("username");

        if (user.get(3).get("userType").equals("PROFESSOR"))
            updateProfessor(firstName, lastName, nationalCode, personnelId, username);
        else if (user.get(3).get("userType").equals("STUDENT"))
            studentService.changeProfessorToStudent(user, exProfessor);
    }

    @Override
    public void changeStudentToProfessor(List<Map<String, String>> user, Student exStudent) {
        String firstName = user.get(0).get("firstName");
        String lastName = user.get(1).get("lastName");
        String nationalCode = user.get(2).get("nationalCode");
        Long personnelId = Long.parseLong(user.get(4).get("userId"));
        String username = user.get(5).get("username");

        if (courseService.removeStudentIdFromCourse(exStudent)) {
            Professor newProfessor = new Professor();
            newProfessor.setFirstName(firstName);
            newProfessor.setLastName(lastName);
            newProfessor.setNationalCode(nationalCode);
            newProfessor.setPersonnelId(personnelId);
            newProfessor.setRoles(roleService.findRoleByName("PROFESSOR").orElseThrow());
            newProfessor.setUsername(username);
            newProfessor.setUserType(UserType.PROFESSOR);
            newProfessor.setPassword(exStudent.getPassword());
            newProfessor.setActive(exStudent.isActive());
            studentService.delete(exStudent);
            save(newProfessor);
        }
    }

    @Override
    public void addNewProfessor(Map<String, String> user) {

        Professor professor = new Professor();
        professor.setFirstName(user.get("firstName"));
        professor.setLastName(user.get("lastName"));
        professor.setUsername(user.get("username"));
        professor.setPassword(new BCryptPasswordEncoder().encode(user.get("password")));
        professor.setNationalCode(user.get("nationalCode"));
        professor.setUserType(UserType.PROFESSOR);
        professor.setPersonnelId(Long.parseLong(user.get("personnelId")));
        professor.setRoles(roleService.findRoleByName("PROFESSOR").orElse(null));
        professor.setActive(false);
        save(professor);
    }
}
