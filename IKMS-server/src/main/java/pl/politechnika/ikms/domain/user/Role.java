package pl.politechnika.ikms.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(name="roles_seq_name",sequenceName="roles_seq", allocationSize=1,initialValue = 5)
public class Role extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_name")
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;
}
