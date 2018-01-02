package pl.politechnika.ikms.domain.upload;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.person.ParentEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "didactic_materials")
@ToString(exclude = {"sharedGroups","sharedParents","sharedEmployees"})
@EqualsAndHashCode(callSuper = true, exclude = {"sharedGroups","sharedParents","sharedEmployees"})
@SequenceGenerator(name="didactic_materials_seq_name",sequenceName="didactic_materials_seq", allocationSize = 1)
public class DidacticMaterialFileEntity extends AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "didactic_materials_seq_name", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "filename", length = 100, nullable = false)
    private String filename;

    @Column(name = "folder", length = 50, nullable = false)
    private String folder;

    @Column(name = "subfolder", length = 50, nullable = false)
    private String subfolder;

    @Column(name = "size", length = 20, nullable = false)
    private String size;

    @Column(name = "extension", length = 5)
    private String extension;

    @Column(name = "icon", length = 20)
    private String icon;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "content")
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @Column(name = "date_of_upload", nullable = false)
    private LocalDate dateOfUpload;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "didactic_has_groups",
            joinColumns = {@JoinColumn(name = "didactic_material_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<GroupEntity> sharedGroups = Lists.newArrayList();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "didactic_has_parents",
    joinColumns = {@JoinColumn(name = "didactic_material_id")},
    inverseJoinColumns = {@JoinColumn(name = "parent_id")})
    private List<ParentEntity> sharedParents = Lists.newArrayList();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "didactic_has_employees",
            joinColumns = {@JoinColumn(name = "didactic_material_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<EmployeeEntity> sharedEmployees = Lists.newArrayList();
}
