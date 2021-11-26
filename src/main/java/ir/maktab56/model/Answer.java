package ir.maktab56.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ir.maktab56.json_formater.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = Answer.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "answer_table";
    private static final String STUDENT_ID = "student_id";
    private static final String QUESTION_SHEET_ID = "question_sheet_id";
    private static final String START_ANSWER_DATE_TIME = "start_answer_date_time";
    private static final String END_ANSWER_DATE_TIME = "end_answer_date_time";
    private static final String ANSWER_QUESTION_MAPPING = "answer_question_mapping";
    private static final String QUESTION_ID = "question_id";
    private static final String ANSWER_ID = "answer_id";
    private static final String ID = "id";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = ANSWER_QUESTION_MAPPING,
            joinColumns = {@JoinColumn(name = ANSWER_ID, referencedColumnName = ID)})
    @MapKeyJoinColumn(name = "question_id")
    private Map<Question, String> answer;

    @Min(value = 0, message = "The value must be positive")
    private double score;

    @Column(name = START_ANSWER_DATE_TIME)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startAnswerDateTime;

    @Column(name = END_ANSWER_DATE_TIME)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime endAnswerDateTime;

    @ManyToOne
    @JoinColumn(name = STUDENT_ID)
    private Student student;

    @ManyToOne
    @JoinColumn(name = QUESTION_SHEET_ID)
    private QuestionSheet questionSheet;
}
