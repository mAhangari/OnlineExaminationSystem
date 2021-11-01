package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Operation.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Operation extends BaseEntity<Long> {

    public static final String TABLE_NAME = "operation_table";
    private static final String NAME = "name";

    @Column(name = NAME)
    private String name;
}
