package ir.maktab56.mapper;

import ir.maktab56.model.Answer;
import ir.maktab56.service.dto.AnswerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AnswerMapper {

    Answer convertDTOtoEntity(AnswerDTO answerDTO);

    AnswerDTO convertEntityToDTO(Answer answer);

    List<Answer> convertListDTOtoEntity(List<AnswerDTO> answerDTOList);

    List<AnswerDTO> convertListEntityToDTO(List<Answer> answerList);
}
