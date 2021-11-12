package ir.maktab56.controllers;

import ir.maktab56.mapper.CourseMapper;
import ir.maktab56.mapper.QuizMapper;
import ir.maktab56.service.CourseService;
import ir.maktab56.service.QuizService;
import ir.maktab56.service.dto.CourseDTO;
import ir.maktab56.service.dto.QuizDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final QuizMapper quizMapper;
    private final QuizService quizService;

    // create mapping for professor profile page
    @GetMapping(value = "/professor-profile")
    public ModelAndView professorProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/professor-profile");
        return modelAndView;
    }

    // return all course base on professor id
    @GetMapping(value = "/professor-courses")
    public ResponseEntity<List<CourseDTO>> professorCourses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(
                courseMapper
                        .convertListEntityToDTO(courseService.findCoursesByProfessor_Username(authentication.getName()))
        );
    }

    // return all quiz base on course id
    @GetMapping(value = "/course-quiz{id}")
    public ResponseEntity<List<QuizDTO>> courseQuiz(@PathVariable String id) {
        return ResponseEntity.ok(
                quizMapper.convertListEntityToDTO(quizService.findQuizzesByCourse_CourseId(id))
        );
    }

    // delete quiz based on given quiz id
    @PostMapping(value = "/delete-quiz")
    public ResponseEntity<Map<String, Object>> deleteQuiz(@RequestBody String id) {

        quizService.deleteQuiz(quizService.findById(Long.parseLong(id)).orElseThrow());
        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // edit quiz based on given information
    @PostMapping(value = "/edit-quiz")
    public ResponseEntity<QuizDTO> editQuiz(@RequestBody String id) {

        return ResponseEntity.ok(
                quizMapper.convertEntityToDTO(quizService.findById(Long.parseLong(id)).orElseThrow())
        );
    }

    // update quiz based on given information
    @PostMapping(value = "/update-quiz")
    public ResponseEntity<Map<String, Object>> updateQuiz(@RequestBody List<Map<String, Object>> quiz) {

        quizService.updateQuiz(quiz);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // create new quiz
    @PostMapping(value = "/create-quiz")
    public String createQuiz(@RequestBody List<Map<String, Object>> quiz) {
        quizService.createQuiz(quiz);
        return "successful!!!";
    }

}
