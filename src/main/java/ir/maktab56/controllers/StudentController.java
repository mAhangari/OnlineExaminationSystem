package ir.maktab56.controllers;

import ir.maktab56.mapper.CourseMapper;
import ir.maktab56.mapper.QuizMapper;
import ir.maktab56.model.Course;
import ir.maktab56.model.Student;
import ir.maktab56.service.*;
import ir.maktab56.service.dto.CourseDTO;
import ir.maktab56.service.dto.QuestionDTO;
import ir.maktab56.service.dto.QuestionSheetDTO;
import ir.maktab56.service.dto.QuizDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final CourseMapper courseMapper;
    private final QuizMapper quizMapper;
    private final QuizService quizService;
    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    // create mapping for student profile page
    @GetMapping(value = "/student-profile")
    public ModelAndView studentProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/student-profile");
        return modelAndView;
    }

    // return all course student course
    @GetMapping(value = "/student-courses")
    public ResponseEntity<List<CourseDTO>> professorCourses() {
        List<Course> courses = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findUserByUsername(authentication.getName()).orElseThrow();

        courses = new ArrayList<>(student.getCourse());

        return ResponseEntity.ok(
                courseMapper.convertListEntityToDTO(courses)
        );
    }

    // start quiz based on quiz id
    @GetMapping(value = "/start-quiz/{quizId}")
    public ResponseEntity<QuizDTO> editQuiz(@PathVariable String quizId) {

        return ResponseEntity.ok(
                quizMapper.convertEntityToDTO(quizService.findById(Long.parseLong(quizId)).orElseThrow())
        );
    }

}
