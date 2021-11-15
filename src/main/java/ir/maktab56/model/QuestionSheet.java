package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = QuestionSheet.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionSheet extends BaseEntity<Long> {

    public static final String TABLE_NAME = "question_sheet_table";

    @ManyToMany
    private Set<Question> questions = new HashSet<>();

    @OneToOne
    private Quiz quiz;

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }
}
