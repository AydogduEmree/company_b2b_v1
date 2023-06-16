package com.crawl.api.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VFixedSubProductsDT  implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	
	ProductRequest productRequest;
	String fixedChargeRequest;
	List<VFixedCharges> subProductList;
}
