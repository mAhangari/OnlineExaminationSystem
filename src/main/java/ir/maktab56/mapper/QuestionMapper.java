package ir.maktab56.mapper;

import ir.maktab56.model.Question;
import ir.maktab56.service.dto.QuestionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {

    Question convertDTOtoEntity(QuestionDTO questionDTO);

    QuestionDTO convertEntityToDTO(Question question);

    List<Question> convertListDTOtoEntity(List<QuestionDTO> questionDTOList);

    List<QuestionDTO> convertListEntityToDTO(List<Question> questionList);
}
