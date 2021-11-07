package ir.maktab56.controllers;

import ir.maktab56.mapper.CourseMapper;
import ir.maktab56.mapper.ProfessorMapper;
import ir.maktab56.mapper.StudentMapper;
import ir.maktab56.mapper.UserMapper;
import ir.maktab56.model.Course;
import ir.maktab56.model.Professor;
import ir.maktab56.model.Student;
import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.service.*;
import ir.maktab56.service.dto.CourseDTO;
import ir.maktab56.service.dto.ProfessorDTO;
import ir.maktab56.service.dto.StudentDTO;
import ir.maktab56.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService<User> userService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final RoleService roleService;
    private final CourseService courseService;
    private final UserMapper userMapper;
    private final ProfessorMapper professorMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    // create mapping for admin profile page
    @GetMapping(value = "/admin-profile")
    public String adminProfile() {
        return "views/admin-profile";
    }

    // create rest api for return all users that activated
    @GetMapping(value = "/new-users")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> newUsers() {

        return ResponseEntity.ok(
                userMapper.convertListEntityToDTO(userService.findAllByIsActive(false))
        );
    }

    // create rest api for change state of new users after registration
    @PostMapping(value = "/user-state")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> userState(@RequestBody Map<String, String> userConfirm) {

        if (userConfirm.get("active").equals("accept")) {
            userService.updateUserActiveByUsernameAndIsActive(userConfirm.get("username"));
        } else {
            userService.deleteUserByUsername(userConfirm.get("username"));
        }

        return ResponseEntity.ok(
                userMapper.convertListEntityToDTO(userService.findAllByIsActive(false))
        );
    }

    // rest api for return users with specified field
    @PostMapping(value = "/all-users")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> all_users(@RequestBody List<Map<String, String>> userSpec) {

        String firstName = userSpec.get(1).get("firstName");
        if (firstName.equals(""))
            firstName = null;
        String lastName = userSpec.get(2).get("lastName");
        if (lastName.equals(""))
            lastName = null;
        String username = userSpec.get(3).get("username");
        if (username.equals(""))
            username = null;

        List<User> userList;
        if (userSpec.get(0).get("userType").equals("ALL"))
            userList = userService.findAllByUserTypeAndUsernameAndFirstNameAndLastName(
                    null, username, firstName, lastName);
        else if (userSpec.get(0).get("userType").equals("PROFESSOR"))
            userList = userService.findAllByUserTypeAndUsernameAndFirstNameAndLastName(
                    UserType.PROFESSOR, username, firstName, lastName);
        else userList = userService.findAllByUserTypeAndUsernameAndFirstNameAndLastName(
                    UserType.STUDENT, username, firstName, lastName);

        return ResponseEntity.ok(
                userMapper.convertListEntityToDTO(userList)
        );
    }

    //  rest api for return user DTO for requested username
    @PostMapping(value = "/user-info")
    @ResponseBody
    public ResponseEntity userInfo(@RequestBody String username) {

        User user = userService.findUserByUsername(username).orElseThrow();

        if (user.getUserType().equals(UserType.STUDENT))
            return ResponseEntity.ok(
                    studentMapper.convertEntityToDTO((Student) user)
            );
        else
            return ResponseEntity.ok(
                    professorMapper.convertEntityToDTO((Professor) user)
            );
    }

    // rest api to change user information
    @PostMapping(value = "/apply-user-change", produces = "application/json")
    @ResponseBody
    public ResponseEntity<JSONObject> userChange(@RequestBody List<Map<String, String>> user) {

        User aUser = userService.findUserByUsername(user.get(5).get("username")).orElseThrow();
        if(aUser.getUserType().equals(UserType.STUDENT))
            studentService.changeStudentFields(user, (Student) aUser);
        else
            professorService.changeProfessorFields(user, (Professor) aUser);

        JSONObject obj = new JSONObject();
        obj.put("state", "done");
        return ResponseEntity.ok(obj);
    }

    // rest api for return all courses
    @GetMapping(value = "/all_courses")
    @ResponseBody
    public ResponseEntity<List<CourseDTO>> allCourses() {
        return ResponseEntity.ok(
                courseMapper.convertListEntityToDTO(courseService.findAll())
        );
    }

    // rest api to add new course
    @PostMapping(value = "/add_course")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addCourse(@RequestBody List<Map<String, String>> data) {

        Course course = new Course();
        JSONObject obj = new JSONObject();

        try {
            course.setTitle(data.get(0).get("title"));
            course.setCourseId(data.get(1).get("courseId"));
            course.setStartDate(LocalDate.parse(data.get(2).get("startDate")));
            course.setEndDate(LocalDate.parse(data.get(3).get("endDate")));
            courseService.save(course);
            obj.put("status", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            obj.put("status", "duplicate");
        }

        System.out.println(obj.toMap());

        return ResponseEntity.ok(obj.toMap());
    }

    // rest api for return course for specified course id
    @PostMapping(value = "/find_course")
    @ResponseBody
    public ResponseEntity<CourseDTO> editCourse(@RequestBody String courseId) {
        return ResponseEntity.ok(
                courseMapper.convertEntityToDTO(courseService.findCoursesByCourseId(courseId))
        );
    }

    // rest api for return all professor
    @GetMapping(value = "/get-professors")
    @ResponseBody
    public ResponseEntity<List<ProfessorDTO>> getProfessors() {
        return ResponseEntity.ok(
                professorMapper.convertListEntityToDTO(professorService.findAll())
        );
    }

    // rest for delete requested course id
    @PostMapping(value = "/delete-course")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteProfessor(@RequestBody String courseId) {

        JSONObject obj = new JSONObject();
        try {
            courseService.deleteCourseByCourseId(courseId);
            obj.put("status", "success");
        } catch (Exception e) {
            obj.put("status", "error");
        }

        return ResponseEntity.ok(obj.toMap());
    }

    // rest api for return all students
    @GetMapping(value = "/get-students")
    @ResponseBody
    public ResponseEntity<List<StudentDTO>> getStudents() {
        return ResponseEntity.ok(
                studentMapper.convertListEntityToDTO(studentService.findAll())
        );
    }

    // rest api for update course base on given fields
    @PostMapping(value = "/update-course")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCourse(@RequestBody List<Map<String, Object>> course) {

        String title = (String) course.get(0).get("title");
        String courseId = (String) course.get(1).get("courseId");
        LocalDate startDate = LocalDate.parse((String) course.get(2).get("startDate"));
        LocalDate endDate = LocalDate.parse((String) course.get(3).get("endDate"));
        String professorUser = (String) course.get(4).get("professor");
        List<Map<String, String>> studentsUsername = (List<Map<String, String>>) course.get(5).get("students");

        Course existsCourse = courseService.findCoursesByCourseId(courseId);
        existsCourse.setTitle(title);
        existsCourse.setStartDate(startDate);
        existsCourse.setEndDate(endDate);
        if (professorUser != null)
            existsCourse.setProfessor(professorService.findUserByUsername(professorUser).orElse(null));

        Set<Student> students = new HashSet<>();
        studentsUsername.forEach(a -> students.add(studentService.findUserByUsername(a.get("username")).orElseThrow()));

        existsCourse.setStudents(students);
        courseService.save(existsCourse);
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // rest api for adding students to course based on given course id
    @PostMapping(value = "/course-user-info")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> courseUserInfo(@RequestBody String courseId) {
        List<UserDTO> users = new ArrayList<>();

        Course course = courseService.findCoursesByCourseId(courseId);
        users.add(professorMapper.convertEntityToDTO(course.getProfessor()));
        course.getStudents().forEach(a -> users.add(studentMapper.convertEntityToDTO(a)));

        return ResponseEntity.ok(users);
    }
}
