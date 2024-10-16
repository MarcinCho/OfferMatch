package com.marcincho.service_msg.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcincho.service_msg.models.ChatMessage;
import com.marcincho.service_msg.models.UnseenMessageCountResponse;
import com.marcincho.service_msg.models.UserConnection;
import com.marcincho.service_msg.service.ConvService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/conv")
public class ConvController {

    private final ConvService convService;

    public ConvController(ConvService convService) {
        this.convService = convService;
    }

    @GetMapping("/contacts")
    public List<UserConnection> getUserContacts() {
        return convService.getUserContacts();
    }

    @GetMapping("/unseenMessages")
    public List<UnseenMessageCountResponse> getUnseenMessageCount() {
        return convService.getUnseenMessageCount();
    }

    @GetMapping("/unseenMessages/{fromUserId}")
    public List<ChatMessage> getUnseenMessages(@PathVariable("fromUserId") UUID fromUserId) {
        return convService.getUnseenMessages(fromUserId);
    }

    @PutMapping("/setReadMessages")
    public List<ChatMessage> setReadMessages(@RequestBody List<ChatMessage> chatMessages) {
        return convService.setReadMessages(chatMessages);
    }
}
