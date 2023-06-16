package com.crawl.api.service.impl;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crawl.api.dao.impl.CatalogDAOImpl;
import com.crawl.api.entity.PricePlan;
import com.crawl.api.entity.User;
import com.crawl.api.model.ReportTypes;
import com.crawl.api.pojo.FixedChargeRequest;
import com.crawl.api.pojo.PricePlanRequest;
import com.crawl.api.pojo.PricePlanRequest2;
import com.crawl.api.pojo.ProductRequest;
import com.crawl.api.pojo.ReferansResponse;
import com.crawl.api.pojo.VFixedCharges;
import com.crawl.api.repository.UserRepository;
import com.crawl.api.rest.util.HttpExecuter;
import com.crawl.api.service.CatalogService;
import com.crawl.api.service.message.ServiceErrorCode;
import com.crawl.api.service.message.ServiceExecutionResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@SuppressWarnings("all")
@Service
@Data
@Builder
@AllArgsConstructor
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private CatalogDAOImpl catalogDAOImpl;
	@Autowired
	private UserRepository userRepository;
	
	/*Jasper Beans- Properties*/
	@Value("${my.jasper.username}")
	private String myJasperUsername;
	@Value("${my.jasper.password}")
	private String myJasperPassword;
	@Value("${my.jasper.url}")
	private String myJasperUrl;
	@Value("${my.jasper.port.enable}")
	private String myJasperPortEnable;
	@Value("${my.jasper.ssl}")
	private String myJasperSsl;
	/*@Resource(name="myJasperUsername")
	private String myJasperUsername;
	@Resource(name="myJasperPassword")
	private String myJasperPassword;
	@Resource(name="myJasperUrl")
	private String myJasperUrl;
	@Resource(name="myJasperPortEnable")
	private String myJasperPortEnable;
	@Resource(name="myJasperSsl")
	private String myJasperSsl;*/
	
	
	public CatalogServiceImpl () {
		
	}
	@Override
	public ServiceExecutionResult addPricePlan(PricePlanRequest pricePlanRequest) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode("");
	   	
		PricePlan pricePlan = new PricePlan(); 
		
		Map execute = catalogDAOImpl.addPricePlan(pricePlanRequest.getPlanName(), 
												  pricePlanRequest.getDescription(),
												  pricePlanRequest.getCurrency_code(),
												  "INSERT",
												  null);
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Plan")) {
		   		result.setErrorCode(ServiceErrorCode.PRICE_PLAN_EXISTS);	
		   		result.setMessage("Price is already exists!");
		   	}else {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	}
		 }
		/*else if (execute != null && execute.get("P_PRICE_PLAN_ID") != null) {
			 --Success
		 }*/
		
		
		return result;
	}
	
	@Override
	public ServiceExecutionResult updPricePlan(PricePlanRequest pricePlanRequest) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode("");
	   	
		PricePlan pricePlan = new PricePlan(); 
		
		Map execute = catalogDAOImpl.addPricePlan(pricePlanRequest.getPlanName(), 
												  pricePlanRequest.getDescription(),
												  pricePlanRequest.getCurrency_code(),
												  "UPDATE",
												  pricePlanRequest.getPrice_plan_id());
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Unkown")) {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	} 
		 } 
		
		return result;
	}
	
	@Override
	public PricePlanRequest getPlanWithId(BigInteger pricePlanId) {
		PricePlanRequest pricePlanRequest = new PricePlanRequest();
		pricePlanRequest= catalogDAOImpl.getPlanWithId(pricePlanId);
		
		return pricePlanRequest;
	}
	@Override
	public List<ReferansResponse> getCurrencies() {
		return catalogDAOImpl.getCurrencies();
	}
	@Override
	public List<PricePlanRequest> getAllPricePlans() {
		return catalogDAOImpl.getPricePlans();
	}
	
	@Override
	public List<PricePlanRequest2> getAllPricePlans2() {
		return catalogDAOImpl.getPricePlans2();
	}
	/*--------- Products -----------------------*/
	@Override
	public List<ProductRequest> getAllProducts() {
		return catalogDAOImpl.getAllProducts();
	}
	@Override
	public ProductRequest getProductById(String productId) {
		return catalogDAOImpl.getProductById(productId);
	}
	@Override
	public ServiceExecutionResult productUpd(ProductRequest productRequest) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode(""); 
		
		Map execute = catalogDAOImpl.ProductInsUpd(productRequest.getProductName(), 
									productRequest.getDescription(),
									productRequest.getProductCost(), 
									productRequest.getDefaultBillFreq(),
									productRequest.getProductHiearchType(), 
									productRequest.getProductCategoryCode(),
									productRequest.getChargeLevel(), 
									productRequest.getSubproducts(),
									productRequest.getImageLink(),
									productRequest.getDisabled(),
									"UPDATE" ,
									productRequest.getProductId());
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Unkown")) {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	} 
		 } 
		
		return result;
	}
	@Override
	public ServiceExecutionResult productAdd(ProductRequest productRequest) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode(""); 
		System.out.println("Burada -1 - Catalog Service Impl - productAdd ");
		Map execute = catalogDAOImpl.ProductInsUpd(productRequest.getProductName(), 
									productRequest.getDescription(),
									productRequest.getProductCost(), 
									productRequest.getDefaultBillFreq(),
									productRequest.getProductHiearchType(), 
									productRequest.getProductCategoryCode(),
									productRequest.getChargeLevel(), 
									productRequest.getSubproducts(),
									productRequest.getImageLink(),
									productRequest.getDisabled(),
									"INSERT" ,
									null);
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Unkown")) {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	} 
		 } 
		result.setSuccessCode(execute.get("P_PRODUCT_ID").toString());  
		return result;
	}
	@Override
	public List<ReferansResponse> getBillFreqs() {
		return catalogDAOImpl.getBillFreqs();
	}
	@Override
	public List<ReferansResponse> getProdCategories() {
		return catalogDAOImpl.getProdCategories();
	}
	/*--------- Bundles   -----------------------*/
	@Override
	public List<ProductRequest> getAllBundles() {
		return catalogDAOImpl.getAllBundles();
	}
	@Override
	public List<VFixedCharges> getSubProducts(String productId) {
		return catalogDAOImpl.getSubProducts(productId);
	}
	@Override
	public List<VFixedCharges> getUnAssignedSubProducts(String productId) {
		return catalogDAOImpl.getUnAssignedUniqSubProducts(productId);
	}
	@Override
	public ServiceExecutionResult fixedChargeSubProductsAdd(String fcSubProductsList) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode(""); 
		
		Map execute = catalogDAOImpl.FCSubproductsAdd(fcSubProductsList); 
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Unkown")) {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	} 
		 } 
		
		return result;
	}
	/*--------- Fixed Charges -----------------------*/
	@Override
	public List<FixedChargeRequest> getAllFixedCharges(String productId) {
		return catalogDAOImpl.getAllFixedCharges(productId);
	}
	@Override
	public List<VFixedCharges> getAllFixedCharges2(String productId) {
		return catalogDAOImpl.getAllFixedCharges2(productId);
	}
	@Override
	public ServiceExecutionResult fixedChargeAdd(String fcRequestList) {
		ServiceExecutionResult result = new ServiceExecutionResult();
		result.setExecutionSuccessful(true);
	   	result.setErrorCode("");
	   	result.setMessage("");
	   	result.setSuccessCode(""); 
		
		Map execute = catalogDAOImpl.FixedChargeInsUpd(fcRequestList); 
		if (execute.get("errorMessage") != null) {
			result.setExecutionSuccessful(false);
		   	if(execute.get("errorMessage").toString().contains("Unkown")) {
		   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
		   		result.setMessage(ServiceErrorCode.UNKNOWN);
		   	} 
		 } 
		
		return result;
	}
	@Override
	public ResponseEntity<byte[]> viewPDFCustomer(String userName) {				
		Optional<User> user = userRepository.findByUsername(userName);
		String userId = user.get().getUserId().toString();
		System.out.println(userId);
		Map<String, Object> documentDetails = new HashMap<String, Object>();
		documentDetails.put("pUserId", userId);
		documentDetails.put("outputFormat", ReportTypes.PDF);
		
		Map<String, Object> resultMap  = new HashMap<String, Object>();
		resultMap = executeRestReport(documentDetails);
		
		if(resultMap == null)
			return null;
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("attachmentType", "application/pdf");
		finalMap.put("filename", "Customer_Info_"+userId); 
		try {
			Blob b1 = null;
			b1= new SerialBlob((byte[])resultMap.get("body"));
			finalMap.put("blobObject",b1);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//finalMap.put("blobObject", catalogDAOImpl.createBlob((byte[])resultMap.get("body"))); //"Transform BLOB OBJECT");
		
		return transformToBlob(finalMap);
	}
	@Override
	public ResponseEntity<byte[]> transformToBlob(Map<String, Object> finalMap) {
		String attachmentType =  (String) finalMap.get("attachmentType");
		byte[] data=null;
		Integer contentLength = null;
		
		if( attachmentType.equals("application/pdf")) {
			Blob blobFile=null;
			if(finalMap.get("blobObject") !=null) {
			 blobFile = (Blob) finalMap.get("blobObject");
			}
			try {
				contentLength = (int) blobFile.length();
				data = blobFile.getBytes(1, contentLength);
			} catch (SQLException e) {
				String error = "Error in transformToBlob - CatalogServiceImpl";
			    System.out.println("Error in transformToBlob - CatalogServiceImpl");
				return new ResponseEntity<byte[]>(error.getBytes(), HttpStatus.OK);
			}
		}
		//https://stackoverflow.com/questions/45433094/displaying-a-pdf-served-from-jasper-reports-server
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control","no-cache, no-store, must-revalidate");
		headers.add("Pragma","no-cache");
		headers.add("Expires","0");
		if(finalMap.get("filename")!= null)
			headers.add(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=" +  finalMap.get("filename"));
			
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(contentLength)
				.contentType(MediaType.parseMediaType(attachmentType))
				.body(data);
	}
	@Override
	public Map<String, Object> executeRestReport(Map<String, Object> documentDetails) {

		String urlString =null; 
		//   argument: "http://localhost:8080/jasperserver/rest_v2/reports/reports/interactive/Directives_Report.pdf?id=1&method=rules"
		urlString = myJasperUrl + "/rest_v2/reports/reports/CustomReports/Customer_Info.pdf?pUserId="+documentDetails.get("pUserId");
		//urlString = myJasperUrl + "/rest_v2/resources/reports/CustomReports/Customer_Info?pUserId="+documentDetails.get("pUserId");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/pdf");
		headers.put("Accept", "application/pdf+json");//Content-Type changeable with version headers.put("Accept", "application/json");
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.putAll(documentDetails);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			returnMap = HttpExecuter.execute(HttpExecuter.GET, urlString,
					headers, requestBody,
					"Basic", myJasperUsername, myJasperPassword, 
					  8100,false,				 
					 null, false);
			if(!returnMap.isEmpty()) {
				if(returnMap.get("status") != null && returnMap.get("status").toString().contains("200")) {
					System.out.println("SUCCESS=  executeRestReport");
					return returnMap;			
				}else {
					return null;
				}				
			} else {
				return null;
			}
				
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
