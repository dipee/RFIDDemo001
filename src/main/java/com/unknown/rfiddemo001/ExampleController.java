package com.unknown.rfiddemo001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ExampleController {
    public static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public final RFIDBase rfidBase;

    public ExampleController(RFIDBase rfidBase) {
        this.rfidBase = rfidBase;
    }


    @GetMapping
    public String example(){

        Map<Integer,String> students = new HashMap<>();

        students.put(1,"Harish");
        students.put(2,"Roshan");
        students.put(3,"ram");
        students.put(4,"ram");

        System.out.println(students.get(2));

        Set<Map.Entry<Integer,String>> values = students.entrySet();
        for (Map.Entry<Integer, String> e : values) {
            System.out.println(e.getKey()+"" + e.getValue());
        }

        return "Ram";
    }

    @GetMapping("/greeting")
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template,name));
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
