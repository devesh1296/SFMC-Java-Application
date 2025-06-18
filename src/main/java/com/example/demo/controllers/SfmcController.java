package com.example.demo.controllers;
import com.example.demo.model.ExecuteResponse;
import com.example.demo.model.OutArguments;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute")
    public ResponseEntity<Object> execute(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());
        ExecuteResponse executeResponse = new ExecuteResponse();
        OutArguments outArguments = new OutArguments();
        ArrayList<ExecuteResponse> outArgumentsArrayList = new ArrayList<>();
        if (requestBody.has("inArguments") && requestBody.get("inArguments").isArray()) {
            JsonNode inArguments = requestBody.get("inArguments").get(0); // Get the first element
            if (inArguments.has("message")) {
                String message = inArguments.get("message").asText();
                if (message.equals("unknownMessage")) {
                    executeResponse.setFoundSignupDate("2024-10-01T00:00:00Z");
                    executeResponse.setAlternateSignupDate("2025-10-01T00:00:00Z");
                    outArgumentsArrayList.add(executeResponse);
                    outArguments.setOutArguments(outArgumentsArrayList);
                    return ResponseEntity.ok(outArguments);
                } else{
                    executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
                    executeResponse.setAlternateSignupDate("2025-10-01T00:00:00Z");
                }

            } else {
                executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
                executeResponse.setAlternateSignupDate("2025-10-01T00:00:00Z");
            }
        } else {
            executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
            executeResponse.setAlternateSignupDate("2025-10-01T00:00:00Z");
        }
        System.out.println("ExecuteResponse: " + executeResponse.getFoundSignupDate());
        outArgumentsArrayList.add(executeResponse);
        outArguments.setOutArguments(outArgumentsArrayList);
        return ResponseEntity.ok(outArguments);
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