package ir.maktab56.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CourseDTO {

    private String title;

    private String courseId;

    private LocalDate startDate;

    private LocalDate endDate;

    private ProfessorDTO professor;

}
