package com.unknown.rfiddemo001;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CommService {
    private static SimpMessagingTemplate template;

    public static void setTemplate(SimpMessagingTemplate tmplt) {
        template = tmplt;
    }

    public static void send(RfidWSMessage message) {
        template.convertAndSend("/topic/user", message);
    }
}
