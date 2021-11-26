package ir.maktab56.controllers;

import ir.maktab56.mapper.CourseMapper;
import ir.maktab56.mapper.QuizMapper;
import ir.maktab56.model.Course;
import ir.maktab56.model.Student;
import ir.maktab56.service.*;
import ir.maktab56.service.dto.AnswerDTO;
import ir.maktab56.service.dto.CourseDTO;
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

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private CourseMapper courseMapper;
    private QuizMapper quizMapper;
    private QuizService quizService;
    private StudentService studentService;
    private QuestionService questionService;
    private AnswerService answerService;

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setQuizMapper(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
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
    public ResponseEntity<List<CourseDTO>> studentCourses() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findUserByUsername(authentication.getName()).orElseThrow();

        List<Course> courses = new ArrayList<>(student.getCourse());

        return ResponseEntity.ok(
                courseMapper.convertListEntityToDTO(courses)
        );
    }

    // return all quiz base on course id
    @GetMapping(value = "/course-quiz{id}")
    public ResponseEntity<List<QuizDTO>> courseQuiz(@PathVariable String id) {
        return ResponseEntity.ok(
                quizMapper.convertListEntityToDTO(quizService.findQuizzesByCourse_CourseId(id))
        );
    }

    // start quiz based on quiz id
    @GetMapping(value = "/start-quiz/{quizId}")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable Long quizId) {

        Long quizTime = studentService.startQuiz(quizId);

        JSONObject object = new JSONObject();
        object.put("quizTimeLeft", quizTime);
        object.put("questions", questionService.convertQuestionsOfQuestionSheetToQuestionDTO(quizId));
        return ResponseEntity.ok(
                object.toMap()
        );
    }

    // set student answers received from request body
    @PostMapping(value = "/set-answer")
    public ResponseEntity<AnswerDTO> setAnswer(@RequestBody List<Map<String, Object>> answer) {

        return ResponseEntity.ok(
                answerService.setAnswer(answer)
        );
    }

}
