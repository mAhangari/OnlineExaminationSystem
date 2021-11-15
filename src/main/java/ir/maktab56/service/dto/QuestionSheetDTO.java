package ir.maktab56.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionSheetDTO {

    private Set<QuestionDTO> questions = new HashSet<>();

    private QuizDTO quiz;

}
