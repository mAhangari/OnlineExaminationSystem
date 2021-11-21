package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultipleChoice extends Question {

    private static final String OPTION_TABLE = "option_table";
    private static final String MULTIPLE_CHOICE_ID = "multiple_choice_id";
    private static final String CORRECT_ANSWER = "correct_answer";

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = OPTION_TABLE, joinColumns = @JoinColumn(name = MULTIPLE_CHOICE_ID))
    private Set<String> options = new HashSet<>();

    @Column(name = CORRECT_ANSWER)
    private String correctAnswer;

    public void addOption(String option) {
        this.options.add(option);
    }
}
