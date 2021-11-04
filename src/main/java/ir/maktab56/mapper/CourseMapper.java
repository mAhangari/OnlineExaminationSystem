package ir.maktab56.mapper;

import ir.maktab56.model.Course;
import ir.maktab56.service.dto.CourseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    Course convertDTOtoEntity(CourseDTO courseDTO);

    CourseDTO convertEntityToDTO(Course course);

    List<Course> convertListDTOtoEntity(List<CourseDTO> courseDTOList);

    List<CourseDTO> convertListEntityToDTO(List<Course> courseList);
}
