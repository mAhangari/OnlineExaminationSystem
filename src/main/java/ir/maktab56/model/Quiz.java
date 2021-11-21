package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = Quiz.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Quiz extends BaseEntity<Long> {

    public static final String TABLE_NAME = "quiz_table";
    private static final String COURSE_ID = "course_id";

    private String title;
    private String description;
    private Long quizTime;

    @OneToOne(cascade = CascadeType.ALL)
    private QuestionSheet questionSheet;

    @ManyToOne
    @JoinColumn(name = COURSE_ID)
    private Course course;

}
