package com.example.demo.controllers;
import com.example.demo.model.ExecuteResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SfmcController {
    @GetMapping(value = "/")
    public String save2() {
        System.out.println("This is the SFMC save endpoint");
        return "Saved";
    }
    @PostMapping(value = "/execute")
    public String execute() {
        System.out.println("This is the SFMC execute endpoint");
        ExecuteResponse executeResponse = new ExecuteResponse();
        executeResponse.setFoundSignupDate("2023-10-01T00:00:00Z");
        return executeResponse.getFoundSignupDate();
    }
    @PostMapping(value = "/save")
    public String save() {
        System.out.println("This is the SFMC save endpoint");
        return "Saved";
    }
    @PostMapping(value = "/publish")
    public String publish() {
        System.out.println("This is the SFMC publish endpoint");
        return "Published";
    }
}