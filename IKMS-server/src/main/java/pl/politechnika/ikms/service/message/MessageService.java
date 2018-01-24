package pl.politechnika.ikms.service.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.message.MessageDto;
import pl.politechnika.ikms.rest.dto.message.MessageWithSenderIdAndRecipientIdDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageService extends GenericService<MessageDto> {

    MessageDto sendMessage(MessageDto messageDto, HttpServletRequest request, String recipientUsername);

    MessageWithSenderIdAndRecipientIdDto sendMessageWithSenderAndRecipientDto(MessageDto messageDto,
            HttpServletRequest request, String recipientUsername);

    Page<MessageDto> findAllOfMyReceivedMessage(Pageable pageable, HttpServletRequest request);

    MessageDto findReceivedMessageById(Long idMessage, HttpServletRequest request);

    Page<MessageDto> findAllOfMySentMessage(Pageable pageable, HttpServletRequest request);

    MessageDto findSentMessageById(Long idMessage, HttpServletRequest request);

    void deleteMessageFromReceived(Long idMessage, HttpServletRequest request);

    void deleteMessageFromSent(Long idMessage, HttpServletRequest request);

    int countNumberOfUnreadMessages(HttpServletRequest request);

    List<MessageDto> findListAllNewestOfMyReceivedMessage(Long lastRecievedMessageId, HttpServletRequest request);

    List<MessageDto> findListAllNewestOfMySentMessage(Long lastRecievedMessageId, HttpServletRequest request);
}
