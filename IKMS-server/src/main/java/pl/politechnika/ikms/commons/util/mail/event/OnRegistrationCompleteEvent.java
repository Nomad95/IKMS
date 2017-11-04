package pl.politechnika.ikms.commons.util.mail.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;

import java.util.Locale;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;

    private Locale locale;

    private UserRegistrationDto userRegistrationDto;

    public OnRegistrationCompleteEvent(
            UserRegistrationDto userRegistrationDto, Locale locale, String appUrl) {
        super(userRegistrationDto);

        this.userRegistrationDto = userRegistrationDto;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}