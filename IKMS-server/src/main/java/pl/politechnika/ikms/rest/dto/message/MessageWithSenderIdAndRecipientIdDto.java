package pl.politechnika.ikms.rest.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeDeserializer;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeSerializer;
import pl.politechnika.ikms.rest.dto.user.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageWithSenderIdAndRecipientIdDto extends AbstractDto{

        private Long id;

        private Long senderId;

        private String senderUsername;

        private String senderFullName;

        private Long recipientId;

        private String recipientUsername;

        private String recipientFullName;

        @NotNull
        private String title;

        private LocalDateTime dateOfSend;

        @NotNull
        private String messageContent;

        private Boolean wasRead;
}
