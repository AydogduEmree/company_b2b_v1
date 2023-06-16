package com.crawl.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String description; 
    private Double productCost;
    private Integer defaultBillFreq;
    private String productHiearchType;
    private String productCategoryCode;
    private String chargeLevel;
    private String subproducts;
    private String  imageLink;
    private String disabled;
    private String productId; // BigInteger
 
}