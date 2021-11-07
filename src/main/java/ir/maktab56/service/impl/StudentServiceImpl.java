package ir.maktab56.service.impl;

import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.repository.StudentRepository;
import ir.maktab56.service.CourseService;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.RoleService;
import ir.maktab56.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl extends UserServiceImpl<Student> implements StudentService {

    private final StudentRepository repository;
    private final RoleService roleService;
    private CourseService courseService;
    private ProfessorService professorService;

    public StudentServiceImpl(StudentRepository repository, RoleService roleService) {
        super(repository);
        this.repository = repository;
        this.roleService = roleService;
    }

    @Autowired
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    // update student base on first name, last name, national code, student id and username
    @Override
    public void updateStudent(
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
            @Param(value = "nationalCode") String nationalCode, @Param(value = "userId") Long userId,
            @Param(value = "username") String username) {

        repository.updateStudent(firstName, lastName, nationalCode, userId, username);
    }

    @Override
    public Optional<Student> findStudentByUsername(String username) {
        return repository.findStudentByUsername(username);
    }

    // method for change student fields base on information submitted
    @Override
    public void changeStudentFields(List<Map<String, String>> user, Student exStudent) {

        String firstName = user.get(0).get("firstName");
        String lastName = user.get(1).get("lastName");
        String nationalCode = user.get(2).get("nationalCode");
        Long studentId = Long.parseLong(user.get(4).get("userId"));
        String username = user.get(5).get("username");

        if (user.get(3).get("userType").equals("STUDENT"))
            updateStudent(firstName, lastName, nationalCode, studentId, username);
        else if (user.get(3).get("userType").equals("PROFESSOR"))
            professorService.changeStudentToProfessor(user, exStudent);
    }

    // method for change existing professor to a student and remove existing professor
    @Override
    public void changeProfessorToStudent(List<Map<String, String>> user, Professor exProfessor) {
        String firstName = user.get(0).get("firstName");
        String lastName = user.get(1).get("lastName");
        String nationalCode = user.get(2).get("nationalCode");
        Long studentId = Long.parseLong(user.get(4).get("userId"));
        String username = user.get(5).get("username");

        if (courseService.removeProfessorIdFromCourse(exProfessor.getCourses())) {

            Student newStudent = new Student();

            newStudent.setFirstName(firstName);
            newStudent.setLastName(lastName);
            newStudent.setNationalCode(nationalCode);
            newStudent.setStudentId(studentId);
            newStudent.setRoles(roleService.findRoleByName("STUDENT").orElseThrow());
            newStudent.setUsername(username);
            newStudent.setUserType(UserType.STUDENT);
            newStudent.setPassword(exProfessor.getPassword());
            newStudent.setActive(exProfessor.isActive());
            professorService.delete(exProfessor);
            save(newStudent);
        }
    }

    // method for create new user base in sign up information
    @Override
    public void addNewStudent(Map<String, String> user) {
        Student student = new Student();
        student.setFirstName(user.get("firstName"));
        student.setLastName(user.get("lastName"));
        student.setUsername(user.get("username"));
        student.setPassword(new BCryptPasswordEncoder().encode(user.get("password")));
        student.setNationalCode(user.get("nationalCode"));
        student.setUserType(UserType.STUDENT);
        student.setStudentId(Long.parseLong(user.get("studentId")));
        student.setRoles(roleService.findRoleByName("STUDENT").orElse(null));
        student.setActive(false);
        save(student);
    }
}
