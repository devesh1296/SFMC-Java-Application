package com.example.demo.controllers;
import com.example.demo.model.*;
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
        Response response = new Response();
        ExecuteResponse executeResponse = new ExecuteResponse();
        ArrayList<Product> products = new ArrayList<>();
        Test test = new Test();
        TestA testA = new TestA();
        TestB testB = new TestB();
        TestC testC = new TestC();

        Product product = new Product();
        PriceInfo priceInfo = new PriceInfo();
        CurrentPrice currentPrice = new CurrentPrice();
        UnitPrice unitPrice = new UnitPrice();
        PriceDisplayCodes priceDisplayCodes = new PriceDisplayCodes();
        currentPrice.setPrice(77.38);
        currentPrice.setPriceString("$77.38");
        unitPrice.setPrice(77.38);
        priceDisplayCodes.setPricePerUnitUom("EA");
        priceDisplayCodes.setRollback(false);
        priceInfo.setCurrentPrice(currentPrice);
        priceInfo.setUnitPrice(unitPrice);
        priceInfo.setPriceDisplayCodes(priceDisplayCodes);
        product.setId("4CLLH6GAR6VC");
        product.setNumberOfReviews(6);
        product.setAvailabilityStatus("IN_STOCK");
        product.setCanonicalUrl("/ip/Charge-Dock-for-Oculus-Quest/212851666?athcpid=212851666&athpgid=email-comms&athcgid=null&athznid=ci&athieid=v0&athstid=CS020&athguid=a205c688-024b-4d71-aacd-4dda2424bc36&athancid=null&athena=true");
        product.setPriceInfo(priceInfo);
        products.add(product);
        executeResponse.setReqId("a205c688-024b-4d71-aacd-4dda2424bc36");
        executeResponse.setCid("a205c688-024b-4d71-aacd-4dda2424bc36");
        testA.setId("testA123");
        testA.setNum(7);
        testB.setId("testB123");
        testB.setFlag(true);
        testC.setId("testC123");
        testC.setValue(1.11);
        test.setTestA(testA);
        test.setTestB(testB);
        test.setTestC(testC);
        executeResponse.setTest(test);


        Product product1 = new Product();
        PriceInfo priceInfo1 = new PriceInfo();
        CurrentPrice currentPrice1 = new CurrentPrice();
        UnitPrice unitPrice1 = new UnitPrice();
        PriceDisplayCodes priceDisplayCodes1 = new PriceDisplayCodes();
        currentPrice1.setPrice(44.88);
        currentPrice1.setPriceString("$44.88");
        unitPrice1.setPrice(44.88);
        priceDisplayCodes1.setPricePerUnitUom("EA");
        priceDisplayCodes1.setRollback(false);
        priceInfo1.setCurrentPrice(currentPrice1);
        priceInfo1.setUnitPrice(unitPrice1);
        priceInfo1.setPriceDisplayCodes(priceDisplayCodes1);
        product1.setId("6J9OMEY9RG03");
        product1.setNumberOfReviews(192);
        product1.setAvailabilityStatus("IN_STOCK");
        product1.setCanonicalUrl("/ip/Arcade1UP-Riser/963795060?athcpid=963795060&athpgid=email-comms&athcgid=null&athznid=ci&athieid=v0&athstid=CS020&athguid=a205c688-024b-4d71-aacd-4dda2424bc36&athancid=null&athena=true");
        product1.setPriceInfo(priceInfo1);
        products.add(product1);

        executeResponse.setProducts(products);

        response.setExecuteResponse(executeResponse);

        ApiResponse apiResponse = new ApiResponse();
        Result result = new Result();
        result.setId("a205c688-024b-4d71-aacd-4dda2424bc36");
        result.setStatus("SUCCESS");
        apiResponse.setResult(result);

        return ResponseEntity.ok(apiResponse);
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