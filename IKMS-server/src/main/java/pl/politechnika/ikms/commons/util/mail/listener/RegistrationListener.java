package pl.politechnika.ikms.commons.util.mail.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.util.CommonConstants;
import pl.politechnika.ikms.commons.util.mail.event.OnRegistrationCompleteEvent;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    /**
     * Recieve emitted event, send email with access data for new user
     */
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserRegistrationDto userRegistrationDto = event.getUserRegistrationDto();

        String recipientAddress = userRegistrationDto.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String message = makeTemplateMessage(userRegistrationDto.getUsername(),userRegistrationDto.getPassword());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(CommonConstants.REGISTRATION_EMAIL_TITLE);
        email.setText(message);
        mailSender.send(email);
    }

    /**
     * @return Template message that is written to email text
     */
    private String makeTemplateMessage(String username, String password){
        return "Witaj,\n\nUtworzyliśmy dla Ciebie konto w systemie przedszkola integracyjnego!\n" +
                "Aby je aktywować, prosimy o zalogowanie się do systemu następującymi danymi: \n\n" +
                "login: " + username + "\nhasło: " + password + "\n\n\nPozdrawiamy,\nIKMS ;)";

    }
}
