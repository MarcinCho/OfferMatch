package com.marcincho.service_msg.config.webSocket;

import com.marcincho.service_msg.service.OnlineOfflineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketEventListener {

    private final OnlineOfflineService onlineOfflineService;

    private final Map<String, String> simpSessionIdToSubscriptionId;

    public WebSocketEventListener(OnlineOfflineService onlineOfflineService) {
        this.onlineOfflineService = onlineOfflineService;
        this.simpSessionIdToSubscriptionId = new ConcurrentHashMap<>();
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        onlineOfflineService.removeOnlineUser(event.getUser());
    }

    @EventListener
    @SendToUser
    public void handleSubscribeEvent(SessionSubscribeEvent subscribeEvent) {
        String subscriptionChannel = subscribeEvent.getMessage().getHeaders()
                                                                    .get("simpDestination").toString();
        String simpSessionId = subscribeEvent.getMessage().getHeaders().get("simpSessionId").toString();
        if (subscriptionChannel == null || simpSessionId == null) {
            log.error("Subscription channel or simp session ID.");
            return;
        }
        simpSessionIdToSubscriptionId.put(simpSessionId, subscriptionChannel);
        onlineOfflineService.addUserSubscription(subscribeEvent.getUser(), subscriptionChannel);
    }

    @EventListener
    public void handleUnSubscription(SessionUnsubscribeEvent unsubscribeEvent) {
        String simpSessionId = unsubscribeEvent.getMessage().getHeaders().get("simpSessionId").toString();
        String unsubscribedChannel = simpSessionIdToSubscriptionId.get(simpSessionId);
        onlineOfflineService.removeUserSubscription(unsubscribeEvent.getUser(), unsubscribedChannel);
    }

    @EventListener
    public void handleConnectEvent(SessionConnectedEvent connectedEvent) {
        onlineOfflineService.addOnlineUser(connectedEvent.getUser());
    }


}
