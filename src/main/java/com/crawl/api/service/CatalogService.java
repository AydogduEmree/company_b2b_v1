package com.crawl.api.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.crawl.api.pojo.FixedChargeRequest;
import com.crawl.api.pojo.PricePlanRequest;
import com.crawl.api.pojo.PricePlanRequest2;
import com.crawl.api.pojo.ProductRequest;
import com.crawl.api.pojo.ReferansResponse;
import com.crawl.api.pojo.VFixedCharges;
import com.crawl.api.service.message.ServiceExecutionResult;

public interface CatalogService {
	/**--------------  PRICE PLANS ------------------------**/
	public ServiceExecutionResult addPricePlan(PricePlanRequest pricePlanRequest);
	
	public ServiceExecutionResult updPricePlan(PricePlanRequest pricePlanRequest);
	
	public  PricePlanRequest getPlanWithId(BigInteger pricePlanId);
	
	public List<ReferansResponse> getCurrencies();
	
	public List<PricePlanRequest> getAllPricePlans();
	
	public List<PricePlanRequest2> getAllPricePlans2();
	/**--------------          ------------------------**/
	
	/**--------------  PRODUCT ------------------------**/
	public  List<ProductRequest> getAllProducts();
	public  ProductRequest getProductById(String productId);
	
	public ServiceExecutionResult productUpd(ProductRequest productRequest);
	
	public ServiceExecutionResult productAdd(ProductRequest productRequest );
	
	public List<ReferansResponse> getBillFreqs();
	
	public List<ReferansResponse> getProdCategories();
	/**--------------------------------------**/
	
	/**--------------  BUNDLE ------------------------**/
	public  List<ProductRequest> getAllBundles();
	public  List<VFixedCharges> getSubProducts(String productId);
	public  List<VFixedCharges> getUnAssignedSubProducts(String productId);
	public ServiceExecutionResult fixedChargeSubProductsAdd(String fcSubProductsList);
	/**--------------------------------------**/
	
	
	/**--------------  FIXED CHARGE ------------------------**/
	public  List<FixedChargeRequest> getAllFixedCharges(String productId);
	
	public  List<VFixedCharges> getAllFixedCharges2(String productId);
	
	public ServiceExecutionResult fixedChargeAdd(String fcRequestList);
	/**--------------------------------------**/
	
	public  ResponseEntity<byte[]> viewPDFCustomer(String userName);
	public  ResponseEntity<byte[]> transformToBlob(Map<String, Object> finalMap);
	public  Map<String, Object> executeRestReport(Map<String, Object> documentDetails);
	
}
