package ir.maktab56.service.impl;

import ir.maktab56.mapper.EssaysMapper;
import ir.maktab56.mapper.MultipleChoiceMapper;
import ir.maktab56.mapper.QuestionSheetMapper;
import ir.maktab56.model.Essays;
import ir.maktab56.model.MultipleChoice;
import ir.maktab56.model.Question;
import ir.maktab56.model.QuestionSheet;
import ir.maktab56.repository.QuestionSheetRepository;
import ir.maktab56.service.QuestionSheetService;
import ir.maktab56.service.dto.QuestionDTO;
import ir.maktab56.service.dto.QuestionSheetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionSheetServiceImpl implements QuestionSheetService {

    private final QuestionSheetRepository repository;
    private final EssaysMapper essaysMapper;
    private final QuestionSheetMapper questionSheetMapper;
    private final MultipleChoiceMapper multipleChoiceMapper;
    private QuestionSheetService questionSheetService;

    @Autowired
    public void setQuestionSheetService(QuestionSheetService questionSheetService) {
        this.questionSheetService = questionSheetService;
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
}
