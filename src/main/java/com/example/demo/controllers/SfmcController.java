package com.example.demo.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute", consumes = {"application/json", "application/jwt"})
    public void execute(@RequestBody String requestBody) {
        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody);
    }
    @PostMapping(value = "/save", consumes = {"application/json", "application/jwt"})
    public ResponseEntity<String> save(@RequestBody String requestBody) {
        System.out.println("This is the SFMC save endpoint");
        System.out.println("Received request: " + requestBody);
        return ResponseEntity.ok("Saved");
    }
    @PostMapping(value = "/publish", consumes = {"application/json", "application/jwt"})
    public ResponseEntity<String> publish(@RequestBody String requestBody) {
        System.out.println("This is the SFMC publish endpoint");
        System.out.println("Received request: " + requestBody);
        return ResponseEntity.ok("Published");
    }
    @PostMapping(value = "/validate", consumes = {"application/json", "application/jwt"})
    public ResponseEntity<String> validate(@RequestBody String requestBody) {
        System.out.println("This is the SFMC validate endpoint");
        System.out.println("Received request: " + requestBody);
        return ResponseEntity.ok("Validated");
    }
}
