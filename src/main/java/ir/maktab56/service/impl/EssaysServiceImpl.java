package ir.maktab56.service.impl;

import ir.maktab56.model.Essays;
import ir.maktab56.model.Quiz;
import ir.maktab56.repository.EssaysRepository;
import ir.maktab56.service.EssaysService;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.QuestionSheetService;
import ir.maktab56.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EssaysServiceImpl implements EssaysService {

    private final EssaysRepository repository;
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
    public Essays save(Essays essays) {
        return repository.save(essays);
    }

    @Override
    public void creatEssays(List<Map<String, Object>> request) {

        Quiz quiz = quizService.findById(Long.parseLong(request.get(3).get("quizId").toString())).orElseThrow();
        Essays essays = new Essays();

        essays.setTitle(request.get(0).get("title").toString());
        essays.setQuestion(request.get(1).get("question").toString());
        if (!request.get(2).get("score").toString().isBlank())
            essays.setScore(Long.parseLong(request.get(2).get("score").toString()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        essays.setProfessor(professorService.findProfessorByUsername(authentication.getName()).orElseThrow());
        save(essays);
        quiz.getQuestionSheet().addQuestion(essays);
        questionSheetService.save(quiz.getQuestionSheet());
    }
}
