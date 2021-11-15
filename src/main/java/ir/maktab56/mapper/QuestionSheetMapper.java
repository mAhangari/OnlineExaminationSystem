package ir.maktab56.mapper;

import ir.maktab56.model.QuestionSheet;
import ir.maktab56.service.dto.QuestionSheetDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuestionSheetMapper {

    QuestionSheet convertDTOtoEntity(QuestionSheetDTO questionSheetDTO);

    QuestionSheetDTO convertEntityToDTO(QuestionSheet questionSheet);

    List<QuestionSheet> convertListDTOtoEntity(List<QuestionSheetDTO> questionSheetDTOList);

    List<QuestionSheetDTO> convertListEntityToDTO(List<QuestionSheet> questionSheetList);
}
