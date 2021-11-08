package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = Quiz.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Quiz extends BaseEntity<Long> {

    public static final String TABLE_NAME = "quiz_table";
    private String title;
    private String description;
    private LocalTime quizTime;

    @OneToOne(cascade = CascadeType.ALL)
    private QuestionSheet questionSheet;
}
