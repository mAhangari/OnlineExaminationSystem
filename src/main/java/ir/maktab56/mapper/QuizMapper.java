package ir.maktab56.mapper;

import ir.maktab56.model.Quiz;
import ir.maktab56.service.dto.QuizDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuizMapper {

    Quiz convertDTOtoEntity(QuizDTO quizDTO);

    QuizDTO convertEntityToDTO(Quiz quiz);

    List<Quiz> convertListDTOtoEntity(List<QuizDTO> quizDTOList);

    List<QuizDTO> convertListEntityToDTO(List<Quiz> quizList);
}
