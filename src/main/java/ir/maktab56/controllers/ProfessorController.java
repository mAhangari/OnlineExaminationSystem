package ir.maktab56.controllers;

import ir.maktab56.mapper.*;
import ir.maktab56.model.Answer;
import ir.maktab56.service.EssaysService;
import ir.maktab56.service.*;
import ir.maktab56.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final QuestionSheetService questionSheetService;
    private final EssaysService essaysService;
    private final MultipleChoiceService multipleChoiceService;
    private final QuestionService questionService;
    private AnswerService answerService;
    private AnswerMapper answerMapper;

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

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
    public ResponseEntity<Map<String, Object>> createQuiz(@RequestBody List<Map<String, Object>> quiz) {
        quizService.createQuiz(quiz);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // return question sheet
    @GetMapping(value = "/question-sheet{id}")
    public ResponseEntity<QuestionSheetDTO> questionSheet(@PathVariable String id) {

        return ResponseEntity.ok(
                questionSheetService.convertQuestionSheetToQuestionSheetDTO(Long.parseLong(id))
        );
    }

    // save essays question
    @PostMapping(value = "/add-essays")
    public ResponseEntity<Map<String, Object>> addEssays(@RequestBody List<Map<String, Object>> request) {

        essaysService.creatOrUpdateEssays(request);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // save multiple option question
    @PostMapping(value = "/add-multipleOption")
    public ResponseEntity<Map<String, Object>> addMultipleOption(@RequestBody List<Map<String, Object>> request) {

        multipleChoiceService.creatOrUpdateMultipleChoice(request);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // get all question belong to current professor
    @GetMapping(value = "/get-questions")
    public ResponseEntity<List<QuestionDTO>> getQuestions() {

        return ResponseEntity.ok(
                questionService.convertQuestionsToQuestionDTOs()
        );
    }

    // add question into question sheet
    @PostMapping(value = "/add-question")
    public ResponseEntity<Map<String, Object>> addQuestion(@RequestBody List<Map<String, Object>> question) {

        questionSheetService.addQuestion(question);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // return all quizzes based on current user
    @GetMapping(value = "/all-quizzes")
    public ResponseEntity<List<QuizDTO>> allQuizzes() {

        return ResponseEntity.ok(
                quizMapper.convertListEntityToDTO(quizService.findAll())
        );
    }

    // return all questions for requested question sheet id
    @GetMapping(value = "/get-question-sheet{id}")
    public ResponseEntity<List<QuestionDTO>> getQuestionSheet(@PathVariable Long id) {

        return ResponseEntity.ok(
                questionService.convertQuestionsOfQuestionSheetToQuestionDTO(id)
        );
    }

    // apply question score base on quiz id and question id
    @PostMapping(value = "/apply-question-score")
    public ResponseEntity<Map<String, Object>> applyQuestionScore(@RequestBody List<Map<String, Object>> question) {

        questionService.applyScore(question);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // apply answer score
    @PostMapping(value = "/apply-answer-score")
    public ResponseEntity<Map<String, Object>> applyAnswerScore(@RequestBody Map<String, Object> answer) {

        Answer updateAnswer = answerService.findById(Long.parseLong(answer.get("answerId").toString())).orElseThrow();
        updateAnswer.setScore(Double.parseDouble(answer.get("score").toString()));
        answerService.save(updateAnswer);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // delete question base on question id
    @DeleteMapping(value = "/delete-question/{questionId}/{quizId}")
    public ResponseEntity<Map<String, Object>> deleteQuestion(@PathVariable Long questionId,
                                                              @PathVariable Long quizId) {

        questionService.remove(questionId, quizId);

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(obj.toMap());
    }

    // get participants information base on quiz id
    @GetMapping(value = "/get-participants/{quizId}")
    public ResponseEntity<List<AnswerDTO>> getParticipants(@PathVariable Long quizId) {

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        return ResponseEntity.ok(
                answerMapper.convertListEntityToDTO(answerService.findParticipatingAnswers(quizId))
        );
    }

}
