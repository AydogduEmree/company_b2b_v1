package com.crawl.api.pojo;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePlanRequest2 {
    private String planName;
    private String description; 
    private String currency_code;
    private String price_plan_id; // BigInteger
    
    
}