package com.example.demo.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute")
    public ResponseEntity<Object> execute(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());

        // Prepare the response list
        List<Map<String, String>> responseList = new ArrayList<>();

        // Add foundSignupDate object
        Map<String, String> foundSignupDate = new HashMap<>();
        foundSignupDate.put("foundSignupDate", "test1");
        responseList.add(foundSignupDate);

        // Add alternateSignupDate object
        Map<String, String> alternateSignupDate = new HashMap<>();
        alternateSignupDate.put("alternateSignupDate", "test2");
        responseList.add(alternateSignupDate);

        return ResponseEntity.ok(responseList);
    }
    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC save endpoint");
        System.out.println("Received request: " + requestBody.toString());
        return ResponseEntity.ok("Saved");
    }
    @PostMapping(value = "/publish")
    public ResponseEntity<String> publish(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC publish endpoint");
        System.out.println("Received request: " + requestBody.toString());
        return ResponseEntity.ok("Published");
    }
}