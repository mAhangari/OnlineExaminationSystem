package ir.maktab56.service.dto;

import ir.maktab56.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDTO extends BaseEntity<Long> {

    private double score;

}
