package pl.politechnika.ikms.rest.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeDeserializer;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeSerializer;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.user.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends AbstractDto{

        private Long id;

        private UserDto sender;

        @NotNull
        private String title;

        private UserDto recipient;

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime dateOfSend;

        @NotNull
        private String messageContents;

        private Boolean wasRead;

        private String recipientUsername;

        private String senderUsername;

        private String recipientFullName;

        private String senderFullName;

}
