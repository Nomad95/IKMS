package pl.politechnika.ikms.commons.util.mail.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.politechnika.ikms.commons.util.mail.DTO.MailDTO;
import pl.politechnika.ikms.commons.util.mail.service.MailService;

@RestController
@RequestMapping(value = "/api/mail")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailController {

    private final @NonNull
    MailService mailService;

    /**
     * Example method that sends simple email
     * @return true if email was sent
     */
    @RequestMapping(
            value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Boolean> sendEmail(@RequestBody MailDTO mailDTO){
        mailService.sendSimpleMessage(mailDTO.getEmail(),mailDTO.getTitle(),mailDTO.getContent());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
