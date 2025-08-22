package com.example.demo.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute", consumes = "application/jwt")
    public void execute(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());
    }
    @PostMapping(value = "/save", consumes = "application/jwt")
    public ResponseEntity<String> save(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC save endpoint");
        System.out.println("Received request: " + requestBody.toString());
        return ResponseEntity.ok("Saved");
    }
    @PostMapping(value = "/publish", consumes = "application/jwt")
    public ResponseEntity<String> publish(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC publish endpoint");
        System.out.println("Received request: " + requestBody.toString());
        return ResponseEntity.ok("Published");
    }
    @PostMapping(value = "/validate", consumes = "application/jwt")
    public ResponseEntity<String> validate(@RequestBody JsonNode requestBody) {
        System.out.println("This is the SFMC validate endpoint");
        System.out.println("Received request: " + requestBody.toString());
        return ResponseEntity.ok("Validated");
    }
}
