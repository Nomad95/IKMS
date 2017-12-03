package pl.politechnika.ikms.domain.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeDeserializer;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeSerializer;
import pl.politechnika.ikms.domain.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"sender_id", "recipient_id" })
@Table(name = "messages")
@ToString(exclude = {"sender_id", "recipient_id" })
@SequenceGenerator(name = "messages_seq_name", sequenceName = "messages_seq", allocationSize = 1)
public class MessageEntity extends AbstractEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_seq_name")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Column(name = "title")
    @NotNull
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;

    @Column(name = "date_of_send")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfSend;

    @Lob
    @Column(name = "message_content")
    @Type(type = "org.hibernate.type.TextType")
    @NotNull
    private String messageContents;

    @Column(name = "was_read")
    private Boolean wasRead;

    @Column(name = "recipient_username")
    @NotNull
    private String recipientUsername;

    @Column(name = "sender_username")
    @NotNull
    private String senderUsername;

    @Column(name = "recipient_full_name")
    @NotNull
    private String recipientFullName;

    @Column(name = "sender_full_name")
    @NotNull
    private String senderFullName;
}
