package pl.politechnika.ikms.rest.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SenderDto {
    private Long senderId;
    private String senderFullName;


}
