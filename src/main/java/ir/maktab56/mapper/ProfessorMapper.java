package ir.maktab56.mapper;

import ir.maktab56.model.Professor;
import ir.maktab56.service.dto.ProfessorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProfessorMapper {

    Professor convertDTOtoEntity(ProfessorDTO professorDTO);

    ProfessorDTO convertEntityToDTO(Professor professor);

    List<Professor> convertListDTOtoEntity(List<ProfessorDTO> professorDTOList);

    List<ProfessorDTO> convertListEntityToDTO(List<Professor> professorList);

}
