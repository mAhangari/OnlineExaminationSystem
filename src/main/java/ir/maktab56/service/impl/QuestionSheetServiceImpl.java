package ir.maktab56.service.impl;

import ir.maktab56.mapper.EssaysMapper;
import ir.maktab56.mapper.MultipleChoiceMapper;
import ir.maktab56.mapper.QuestionSheetMapper;
import ir.maktab56.model.*;
import ir.maktab56.repository.QuestionSheetRepository;
import ir.maktab56.service.QuestionService;
import ir.maktab56.service.QuestionSheetService;
import ir.maktab56.service.QuizService;
import ir.maktab56.service.dto.QuestionDTO;
import ir.maktab56.service.dto.QuestionSheetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionSheetServiceImpl implements QuestionSheetService {

    private final QuestionSheetRepository repository;
    private final EssaysMapper essaysMapper;
    private final QuestionSheetMapper questionSheetMapper;
    private final MultipleChoiceMapper multipleChoiceMapper;
    private QuestionSheetService questionSheetService;
    private QuizService quizService;
    private QuestionService questionService;

    @Autowired
    public void setQuestionSheetService(QuestionSheetService questionSheetService) {
        this.questionSheetService = questionSheetService;
    }

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Optional<QuestionSheet> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<QuestionSheet> findByQuiz_Id(Long quizId) {
        return repository.findByQuiz_Id(quizId);
    }

    @Override
    public QuestionSheet save(QuestionSheet questionSheet) {
        return repository.save(questionSheet);
    }

    @Override
    public QuestionSheetDTO convertQuestionSheetToQuestionSheetDTO(Long quizId) {
        QuestionSheet questionSheet = questionSheetService.findByQuiz_Id(quizId).orElseThrow();
        QuestionSheetDTO questionSheetDTO = questionSheetMapper.convertEntityToDTO(questionSheet);

        Set<QuestionDTO> questionDTOS = new HashSet<>();

        for (Question question: questionSheet.getQuestions()) {
            if (question instanceof Essays)
                questionDTOS.add(essaysMapper.convertEntityToDTO((Essays) question));
            else
                questionDTOS.add(multipleChoiceMapper.convertEntityToDTO((MultipleChoice) question));
        }
        questionSheetDTO.setQuestions(questionDTOS);

        return questionSheetDTO;
    }

    @Override
    public void addQuestion(List<Map<String, Object>> question) {
        Quiz quiz = quizService.findById(Long.parseLong(question.get(1).get("quizId").toString())).orElseThrow();
        Question question1 = questionService
                .findById(Long.parseLong(question.get(0).get("questionId").toString())).orElseThrow();

        quiz.getQuestionSheet().addQuestion(question1);
        questionSheetService.save(quiz.getQuestionSheet());
    }
}
