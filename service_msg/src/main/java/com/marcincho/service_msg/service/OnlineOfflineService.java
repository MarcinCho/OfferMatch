package com.marcincho.service_msg.service;

import com.marcincho.service_msg.config.UserDetailsImpl;
import com.marcincho.service_msg.entity.Conversation;
import com.marcincho.service_msg.entity.UserEnt;
import com.marcincho.service_msg.models.*;
import com.marcincho.service_msg.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@Slf4j
public class OnlineOfflineService {

    private final Set<UUID> usersOnline;
    private final Map<UUID, Set<String>> userSubscriptions;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations simpOperations;

    public OnlineOfflineService( UserRepository userRepository, SimpMessageSendingOperations simpOperations) {
        this.usersOnline = new ConcurrentSkipListSet<>();
        this.userSubscriptions = new ConcurrentHashMap<>();
        this.userRepository = userRepository;
        this.simpOperations = simpOperations;
    }
    public void addOnlineUser(Principal user) {
        if (user != null) {
            UserDetailsImpl userDetails = getUserDetails(user);
            log.info("User {} is online", userDetails.getUsername());
            for (UUID userId : usersOnline) {
                simpOperations.convertAndSend(
                        "/topic/" + userId, ChatMessage.builder()
                                .type(MessageTypeInfo.FRIEND_OFFLINE)
                                .userConnection(UserConnection.builder()
                                                        .connectionId(userDetails.getId()).build()).build());
            }
        } else {
            log.error("AddOnlineUser: user is null");
        }
    }

    private UserDetailsImpl getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        Object object = user.getPrincipal();
        return (UserDetailsImpl) object;
    }

    public void addUserSubscription(Principal user, String subscriptionChannel) {
        UserDetailsImpl userDetails = getUserDetails(user);
        log.info("{} subscribed to {}", userDetails.getUsername(), subscriptionChannel);
        Set<String> subscriptions = userSubscriptions.getOrDefault(userDetails.getId(), new HashSet<>());
        subscriptions.add(subscriptionChannel);
        userSubscriptions.put(userDetails.getId(), subscriptions);
    }

    public boolean isUserOnline(UUID userId) {
        return usersOnline.contains(userId);
    }

    public boolean isUserSubscribed(UUID toUserId, String subscription) {
        Set<String> subscriptions = userSubscriptions.getOrDefault(toUserId, new HashSet<>());
        return subscriptions.contains(subscription);
    }

    public void removeOnlineUser(Principal user) {
        if (user != null) {
            UserDetailsImpl userDetails = getUserDetails(user);
            log.info("User {} is offline", userDetails.getUsername());
            usersOnline.remove(getUserDetails(user).getId());
            userSubscriptions.remove(userDetails.getId());
            for (UUID userId : usersOnline) {
                simpOperations.convertAndSend(
                        "/topic/" + userId, ChatMessage.builder()
                                .type(MessageTypeInfo.FRIEND_OFFLINE)
                                .userConnection(UserConnection.builder().connectionId(userDetails.getId()).build()).build());
            }
        }
    }

    public void removeUserSubscription(Principal user, String unsubscribedChannel) {
        UserDetailsImpl userDetails = getUserDetails(user);
        log.info("User {} unsubscribed from channel {}", userDetails.getUsername(), unsubscribedChannel);
        Set<String> subscriptions = userSubscriptions.getOrDefault(userDetails.getId(), new HashSet<>());
        subscriptions.remove(unsubscribedChannel);
        userSubscriptions.put(userDetails.getId(), subscriptions);
    }

    public List<UserResponse> getOnlineUsers() {
        return userRepository.findAllById(usersOnline).stream()
                .map(
                        userEntity -> new UserResponse(
                                userEntity.getId(), userEntity.getUsername(), userEntity.getEmail()))
                .toList();
    }

    public void notifySender(UUID userId, List<Conversation> conversations, MessageDeliveryStatusEnum msgStatus) {
        if (!isUserOnline(userId)) {
            log.info("{} not online saving msg", userId.toString());
            return;
        }
        List<MessageDeliveryStatusUpdate> messageDeliveryStatusUpdates = conversations.stream()
                .map(e ->
                        MessageDeliveryStatusUpdate.builder()
                                .id(e.getId())
                                .messageDeliveryStatusEnum(msgStatus)
                                .content(e.getContent())
                                .build())
                .toList();
        conversations.forEach( conv ->
                simpOperations.convertAndSend(
                        "/topid/" + userId, ChatMessage.builder()
                                .id(conv.getId())
                                .messageDeliveryStatusUpdates(messageDeliveryStatusUpdates)
                                .type(MessageTypeInfo.MESSAGE_DELIVERY_UPDATE)
                                .content(conv.getContent())
                                .build()));
    }

    public Map<String, Set<String>> getUserSubscribed() {
        Map<String, Set<String>> result = new HashMap<>();
        List<UserEnt> users = userRepository.findAllById(userSubscriptions.keySet());
        users.forEach(user -> result.put(user.getUsername(), userSubscriptions.get(user.getId())));
        return result;
    }
}
