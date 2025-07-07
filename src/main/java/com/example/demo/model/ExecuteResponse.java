package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ExecuteResponse {

    private String reqId;
    private String cid;
    private ArrayList<Product> products;
    private Test test;
}
