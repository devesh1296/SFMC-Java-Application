package com.example.demo.controllers;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SfmcController {

    private final ObjectMapper mapper = new ObjectMapper();
    private static final int parallelism = 250;      // Downstream can handle 250 parallel calls
    private static final int perContactDelayMs = 600;

    @PostMapping(value = "/execute")
    public ResponseEntity<Object> execute(
            @RequestBody JsonNode requestBody) throws JsonProcessingException {

        System.out.println("This is the SFMC execute endpoint");
        System.out.println("Received request: " + requestBody.toString());

        int contactCount = requestBody.isArray() ? requestBody.size() : 1;
        long simulatedTotalDelayMs =
                Math.round((contactCount / (double) parallelism) * perContactDelayMs);

        System.out.printf("Simulating %d contacts @ %d ms each, concurrency=%d → total delay ~%d ms%n",
                contactCount, perContactDelayMs, parallelism, simulatedTotalDelayMs);

        try {
            Thread.sleep(simulatedTotalDelayMs);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<ExecuteResponse> responses = new ArrayList<>();

        // Build a mock ExecuteResponse for each contact
        for (int i = 0; i < contactCount; i++) {
            JsonNode contact = requestBody.get(i);

            ExecuteResponse executeResponse = new ExecuteResponse();
            ArrayList<Product> products = new ArrayList<>();

            // Mock product 1
            Product p1 = new Product();
            PriceInfo priceInfo = new PriceInfo();
            CurrentPrice cp = new CurrentPrice();
            UnitPrice up = new UnitPrice();
            PriceDisplayCodes pdc = new PriceDisplayCodes();
            cp.setPrice(77.38);
            cp.setPriceString("$77.38");
            up.setPrice(77.38);
            pdc.setPricePerUnitUom("EA");
            pdc.setRollback(false);
            priceInfo.setCurrentPrice(cp);
            priceInfo.setUnitPrice(up);
            priceInfo.setPriceDisplayCodes(pdc);
            p1.setId("4CLLH6GAR6VC");
            p1.setNumberOfReviews(6);
            p1.setAvailabilityStatus("IN_STOCK");
            p1.setCanonicalUrl("/ip/Charge-Dock-for-Oculus-Quest/212851666");
            p1.setPriceInfo(priceInfo);
            products.add(p1);

            // Mock product 2
            Product p2 = new Product();
            PriceInfo priceInfo2 = new PriceInfo();
            CurrentPrice cp2 = new CurrentPrice();
            UnitPrice up2 = new UnitPrice();
            PriceDisplayCodes pdc2 = new PriceDisplayCodes();
            cp2.setPrice(44.88);
            cp2.setPriceString("$44.88");
            up2.setPrice(44.88);
            pdc2.setPricePerUnitUom("EA");
            pdc2.setRollback(false);
            priceInfo2.setCurrentPrice(cp2);
            priceInfo2.setUnitPrice(up2);
            priceInfo2.setPriceDisplayCodes(pdc2);
            p2.setId("6J9OMEY9RG03");
            p2.setNumberOfReviews(192);
            p2.setAvailabilityStatus("IN_STOCK");
            p2.setCanonicalUrl("/ip/Arcade1UP-Riser/963795060");
            p2.setPriceInfo(priceInfo2);
            products.add(p2);

            executeResponse.setProducts(products);

            // Add contact info for traceability
            String cid = contact.has("cid") ? contact.get("cid").asText() : "unknown";
            executeResponse.setReqId("req-" + cid);
            executeResponse.setCid(cid);

            responses.add(executeResponse);
        }

        return ResponseEntity.ok(responses);
    }



    // Keeping save/publish as is
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
