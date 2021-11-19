package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = Score.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Score extends BaseEntity<Long> {

    public static final String TABLE_NAME = "score_table";
    private static final String QUESTION_SHEET_ID = "question_sheet_id";

    private double score;

    @OneToOne
    @JoinColumn(name = QUESTION_SHEET_ID, nullable = false)
    private QuestionSheet questionSheet;
}
