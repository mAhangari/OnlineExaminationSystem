package ir.maktab56.service.impl;

import ir.maktab56.mapper.EssaysMapper;
import ir.maktab56.mapper.MultipleChoiceMapper;
import ir.maktab56.model.*;
import ir.maktab56.repository.QuestionRepository;
import ir.maktab56.service.QuestionService;
import ir.maktab56.service.QuestionSheetService;
import ir.maktab56.service.ScoreService;
import ir.maktab56.service.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;
    private EssaysMapper essaysMapper;
    private MultipleChoiceMapper multipleChoiceMapper;
    private QuestionSheetService questionSheetService;
    private ScoreService scoreService;

    @Autowired
    public void setEssaysMapper(EssaysMapper essaysMapper) {
        this.essaysMapper = essaysMapper;
    }

    @Autowired
    public void setMultipleChoiceMapper(MultipleChoiceMapper multipleChoiceMapper) {
        this.multipleChoiceMapper = multipleChoiceMapper;
    }

    @Autowired
    public void setQuestionSheetService(QuestionSheetService questionSheetService) {
        this.questionSheetService = questionSheetService;
    }

    @Autowired
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public Question save(Question question) {
        return repository.save(question);
    }

    @Override
    public List<Question> findByProfessor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByProfessor_Username(authentication.getName());
    }

    @SuppressWarnings("DuplicatedCode")
    public List<QuestionDTO> convertQuestionsToQuestionDTOs() {
        List<Question> questions = findByProfessor();
        return getQuestionDTOS(questions);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<QuestionDTO> convertQuestionsOfQuestionSheetToQuestionDTO(Long quizId) {
        QuestionSheet questionSheet = questionSheetService.findByQuiz_Id(quizId).orElseThrow();
        return getQuestionDTOS(new ArrayList<>(questionSheet.getQuestions()));
    }

    @Override
    public void applyScore(List<Map<String, Object>> question) {
        QuestionSheet questionSheet =
                questionSheetService.findByQuiz_Id(Long.parseLong(question.get(1).get("quizId").toString())).orElseThrow();
        Question question1 = findById(Long.parseLong(question.get(0).get("questionId").toString())).orElseThrow();

        if (!question.get(2).get("score").toString().isBlank()) {
            if (question1.getScores().size()>0) {
                question1.getScores().forEach(a -> {
                    if (!Objects.equals(a.getQuestionSheet().getId(), questionSheet.getId())) {
                        question1.getScores().remove(a);
                    }
                });
                if (question1.getScores().size() > 0){
                    question1.getScores().stream().findFirst().get().setScore(
                            Double.parseDouble(question.get(2).get("score").toString())
                    );
                }else {
                    Score newScore = new Score();
                    newScore.setScore(Double.parseDouble(question.get(2).get("score").toString()));
                    newScore.setQuestionSheet(questionSheet);

                    question1.addScore(newScore);
                }
            } else {
                Score newScore = new Score();
                newScore.setScore(Double.parseDouble(question.get(2).get("score").toString()));
                newScore.setQuestionSheet(questionSheet);

                question1.addScore(newScore);
            }
            save(question1);
        }
    }

    @Override
    public void remove(Long questionId, Long quizId) {
        Question question = findById(questionId).orElseThrow();
        QuestionSheet questionSheet = questionSheetService.findByQuiz_Id(quizId).orElseThrow();
        questionSheet.removeQuestion(question);
        questionSheetService.save(questionSheet);

        repository.deleteById(questionId);
    }

    private List<QuestionDTO> getQuestionDTOS(List<Question> questions) {
        List<QuestionDTO> questionDTO = new ArrayList<>();
        for (Question question : questions) {
            if (question instanceof Essays) {
                questionDTO.add(essaysMapper.convertEntityToDTO((Essays) question));
            } else {
                questionDTO.add(multipleChoiceMapper.convertEntityToDTO((MultipleChoice) question));
            }
        }
        return questionDTO;
    }
}
