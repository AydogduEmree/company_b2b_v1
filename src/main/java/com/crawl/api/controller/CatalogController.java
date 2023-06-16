package com.crawl.api.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crawl.api.pojo.FixedChargeRequest;
import com.crawl.api.pojo.PricePlanRequest;
import com.crawl.api.pojo.PricePlanRequest2;
import com.crawl.api.pojo.ProductRequest;
import com.crawl.api.pojo.ReferansResponse;
import com.crawl.api.pojo.VFixedChargeDT;
import com.crawl.api.pojo.VFixedCharges;
import com.crawl.api.pojo.VFixedSubProductsDT;
import com.crawl.api.service.impl.CatalogServiceImpl;
import com.crawl.api.service.message.ServiceExecutionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;

@RestController
@RequestMapping("/api/catalog")
@AllArgsConstructor
public class CatalogController {
	
	
	@Autowired
	private CatalogServiceImpl catalogService;
	//Commit Purpose
	/**---------------  PRICE PLANS -------------------------**/
	@RequestMapping(value = "/addPricePlan", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> addPricePlan(@RequestBody PricePlanRequest pricePlanRequest) {
        ServiceExecutionResult result = new ServiceExecutionResult();
        result = catalogService.addPricePlan(pricePlanRequest);
        if(result.getErrorCode()!= "") { //   !result.isExecutionSuccessful()
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	@RequestMapping(value = "/updPricePlan", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> updPricePlan(@RequestBody PricePlanRequest pricePlanRequest) {
        ServiceExecutionResult result = new ServiceExecutionResult();
        result = catalogService.updPricePlan(pricePlanRequest);
        if(result.getErrorCode()!= "") { //   !result.isExecutionSuccessful()
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	@RequestMapping(value = "/pricePlan/{pricePlanId}", method = RequestMethod.GET)
	 public  PricePlanRequest getPricePlan(@PathVariable BigInteger pricePlanId) {
		
	      return catalogService.getPlanWithId(pricePlanId);
	 }
	@RequestMapping(value = "/curriencies", method = RequestMethod.GET)
	 public  List<ReferansResponse> getCurriencies() {
		
	      return catalogService.getCurrencies();
	 }
	@RequestMapping(value = "/plans", method = RequestMethod.GET)
	 public  List<PricePlanRequest> getAllPricePlans() {
		
	      return catalogService.getAllPricePlans();
	 }
	@RequestMapping(value = "/plans2", method = RequestMethod.GET)
	 public  List<PricePlanRequest2> getAllPricePlans2() {
		
	      return catalogService.getAllPricePlans2();
	 }
	/**--------------          ------------------------**/
	
	/**--------------  PRODUCT ------------------------**/
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	 public  List<ProductRequest> getAllProducts() {
		return catalogService.getAllProducts();
	 }
	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	 public  ProductRequest getProductById(@PathVariable String productId) {
		return catalogService.getProductById(productId);
	 }
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> addProduct(@RequestBody VFixedChargeDT vFixedCargeDT) {
        /*Create product*/
		ServiceExecutionResult result = new ServiceExecutionResult();
        result = catalogService.productAdd(vFixedCargeDT.getProductRequest());
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        		//System.out.println("PRODUCT CODE = " + result.getSuccessCode());
        		//System.out.println("Eklenen Fixed  = " +  vFixedCargeDT.getFixedChargeRequest());
        
        /*Create Fixed Charge*/ // By Getting productID of a new product  
        ObjectMapper mapper = new ObjectMapper();
        List<VFixedCharges> participantJsonList = new ArrayList<>();
		try {
			participantJsonList = mapper.readValue(vFixedCargeDT.getFixedChargeRequest(), new TypeReference<List<VFixedCharges>>(){});
			List<VFixedCharges> list2 = new ArrayList<>();
	        for(VFixedCharges p:  participantJsonList ){
	             p.setProductId(result.getSuccessCode());
	             p.setEffectDate(null);
	        } 
		} catch (JsonMappingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		} catch (JsonProcessingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		}
		if ( participantJsonList == null || participantJsonList.isEmpty() ) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
		}
		String jsonStr = JSONArray.toJSONString(participantJsonList);
		System.out.println(jsonStr);
		result= catalogService.fixedChargeAdd(jsonStr);
		 
        
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        
        
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	@RequestMapping(value = "/updProduct", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> updProduct(@RequestBody VFixedChargeDT vFixedCargeDT) {
        ServiceExecutionResult result = new ServiceExecutionResult();
        /* PRODUCT UPDATE*/
        result = catalogService.productUpd(vFixedCargeDT.getProductRequest());
        if(result.getErrorCode()!= "") { //   !result.isExecutionSuccessful()
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        /*FIXED CHARGE UPDATE*/
        ObjectMapper mapper = new ObjectMapper();
        List<VFixedCharges> participantJsonList = new ArrayList<>();
        try {
			participantJsonList = mapper.readValue(vFixedCargeDT.getFixedChargeRequest(), new TypeReference<List<VFixedCharges>>(){});
			List<VFixedCharges> list2 = new ArrayList<>();
	        for(VFixedCharges p:  participantJsonList ){
	             p.setEffectDate(null);
	        } 
		} catch (JsonMappingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		} catch (JsonProcessingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		}
		if ( participantJsonList == null || participantJsonList.isEmpty() ) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
		}
		String jsonStr = JSONArray.toJSONString(participantJsonList);
		System.out.println(jsonStr);
		result= catalogService.fixedChargeAdd(jsonStr);
		 
        
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/refBillFreqs", method = RequestMethod.GET)
	 public  List<ReferansResponse> getBillFreqs() {
		
	      return catalogService.getBillFreqs();
	 }
	@RequestMapping(value = "/refProdCategories", method = RequestMethod.GET)
	 public  List<ReferansResponse> getProdCategories() {
		
	      return catalogService.getProdCategories();
	 }
	/**--------------         ------------------------**/
	
	/**--------------  BUNDLES ------------------------**/
	@RequestMapping(value = "/bundles", method = RequestMethod.GET)
	 public  List<ProductRequest> getAllBundles() {
		return catalogService.getAllBundles();
	 }
	@RequestMapping(value = "/bundle/{productId}", method = RequestMethod.GET)
	 public  ProductRequest getBundleById(@PathVariable String productId) {
		return catalogService.getProductById(productId);
	 }
	@RequestMapping(value = "/getSubProducts/{productId}", method = RequestMethod.GET)
	 public  List<VFixedCharges> getSubProducts(@PathVariable String productId) {
		return catalogService.getSubProducts(productId);
	 }
	@RequestMapping(value = "/getUnAssignedSubProducts/{productId}", method = RequestMethod.GET)
	 public  List<VFixedCharges> getUnAssignedSubProducts(@PathVariable String productId) {
		return catalogService.getUnAssignedSubProducts(productId);
	 }
	@RequestMapping(value = "/addBundle", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> addProduct(@RequestBody VFixedSubProductsDT vFixedSubProductsDT) {
        /*Create product*/
		String newProductId;
		ServiceExecutionResult result = new ServiceExecutionResult();
        result = catalogService.productAdd(vFixedSubProductsDT.getProductRequest());
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        		//System.out.println("PRODUCT CODE = " + result.getSuccessCode());
        		//System.out.println("Eklenen Fixed  = " +  vFixedCargeDT.getFixedChargeRequest());
        
        /*Create Fixed Charge*/ // By Getting productID of a new product  
        newProductId = result.getSuccessCode();
        ObjectMapper mapper = new ObjectMapper();
        List<VFixedCharges> participantJsonList = new ArrayList<>();
		try {
			participantJsonList = mapper.readValue(vFixedSubProductsDT.getFixedChargeRequest(), new TypeReference<List<VFixedCharges>>(){});
			List<VFixedCharges> list2 = new ArrayList<>();
	        for(VFixedCharges p:  participantJsonList ){
	             p.setProductId(newProductId);
	             p.setEffectDate(null);
	        } 
		} catch (JsonMappingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		} catch (JsonProcessingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		}
		if ( participantJsonList == null || participantJsonList.isEmpty() ) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
		}
		String jsonStr = JSONArray.toJSONString(participantJsonList);
		System.out.println(jsonStr);
		result= catalogService.fixedChargeAdd(jsonStr);
		 
        
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        /* Add Bundle Sub Products */
        participantJsonList.clear();
        participantJsonList =  vFixedSubProductsDT.getSubProductList();
        if(participantJsonList.size()<1) {
        	return new ResponseEntity<>(result,HttpStatus.OK);
        }
		for(VFixedCharges p:  participantJsonList ){
             p.setPricePlanId(newProductId);
        } 
		jsonStr=""; 
		try {
			jsonStr = mapper.writeValueAsString(participantJsonList);
		} catch (JsonMappingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		} catch (JsonProcessingException e) {
			return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
			//e.printStackTrace();
		}
		System.out.println(jsonStr);
		result= catalogService.fixedChargeSubProductsAdd(jsonStr);
		
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	
	/**--------------         ------------------------**/
	
    /**--------------  FIXED CHARGE ------------------------**/
	@RequestMapping(value = "/fixedCharges/{productId}", method = RequestMethod.GET)
	 public  List<FixedChargeRequest> getAllFixedCharges(@PathVariable String productId) {
		return catalogService.getAllFixedCharges(productId);
	 }
	@RequestMapping(value = "/fixedCharges2/{productId}", method = RequestMethod.GET)
	 public  List<VFixedCharges> getAllFixedCharges2(@PathVariable String productId) {
		return catalogService.getAllFixedCharges2(productId);
	 }
	@RequestMapping(value = "/addFixedCharges", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> addFixedCharges(@RequestBody String listFixedCharge) {
        ServiceExecutionResult result = new ServiceExecutionResult();
        result = catalogService.fixedChargeAdd(listFixedCharge);
        if(result.getErrorCode()!= "") { 
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	/**--------------         ------------------------**/
	@RequestMapping(value = "/viewPDFCustomer/{userName}", method = RequestMethod.GET)
	 public  ResponseEntity<byte[]> viewPDFCustomer(@PathVariable String userName) {
		return catalogService.viewPDFCustomer(userName);
	 } 
}
