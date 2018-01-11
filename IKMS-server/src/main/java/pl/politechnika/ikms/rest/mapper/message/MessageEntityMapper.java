package pl.politechnika.ikms.rest.mapper.message;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.message.MessageEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.message.MessageDto;
import pl.politechnika.ikms.rest.dto.message.MessageWithSenderIdAndRecipientIdDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityMapper;

@Component
public class MessageEntityMapper extends AbstractModelMapper<MessageEntity, MessageDto> {

    @Autowired
    private final @NonNull
    UserRepository userRepository;

    @Autowired
    private final @NonNull
    UserEntityMapper userEntityMapper;

    public MessageEntityMapper(ModelMapper modelMapper,
                               UserRepository userRepository,
                               UserEntityMapper userEntityMapper) {
        super(modelMapper);
        this.userEntityMapper = userEntityMapper;
        this.userRepository = userRepository;
    }

    //TODO: [Arek] Mapowania encji na dto przez modelMapper w tym przypadku wywala exception z
    //matches multiple source property hierarchies problem. Później popatrze czy da się to rozwiązać
    //bo to jest jakiś problem z konfiguracją modelMappera

    @Override
    public MessageDto convertToDto(MessageEntity messageEntity) {
        MessageDto messageDto = new MessageDto();
        messageDto.setDateOfSend(messageEntity.getDateOfSend());
        messageDto.setId(messageEntity.getId());
        messageDto.setMessageContents(messageEntity.getMessageContents());
        if(messageEntity.getRecipient() != null)
        messageDto.setRecipient(userEntityMapper.convertToDto(userRepository.findOne(messageEntity.getRecipient().getId())));
        if(messageEntity.getSender() != null)
        messageDto.setSender(userEntityMapper.convertToDto(userRepository.findOne(messageEntity.getSender().getId())));
        messageDto.setRecipientUsername(messageEntity.getRecipientUsername());
        messageDto.setSenderUsername(messageEntity.getSenderUsername());
        messageDto.setTitle(messageEntity.getTitle());
        messageDto.setWasRead(messageEntity.getWasRead());
        messageDto.setRecipientFullName(messageEntity.getRecipientFullName());
        messageDto.setSenderFullName(messageEntity.getSenderFullName());

        return messageDto;
    }


    public MessageWithSenderIdAndRecipientIdDto convertToMessageWithSenderIdAndRecipientIdDto(final MessageEntity messageEntity) {
        return MessageWithSenderIdAndRecipientIdDto.builder()
                .dateOfSend(messageEntity.getDateOfSend())
                .id(messageEntity.getId())
                .messageContent(messageEntity.getMessageContents())
                .recipientId(messageEntity.getRecipient().getId())
                .senderId(messageEntity.getSender().getId())
                .recipientUsername(messageEntity.getRecipientUsername())
                .senderUsername(messageEntity.getSenderUsername())
                .title(messageEntity.getTitle())
                .wasRead(messageEntity.getWasRead())
                .recipientFullName(messageEntity.getRecipientFullName())
                .senderFullName(messageEntity.getSenderFullName()).build();

    }

    @Override
    public MessageEntity convertToEntity(MessageDto messageDto) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setDateOfSend(messageDto.getDateOfSend());
        messageEntity.setId(messageDto.getId());
        messageEntity.setMessageContents(messageDto.getMessageContents());
        if (messageDto.getRecipient() != null)
            messageEntity.setRecipient(userRepository.findOne(messageDto.getRecipient().getId()));
        if (messageDto.getSender() != null)
            messageEntity.setSender(userRepository.findOne(messageDto.getSender().getId()));
        messageEntity.setRecipientUsername(messageDto.getRecipientUsername());
        messageEntity.setSenderUsername(messageDto.getSenderUsername());
        messageEntity.setTitle(messageDto.getTitle());
        messageEntity.setWasRead(messageDto.getWasRead());
        messageEntity.setRecipientFullName(messageDto.getRecipientFullName());
        messageEntity.setSenderFullName(messageDto.getSenderFullName());

        return messageEntity;
    }
}
