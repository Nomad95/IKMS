package pl.politechnika.ikms.rest.controller.message;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.message.MessageEntity;
import pl.politechnika.ikms.rest.dto.message.MessageDto;
import pl.politechnika.ikms.rest.mapper.message.MessageEntityMapper;
import pl.politechnika.ikms.service.message.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final @NonNull MessageService messageService;

    private final @NonNull MessageEntityMapper messageEntityMapper;

    @PostMapping("/user/{recipientUsername}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDto sendMessageToUser(@RequestBody @Valid MessageDto messageDto,
                                        HttpServletRequest request,
                                        @PathVariable("recipientUsername") String recipientUsername) {
        MessageEntity messageToSend = messageService
                .sendMessage(messageEntityMapper.convertToEntity(messageDto), request, recipientUsername);

        return messageEntityMapper.convertToDto(messageToSend);
    }

    @GetMapping("/myMessages/received")
    public Page<MessageDto> getAllOfMyReceivedMessage(Pageable pageable, HttpServletRequest request) {
        Page<MessageEntity> myReceivedMessages = messageService.findAllOfMyReceivedMessage(pageable, request);

        return myReceivedMessages.map(messageEntityMapper::convertToDto);
    }

    @GetMapping(value = "/myMessages/received/{idMessage}")
    public MessageDto getReceivedMessageById(@PathVariable("idMessage") Long idMessage, HttpServletRequest request) {
        MessageEntity message = messageService.findReceivedMessageById(idMessage, request);

        return messageEntityMapper.convertToDto(message);
    }

    @GetMapping(value = "/myMessages/sent")
    public Page<MessageDto> getAllOfMySentMessage(Pageable pageable, HttpServletRequest request) {
        Page<MessageEntity> mySentMessages = messageService.findAllOfMySentMessage(pageable, request);

        return mySentMessages.map(messageEntityMapper::convertToDto);
    }

    @GetMapping(value = "/myMessages/sent/{idMessage}")
    public MessageDto getSentMessageById(@PathVariable("idMessage") Long idMessage, HttpServletRequest request) {
        MessageEntity message = messageService.findSentMessageById(idMessage, request);

        return messageEntityMapper.convertToDto(message);
    }

    @DeleteMapping(value = "myMessages/received/{idMessage}")
    public void deleteMessageFromReceived(@PathVariable("idMessage") Long idMessage,
                                                                     HttpServletRequest request) {
        messageService.deleteMessageFromReceived(idMessage, request);
    }

    @DeleteMapping(value = "myMessages/sent/{idMessage}")
    public void deleteMessageFromSent(@PathVariable("idMessage") Long idMessage,
                                                                 HttpServletRequest request) {
        messageService.deleteMessageFromSent(idMessage, request);
    }

    @GetMapping(value = "/myMessages/received/quantity/unread")
    public String getNumberOfUnreadMessage(HttpServletRequest request) {
        int count = messageService.countNumberOfUnreadMessages(request);

        return "{\"count\": \" " + count + "\"}";

    }






}
