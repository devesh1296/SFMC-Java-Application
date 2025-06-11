package com.example.demo.controllers;
import com.example.demo.model.ExecuteResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @PostMapping(value = "/execute")
    public ResponseEntity<ExecuteResponse> execute() {
        System.out.println("This is the SFMC execute endpoint");
        ExecuteResponse executeResponse = new ExecuteResponse();
        executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
        return ResponseEntity.ok(executeResponse);
    }
    @PostMapping(value = "/save")
    public ResponseEntity<String> save() {
        System.out.println("This is the SFMC save endpoint");
        return ResponseEntity.ok("Saved");
    }
    @PostMapping(value = "/publish")
    public ResponseEntity<String> publish() {
        System.out.println("This is the SFMC publish endpoint");
        return ResponseEntity.ok("Published");
    }
}