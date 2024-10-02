package com.marcincho.service_msg.mapper;

import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.Conversation;
import com.marcincho.service_msg.entity.User;
import com.marcincho.service_msg.models.ChatMessage;
import com.marcincho.service_msg.models.MessageTypeInfo;
import com.marcincho.service_msg.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ChatMessageMapper {

    private final UserRepository userRepository;

    public ChatMessageMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ChatMessage> toChatMessages(List<Conversation> conversations, UserDetailsImpl userDetails, MessageTypeInfo messageTypeInfo) {
        List<UUID> fromUserIds = conversations.stream().map(Conversation::getFromUser).toList();
        Map<UUID, String> fromUserNames = userRepository.findAllById(fromUserIds).stream().collect(Collectors.toMap(User::getId, User::getUsername));

        return conversations.stream().map(e -> toChatMessage(e, userDetails, fromUserNames, messageTypeInfo)).toList();

    }

    private static  ChatMessage toChatMessage(Conversation e, UserDetailsImpl userDetails, Map<UUID, String> fromUserNames, MessageTypeInfo messageTypeInfo) {
        return ChatMessage.builder()
                .id(e.getId())
                .type(messageTypeInfo)
                .content(e.getContent())
                .receiverId(e.getToUser())
                .receiverUsername(userDetails.getUsername())
                .senderId(e.getFromUser())
                .senderUsername(fromUserNames.get(e.getFromUser()))
                .build();
    }

}
