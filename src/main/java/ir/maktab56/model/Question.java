package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = Question.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question extends BaseEntity<Long> {

    public static final String TABLE_NAME = "question_table";
    public static final String QUESTION_BANK_ID = "question_bank_id";
    public static final String PROFESSOR_ID = "professor_id";
    public static final String TITLE = "title";

    @Column(name = TITLE, nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    private String question;

    private double score;

    @ManyToOne
    @JoinColumn(name = PROFESSOR_ID)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = QUESTION_BANK_ID)
    private QuestionBank questionBank;
}
