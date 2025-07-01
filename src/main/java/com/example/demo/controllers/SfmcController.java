package com.example.demo.controllers;
import com.example.demo.model.ExecuteResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute")
    public void execute(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());
        ExecuteResponse executeResponse = new ExecuteResponse();
        ArrayList<ExecuteResponse> outArgumentsArrayList = new ArrayList<>();
        if (requestBody.has("inArguments") && requestBody.get("inArguments").isArray()) {
            JsonNode inArguments = requestBody.get("inArguments").get(0); // Get the first element
            if (inArguments.has("message")) {
                String message = inArguments.get("message").asText();
                if (message.equals("unknownMessage")) {
                    executeResponse.setFoundSignupDate("test1");
                    executeResponse.setAlternateSignupDate("test2");
                } else{
                    executeResponse.setFoundSignupDate("test1");
                    executeResponse.setAlternateSignupDate("test2");
                }

            } else {
                executeResponse.setFoundSignupDate("test1");
                executeResponse.setAlternateSignupDate("test2");
            }
        } else {
            executeResponse.setFoundSignupDate("test1");
            executeResponse.setAlternateSignupDate("test2");
        }
        System.out.println("ExecuteResponse: " + executeResponse.getFoundSignupDate());
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