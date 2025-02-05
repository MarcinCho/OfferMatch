package com.marcincho.service_msg.service;

import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.Conversation;
import com.marcincho.service_msg.models.MessageDeliveryStatusEnum;
import com.marcincho.service_msg.repository.ConversationRepository;
import com.marcincho.service_msg.models.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
public class ChatService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final ConversationRepository conversationRepository;

    private final OnlineOfflineService onlineOfflineService;

    public ChatService(SimpMessageSendingOperations simpMessageSendingOperations, ConversationRepository conversationRepository, OnlineOfflineService onlineOfflineService) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.conversationRepository = conversationRepository;
        this.onlineOfflineService = onlineOfflineService;
    }


    public void sendMessageToConvId(ChatMessage chatMessage, String conversationId, SimpMessageHeaderAccessor headerAccessor) {
        UserDetailsImpl userDetails = getUser();
        UUID fromUserId = userDetails.getId();
        UUID toUserId = chatMessage.getReceiverId();
        populateContext(chatMessage, userDetails);
        boolean isTargetOnline = onlineOfflineService.isUserOnline(toUserId);
        boolean isTargetSubscribed = onlineOfflineService.isUserSubscribed(toUserId, "/topic/" + conversationId);
        chatMessage.setId(UUID.randomUUID());

        Conversation.ConversationBuilder conversationBuilder = Conversation.builder();

        conversationBuilder
                .id(chatMessage.getId())
                .fromUser(fromUserId)
                .toUser(toUserId)
                .content(chatMessage.getContent())
                .convId(conversationId);
        if (!isTargetOnline) {
            log.info(
                    "{} is Offline. Content saved.", chatMessage.getReceiverUsername()
            );
            conversationBuilder.deliveryStatus(MessageDeliveryStatusEnum.NOT_DELIVERED.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.NOT_DELIVERED);
        } else if (!isTargetSubscribed) {
            log.info(
                    "{} is online but not subscribed, sending to private.", chatMessage.getReceiverUsername()
            );
            conversationBuilder.deliveryStatus(MessageDeliveryStatusEnum.DELIVERED.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.DELIVERED);
            simpMessageSendingOperations.convertAndSend("/topic/" + toUserId.toString(), chatMessage);

        } else {
            conversationBuilder.deliveryStatus(MessageDeliveryStatusEnum.SEEN.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.SEEN);
        }
        conversationRepository.save(conversationBuilder.build());
        simpMessageSendingOperations.convertAndSend("/topic/" + conversationId, chatMessage);
    }

    private void populateContext(ChatMessage chatMessage, UserDetailsImpl userDetails) {
        chatMessage.setSenderUsername(userDetails.getUsername());
        chatMessage.setSenderId(userDetails.getId());
    }

    private UserDetailsImpl getUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetailsImpl) object;
    }
}
