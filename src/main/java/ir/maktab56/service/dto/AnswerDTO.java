package ir.maktab56.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerDTO {

    private Long id;

    private Map<String, String> answer;

    private double score;

    private LocalDateTime startAnswerDateTime;

    private LocalDateTime endAnswerDateTime;

    private StudentDTO student;

    private QuestionSheetDTO questionSheet;

}
