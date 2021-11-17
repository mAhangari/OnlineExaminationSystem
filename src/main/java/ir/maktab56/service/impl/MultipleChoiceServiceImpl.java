package ir.maktab56.service.impl;

import ir.maktab56.model.MultipleChoice;
import ir.maktab56.model.Quiz;
import ir.maktab56.repository.MultipleChoiceRepository;
import ir.maktab56.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MultipleChoiceServiceImpl implements MultipleChoiceService {

    private final MultipleChoiceRepository repository;
    private ProfessorService professorService;
    private QuizService quizService;
    private QuestionSheetService questionSheetService;

    @Autowired
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @Autowired
    public void setQuestionSheetService(QuestionSheetService questionSheetService) {
        this.questionSheetService = questionSheetService;
    }

    @Override
    public MultipleChoice save(MultipleChoice multipleChoice) {
        return repository.save(multipleChoice);
    }

    @Override
    public void creatMultipleChoice(List<Map<String, Object>> request) {

        Quiz quiz = quizService.findById(Long.parseLong(request.get(3).get("quizId").toString())).orElseThrow();
        MultipleChoice multipleChoice = new MultipleChoice();

        multipleChoice.setTitle(request.get(0).get("title").toString());
        multipleChoice.setQuestion(request.get(1).get("question").toString());
        if (!request.get(2).get("score").toString().isBlank())
            multipleChoice.setScore(Long.parseLong(request.get(2).get("score").toString()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        multipleChoice.setProfessor(professorService.findProfessorByUsername(authentication.getName()).orElseThrow());
        save(multipleChoice);
        quiz.getQuestionSheet().addQuestion(multipleChoice);
        questionSheetService.save(quiz.getQuestionSheet());
    }
}
