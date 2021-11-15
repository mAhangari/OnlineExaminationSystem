package ir.maktab56.mapper;

import ir.maktab56.model.Essays;
import ir.maktab56.service.dto.EssaysDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EssaysMapper {

    Essays convertDTOtoEntity(EssaysDTO essaysDTO);

    EssaysDTO convertEntityToDTO(Essays essays);

    List<Essays> convertListDTOtoEntity(List<EssaysDTO> essaysDTOList);

    List<EssaysDTO> convertListEntityToDTO(List<Essays> essaysList);
}
