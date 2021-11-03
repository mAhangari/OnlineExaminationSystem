package ir.maktab56.mapper;

import ir.maktab56.model.Student;
import ir.maktab56.service.dto.StudentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    Student convertDTOtoEntity(StudentDTO studentDTO);

    StudentDTO convertEntityToDTO(Student student);

    List<Student> convertListDTOtoEntity(List<StudentDTO> studentDTOList);

    List<StudentDTO> convertListEntityToDTO(List<Student> studentList);

}
