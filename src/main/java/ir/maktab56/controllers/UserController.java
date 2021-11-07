package ir.maktab56.controllers;

import ir.maktab56.model.User;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.StudentService;
import ir.maktab56.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService<User> userService;
    private final ProfessorService professorService;
    private final StudentService studentService;

    // post mapping for requested url and add new user to database based on information submitted
    @PostMapping(value = "/add-user")
    public String adduser(@RequestBody Map<String, String> user) {

        if (userService.existsUserByUsername(user.get("username")))
            return "username already exists!!!";

        else if (user.get("userType").equals("PROFESSOR")) {
            professorService.addNewProfessor(user);
        } else {
            studentService.addNewStudent(user);
        }

        return "Registration was successful!!! wait for confirmation.";
    }
}
