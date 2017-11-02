package pl.politechnika.ikms.commons.util.mail.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String email;
    private String title;
    private String content;
}
