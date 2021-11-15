package ir.maktab56.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class MultipleChoiceDTO {

    private Long id;

    private String title;

    private String question;

    private double score;

    private String correctAnswer;

    private Set<String> options;

}
