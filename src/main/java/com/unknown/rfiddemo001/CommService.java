package com.unknown.rfiddemo001;

import com.unknown.rfiddemo001.rfidPOJO.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CommService {
    private static SimpMessagingTemplate template;

    public static void setTemplate(SimpMessagingTemplate tmplt) {
        template = tmplt;
    }

    public static void send(Message message) {
        template.convertAndSend("/chatroom/public", message);
    }
}
