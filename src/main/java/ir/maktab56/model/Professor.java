package ir.maktab56.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Professor extends User {

    private Long personnelId;
}
