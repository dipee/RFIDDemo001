package com.unknown.rfiddemo001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ExampleController {

    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    public final RFIDBase rfidBase;

    public ExampleController(RFIDBase rfidBase) {
        this.rfidBase = rfidBase;
    }


    @GetMapping("/test")
    public void example(){

        this.template.convertAndSend("/chatroom/public", "Hello from websocket");
    }


    @GetMapping("/reader")
    public void reader(){
        rfidBase.connectToReader("169.254.200.97",5084);
        rfidBase.startRead();
    }

    @GetMapping("/stop-reader")
    public void stopReader(){
        rfidBase.stopRead();
    }


}
