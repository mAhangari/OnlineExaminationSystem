package ir.maktab56.service.impl;

import ir.maktab56.model.MultipleChoice;
import ir.maktab56.model.Quiz;
import ir.maktab56.model.Score;
import ir.maktab56.repository.MultipleChoiceRepository;
import ir.maktab56.service.*;
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
    public Optional<MultipleChoice> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void creatOrUpdateMultipleChoice(List<Map<String, Object>> request) {

        if (request.get(6).get("multipleChoiceId").toString().isBlank()) {
            Quiz quiz = quizService.findById(Long.parseLong(request.get(3).get("quizId").toString())).orElseThrow();
            MultipleChoice multipleChoice = new MultipleChoice();

            for (int i = 0; i < ((List) request.get(4).get("option")).size(); i++)
                multipleChoice.addOption(((List) request.get(4).get("option")).get(i).toString());

            multipleChoice.setTitle(request.get(0).get("title").toString());
            multipleChoice.setQuestion(request.get(1).get("question").toString());
            if (!request.get(2).get("score").toString().isBlank()) {
                Score score = new Score();
                score.setScore(Double.parseDouble(request.get(2).get("score").toString()));
                score.setQuestionSheet(quiz.getQuestionSheet());

                multipleChoice.addScore(score);
            }
            multipleChoice.setCorrectAnswer(request.get(5).get("correctAnswer").toString());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            multipleChoice.setProfessor(professorService.findProfessorByUsername(authentication.getName()).orElseThrow());
            save(multipleChoice);
            quiz.getQuestionSheet().addQuestion(multipleChoice);
            questionSheetService.save(quiz.getQuestionSheet());
        } else {
            MultipleChoice multipleChoice =
                    findById(Long.parseLong(request.get(6).get("multipleChoiceId").toString())).orElseThrow();
            multipleChoice.getOptions().clear();
            for (int i = 0; i < ((List) request.get(4).get("option")).size(); i++)
                multipleChoice.addOption(((List) request.get(4).get("option")).get(i).toString());
            multipleChoice.setTitle(request.get(0).get("title").toString());
            multipleChoice.setQuestion(request.get(1).get("question").toString());
            multipleChoice.setCorrectAnswer(request.get(5).get("correctAnswer").toString());
            save(multipleChoice);
        }
    }
}
