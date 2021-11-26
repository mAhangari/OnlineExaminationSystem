package ir.maktab56.mapper;

import ir.maktab56.model.*;
import ir.maktab56.service.dto.AnswerDTO;
import ir.maktab56.service.dto.QuestionDTO;
import ir.maktab56.service.dto.QuestionSheetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;

@Mapper
public interface AnswerMapper {

    @Named(value = "answerToStringAnswer")
    static Map<String, String> answerToStringAnswer(Map<Question, String> answer) {
        Map<String, String> newAnswer = new HashMap<>();

        for (Question question : answer.keySet()) {
            newAnswer.put(question.getId().toString(), answer.get(question));
        }
        return newAnswer;
    }

    @Named(value = "questionSheetToQuestionSheetDTO")
    static QuestionSheetDTO questionSheetToQuestionSheetDTO(QuestionSheet questionSheet) {

        Set<QuestionDTO> questionDTO = new HashSet<>();
        EssaysMapper essaysMapper = new EssaysMapperImpl();
        MultipleChoiceMapper multipleChoiceMapper = new MultipleChoiceMapperImpl();

        for (Question question : questionSheet.getQuestions()) {
            if (question instanceof Essays) {
                questionDTO.add(essaysMapper.convertEntityToDTO((Essays) question));
            } else {
                questionDTO.add(multipleChoiceMapper.convertEntityToDTO((MultipleChoice) question));
            }
        }
        return new QuestionSheetDTO(questionSheet.getId(), questionDTO);
    }

    @Mapping(source = "answer", target = "answer", qualifiedByName = "answerToStringAnswer")
    @Mapping(source = "questionSheet", target = "questionSheet", qualifiedByName = "questionSheetToQuestionSheetDTO")
    AnswerDTO convertEntityToDTO(Answer answer);

    List<AnswerDTO> convertListEntityToDTO(List<Answer> answerList);
}
