package com.block6pathvariableheaders;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @PutMapping("/post")
    public HashMap<String, String> putVar(@RequestParam Map<String,String> valores){
        return new HashMap<>(valores);
    }

    @GetMapping("/header")
    public HashMap<String,String> getHeader(@RequestHeader String h1, @RequestHeader String H2){
        HashMap<String,String> headers=new HashMap<>();
        headers.put("h1",h1);
        headers.put("H2",H2);
        return headers;
    }

    @PostMapping("/all")
    public ResponseData getData(@RequestBody(required = false) String b,
                                @RequestHeader Map<String, String> requestHeaders,
                                @RequestParam Map<String, String> requestParams){
        List<String> headers=new ArrayList<>();
        List<String> params=new ArrayList<>();
        String body= b==null || b.isBlank() ? "":b;
        requestHeaders.forEach((k,v)->headers.add(v));
        requestParams.forEach((k,v)->params.add(v));
        return new ResponseData(body,headers,params);
    }


}
