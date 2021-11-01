package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Role.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Role extends BaseEntity<Long> {

    public static final String TABLE_NAME = "role_table";
    private static final String NAME = "name";

    @Column(name = NAME)
    private String name;

    @ManyToMany
    private Set<Operation> operations = new HashSet<>();

}
