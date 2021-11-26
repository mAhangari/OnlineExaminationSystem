package ir.maktab56.service.impl;

import ir.maktab56.mapper.AnswerMapper;
import ir.maktab56.model.*;
import ir.maktab56.repository.AnswerRepository;
import ir.maktab56.service.AnswerService;
import ir.maktab56.service.QuestionService;
import ir.maktab56.service.QuestionSheetService;
import ir.maktab56.service.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository repository;
    private AnswerMapper answerMapper;
    private QuestionService questionService;
    private QuestionSheetService questionSheetService;

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setQuestionSheetService(QuestionSheetService questionSheetService) {
        this.questionSheetService = questionSheetService;
    }

    @Override
    public Answer save(Answer answer) {
        return repository.save(answer);
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Answer> findByStudent_Username(String username) {

        return repository.findByStudent_Username(username);
    }

    @Override
    public Optional<Answer> findByStudent_UsernameAndQuestionSheet_Quiz_Id(String username, Long quizId) {
        return repository.findByStudent_UsernameAndQuestionSheet_Quiz_Id(username, quizId);
    }

    @Override
    public Optional<List<Answer>> findByQuestionSheet(QuestionSheet questionSheet) {
        return repository.findByQuestionSheet(questionSheet);
    }

    // set answer base on receive answer from student
    @Override
    public AnswerDTO setAnswer(List<Map<String, Object>> answer) {
        Long quizId = Long.parseLong(answer.get(0).get("quizId").toString());
        Map<Question, String> answers = createAnswers(answer);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Answer answer1 = findByStudent_UsernameAndQuestionSheet_Quiz_Id(authentication.getName(), quizId).orElseThrow();
        answer1.setAnswer(answers);

        if (answer1.getEndAnswerDateTime() == null) {
            answer1.setEndAnswerDateTime(LocalDateTime.now());
            save(answer1);
        }
        return answerMapper.convertEntityToDTO(answer1);
    }

    @Override
    public List<Answer> findParticipatingAnswers(Long quizId) {
        QuestionSheet questionSheet = questionSheetService.findByQuiz_Id(quizId).orElseThrow();
        return findByQuestionSheet(questionSheet).orElseThrow();
    }

    // map input answer to (Question, String) and then return
    private Map<Question, String> createAnswers(List<Map<String, Object>> answer) {

        Map<Question, String> answers = new HashMap<>();

        for (int index = 1; index < answer.size(); index++) {
            answers.put(
                    questionService
                            .findById(Long.parseLong(answer.get(index).get("questionId").toString())).orElseThrow(),
                    answer.get(index).get("answer").toString()
            );
        }

        return answers;
    }


}
