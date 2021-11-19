package ir.maktab56.service.impl;

import ir.maktab56.model.Essays;
import ir.maktab56.model.Quiz;
import ir.maktab56.model.Score;
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
import java.util.Optional;

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
    public Optional<Essays> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void creatOrUpdateEssays(List<Map<String, Object>> request) {
        if (request.get(4).get("essaysId").toString().isBlank()) {
            Quiz quiz = quizService.findById(Long.parseLong(request.get(3).get("quizId").toString())).orElseThrow();
            Essays essays = new Essays();

            essays.setTitle(request.get(0).get("title").toString());
            essays.setQuestion(request.get(1).get("question").toString());
            if (!request.get(2).get("score").toString().isBlank()) {
                Score score = new Score();
                score.setScore(Double.parseDouble(request.get(2).get("score").toString()));
                score.setQuestionSheet(quiz.getQuestionSheet());

                essays.addScore(score);
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            essays.setProfessor(professorService.findProfessorByUsername(authentication.getName()).orElseThrow());
            save(essays);
            quiz.getQuestionSheet().addQuestion(essays);
            questionSheetService.save(quiz.getQuestionSheet());
        } else {
            Essays essays = findById(Long.parseLong(request.get(4).get("essaysId").toString())).orElseThrow();
            essays.setTitle(request.get(0).get("title").toString());
            essays.setQuestion(request.get(1).get("question").toString());
            save(essays);
        }
    }
}
