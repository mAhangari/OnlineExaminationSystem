package ir.maktab56.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Professor extends User {

    private Long personnelId;

    @OneToMany(mappedBy = "professor")
    private Set<Course> courses = new HashSet<>();
}
