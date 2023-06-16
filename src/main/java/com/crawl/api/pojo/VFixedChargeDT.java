package com.crawl.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VFixedChargeDT  implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
   
	
	ProductRequest productRequest;
	String fixedChargeRequest;
}
