package pl.politechnika.ikms.domain.register;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.schedule.ClassesType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Data
@Table(name = "register")
@EqualsAndHashCode(callSuper = true, exclude = "id")
@ToString(exclude = { "entries"})
@SequenceGenerator(name="register_seq_name",sequenceName="register_seq", allocationSize = 1)
public class RegisterEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_seq_name")
    @Column(name = "id")
    private Long id;

    @Column(name = "classes_type")
    @Enumerated(EnumType.STRING)
    private ClassesType classesType;

    @Column(name = "activity_start")
    private LocalDateTime activityStart;

    @Column(name = "leader_id")
    private Long leaderId;

    @OneToMany(mappedBy = "register", fetch = FetchType.LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PresenceEntity> presences = newArrayList();

    @OneToMany(mappedBy = "register", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<RegisterEntryEntity> entries = newArrayList();

}
