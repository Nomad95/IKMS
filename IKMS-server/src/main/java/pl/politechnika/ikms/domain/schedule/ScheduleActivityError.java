package pl.politechnika.ikms.domain.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "schedule_activity_errors")
@ToString(exclude = {"activity"})
@EqualsAndHashCode(callSuper = true, exclude = {"activity"})
@SequenceGenerator(name="activities_err_seq_name",sequenceName="activities_err_seq", allocationSize = 1)
public class ScheduleActivityError extends AbstractEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_err_seq_name")
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private ScheduleActivityEntity activity;
}
