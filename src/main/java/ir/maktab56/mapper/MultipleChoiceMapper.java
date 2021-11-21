package ir.maktab56.mapper;

import ir.maktab56.model.MultipleChoice;
import ir.maktab56.service.dto.MultipleChoiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MultipleChoiceMapper {

    MultipleChoice convertDTOtoEntity(MultipleChoiceDTO multipleChoiceDTO);

    MultipleChoiceDTO convertEntityToDTO(MultipleChoice multipleChoice);

    List<MultipleChoice> convertListDTOtoEntity(List<MultipleChoiceDTO> multipleChoiceDTOList);

    List<MultipleChoiceDTO> convertListEntityToDTO(List<MultipleChoice> multipleChoiceList);
}
