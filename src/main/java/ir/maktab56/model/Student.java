package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Student extends User {

    private Long StudentId;

    @ManyToMany(mappedBy = "students")
    private Set<Course> course = new HashSet<>();
    
}
