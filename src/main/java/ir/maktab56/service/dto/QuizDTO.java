package ir.maktab56.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizDTO {

    private Long id;

    @NotNull
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Min(value = 0, message = "Minimum value must be zero")
    @Max(value = 360, message = "Maximum value must be 360")
    private Long quizTime;
}
