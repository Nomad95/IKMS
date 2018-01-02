package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "personalData")
@Table(name = "parents")
@ToString(exclude = {"personalData", "sharedFiles"})
@SequenceGenerator(name="parents_seq_name",sequenceName="parents_seq", allocationSize = 1)
public class ParentEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parents_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id", nullable = false)
    private PersonalDataEntity personalData;

    @ManyToMany(mappedBy = "sharedParents")
    private List<DidacticMaterialFileEntity> sharedFiles;
}
