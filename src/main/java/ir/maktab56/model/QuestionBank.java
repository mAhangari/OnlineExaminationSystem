package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = QuestionBank.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionBank extends BaseEntity<Long> {

    public static final String TABLE_NAME = "question_bank_table";

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @OneToOne
    private Course course;

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

}
