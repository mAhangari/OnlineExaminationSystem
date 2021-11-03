package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = Course.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Course extends BaseEntity<Long> {

    public static final String TABLE_NAME = "course_table";
    private static final String COURSE_ID = "course_id";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    private String title;

    @Column(name = COURSE_ID)
    private String courseId;

    @Column(name = START_DATE)
    private LocalDate startDate;

    @Column(name = END_DATE)
    private LocalDate endDate;

    @ManyToOne
    private Professor professor;

    @ManyToMany
    private Set<Student> student;

}
