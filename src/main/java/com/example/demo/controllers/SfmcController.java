package com.example.demo.controllers;
import com.example.demo.model.ExecuteResponse;
import com.example.demo.model.ExecuteResponse1;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute")
    public ResponseEntity<Object> execute(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());
        ExecuteResponse executeResponse = new ExecuteResponse();
        ExecuteResponse1 executeResponse1 = new ExecuteResponse1();
        if (requestBody.has("inArguments") && requestBody.get("inArguments").isArray()) {
            JsonNode inArguments = requestBody.get("inArguments").get(0); // Get the first element
            if (inArguments.has("message")) {
                String message = inArguments.get("message").asText();
                if (message.equals("unknownMessage")) {
                    executeResponse1.setAlternateSignupDate("2025-10-01T00:00:00Z");
                    System.out.println("ExecuteResponse1: " + executeResponse1.getAlternateSignupDate());
                    return ResponseEntity.ok(executeResponse1);
                } else{
                    executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
                }

            } else {
                executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
            }
        } else {
            executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
        }
        System.out.println("ExecuteResponse: " + executeResponse.getFoundSignupDate());
        return ResponseEntity.ok(executeResponse);
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