package ir.maktab56.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Question.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question extends BaseEntity<Long> {

    public static final String TABLE_NAME = "question_table";
    private static final String QUESTION_BANK_ID = "question_bank_id";
    private static final String PROFESSOR_ID = "professor_id";
    private static final String TITLE = "title";
    private static final String SCORE_ID = "score_id";

    @Column(name = TITLE, nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    private String question;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Score> scores = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = PROFESSOR_ID)
    private Professor professor;

    public void addScore(Score score) {
        scores.add(score);
    }
}
