package pl.politechnika.ikms.commons.util.mail.service;

public interface MailService {

    void sendSimpleMessage(String to, String subject, String text);
}
