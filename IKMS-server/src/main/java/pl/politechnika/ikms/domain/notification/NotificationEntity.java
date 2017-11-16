package pl.politechnika.ikms.domain.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeDeserializer;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeSerializer;
import pl.politechnika.ikms.domain.notification.enums.Priority;
import pl.politechnika.ikms.domain.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = "recipient")
@Table(name = "notifications")
@ToString(exclude = "recipient")
@SequenceGenerator(name="notifications_seq_name",sequenceName="notifications_seq", allocationSize = 1)
public class NotificationEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "notifications_seq_name")
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @Size(max=200)
    @NotNull
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserEntity recipient;

    @Column(name = "date_of_send")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfSend;

    @Column(name = "was_read")
    private Boolean wasRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "sender")
    private String senderFullName;


}
