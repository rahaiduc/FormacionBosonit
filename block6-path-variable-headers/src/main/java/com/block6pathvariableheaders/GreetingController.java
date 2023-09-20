package com.block6pathvariableheaders;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping ("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping("/greeting2")
    public Greeting postJSON(@RequestBody Greeting greeting){
        return greeting;
    }

    @GetMapping("/user/{id}")
    public String getId(@PathVariable String id){
        return id;
    }


}
