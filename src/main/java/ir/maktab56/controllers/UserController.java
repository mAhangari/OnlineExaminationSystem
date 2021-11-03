package ir.maktab56.controllers;

import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.RoleService;
import ir.maktab56.service.StudentService;
import ir.maktab56.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService<User> userService;
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final RoleService roleService;

    @PostMapping(value = "/add-user")
    public String adduser(@RequestBody Map<String, String> user) {

        if (userService.existsUserByUsername(user.get("username")))
            return "username already exists!!!";

        else if (user.get("userType").equals("PROFESSOR")) {
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
            professorService.save(professor);
        } else {
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
            studentService.save(student);
        }

        return "Registration was successful!!! wait for confirmation.";
    }
}
