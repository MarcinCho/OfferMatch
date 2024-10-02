package com.marcincho.service_msg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private UUID id;

    private String content;
    private MessageTypeInfo type;

    private UUID senderId;
    private String senderUsername;

    private UUID receiverId;
    private String receiverUsername;

    private UserConnection userConnection;

    private MessageDeliveryStatusEnum messageDeliveryStatusEnum;

    private List<MessageDeliveryStatusUpdate> messageDeliveryStatusUpdates;

}
