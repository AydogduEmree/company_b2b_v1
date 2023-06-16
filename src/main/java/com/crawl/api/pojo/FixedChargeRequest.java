package com.crawl.api.pojo;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixedChargeRequest {
    private Long fcSeqId;
    private String pricePlanId;// BigInteger
	private String productId;// BigInteger
	private Date effectDate; 
	private String refund;
	private Integer billFreq;
	private Double priceCost;

    
}