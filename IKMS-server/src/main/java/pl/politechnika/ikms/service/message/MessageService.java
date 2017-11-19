package pl.politechnika.ikms.service.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.message.MessageEntity;

import javax.servlet.http.HttpServletRequest;

public interface MessageService extends GenericService<MessageEntity>{

    MessageEntity sendMessage(MessageEntity messageEntity, HttpServletRequest request, String recipientUsername);

    Page<MessageEntity> findAllOfMyReceivedMessage(Pageable pageable, HttpServletRequest request);

    MessageEntity findReceivedMessageById(Long idMessage, HttpServletRequest request);

    Page<MessageEntity> findAllOfMySentMessage(Pageable pageable, HttpServletRequest request);

    MessageEntity findSentMessageById(Long idMessage, HttpServletRequest request);

    void deleteMessageFromReceived(Long idMessage, HttpServletRequest request);

    void deleteMessageFromSent(Long idMessage, HttpServletRequest request);

    int countNumberOfUnreadMessages(HttpServletRequest request);
}
