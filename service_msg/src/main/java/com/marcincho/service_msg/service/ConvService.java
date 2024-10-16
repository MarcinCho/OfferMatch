package com.marcincho.service_msg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.Conversation;
import com.marcincho.service_msg.entity.UserEnt;
import com.marcincho.service_msg.mapper.ChatMessageMapper;
import com.marcincho.service_msg.models.ChatMessage;
import com.marcincho.service_msg.models.MessageDeliveryStatusEnum;
import com.marcincho.service_msg.models.MessageTypeInfo;
import com.marcincho.service_msg.models.UnseenMessageCountResponse;
import com.marcincho.service_msg.models.UserConnection;
import com.marcincho.service_msg.repository.ConversationRepository;
import com.marcincho.service_msg.repository.UserRepository;
import static com.marcincho.service_msg.utils.ConvUtils.getConvId;
import com.marcincho.service_msg.utils.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConvService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final ChatMessageMapper chatMessageMapper;
    private final ConversationRepository conversationRepository;
    private final OnlineOfflineService onlineOfflineService;

    public ConvService(UserRepository userRepository, SecurityUtils securityUtils, ChatMessageMapper chatMessageMapper, ConversationRepository conversationRepository, OnlineOfflineService onlineOfflineService, SimpMessageSendingOperations simpOperations) {
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.chatMessageMapper = chatMessageMapper;
        this.conversationRepository = conversationRepository;
        this.onlineOfflineService = onlineOfflineService;
    }

    public List<UserConnection> getUserContacts() {
        UserDetailsImpl userDetails = securityUtils.getUser();
        String username = userDetails.getUsername();
        UserEnt currentUserEnt = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
        List<UserEnt> contacts = userRepository.findAllByUsernameNot(username);
        return contacts.stream().map(userEnt -> UserConnection.builder()
                .connectionId(userEnt.getId())
                .connectionUsername(userEnt.getUsername())
                .convID(getConvId(currentUserEnt.getId(), userEnt.getId()))
                .unSeen(0)
                .isOnline(onlineOfflineService.isUserOnline(userEnt.getId()))
                .build())
                .toList();
    }

    public List<UnseenMessageCountResponse> getUnseenMessageCount() {
        List<UnseenMessageCountResponse> result = new ArrayList<>();
        UserDetailsImpl userDetails = securityUtils.getUser();
        List<Conversation> unseenMessages = conversationRepository.findUnseenMessagesCount(userDetails.getId());

        if (!CollectionUtils.isEmpty(unseenMessages)) {
            Map<UUID, List<Conversation>> unseenMessageCountByUser = new HashMap<>();
            for (Conversation conversation : unseenMessages) {
                List<Conversation> values = unseenMessageCountByUser.getOrDefault(conversation.getFromUser(), new ArrayList<>());
                values.add(conversation);
                unseenMessageCountByUser.put(conversation.getFromUser(), values);
            }
            log.info("There are unread messages for {}", userDetails.getUsername());
            unseenMessageCountByUser.forEach((user, conversations) -> {
                result.add(UnseenMessageCountResponse.builder()
                        .count((long) conversations.size())
                        .fromUser(user)
                        .build());
                updateMessageDelivery(user, conversations, MessageDeliveryStatusEnum.DELIVERED);
            });
        }
        return result;
    }

    public List<ChatMessage> getUnseenMessages(UUID fromUserId) {
        List<ChatMessage> result = new ArrayList<>();
        UserDetailsImpl userDetails = securityUtils.getUser();
        List<Conversation> unseenConversations = conversationRepository.findUnseen(userDetails.getId(), fromUserId);

        if (!CollectionUtils.isEmpty(unseenConversations)) {
            log.info("There are some unseen msg from {} to {}", fromUserId, userDetails.getId());
            updateMessageDelivery(fromUserId, unseenConversations, MessageDeliveryStatusEnum.SEEN);
            result = chatMessageMapper.toChatMessages(unseenConversations, userDetails, MessageTypeInfo.UNSEEN);
        }
        return result;
    }

    private void updateMessageDelivery(UUID user, List<Conversation> conversations, MessageDeliveryStatusEnum msgStatus) {
        conversations.forEach(msg -> msg.setDeliveryStatus(msgStatus.toString()));
        onlineOfflineService.notifySender(user, conversations, msgStatus);
        conversationRepository.saveAll(conversations);
    }

    public List<ChatMessage> setReadMessages(List<ChatMessage> chatMessages) {
        List<UUID> inTransitMsgIds = chatMessages.stream().map(ChatMessage::getId).toList();
        List<Conversation> conversations = conversationRepository
                .findAllById(inTransitMsgIds);
        conversations.forEach(msg -> msg.setDeliveryStatus(MessageDeliveryStatusEnum.SEEN.toString()));
        List<Conversation> saved = conversationRepository.saveAll(conversations);
        return chatMessageMapper.toChatMessages(saved, securityUtils.getUser(), MessageTypeInfo.CHAT);
    }
}
