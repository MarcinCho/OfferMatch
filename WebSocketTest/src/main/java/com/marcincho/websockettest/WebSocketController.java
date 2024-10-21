package com.marcincho.websockettest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public SocketMessage greeting(Message message) throws Exception {
        return new SocketMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!", "system", message.getText());
    }
}
