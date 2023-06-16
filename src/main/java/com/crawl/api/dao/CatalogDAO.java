package com.crawl.api.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.crawl.api.pojo.FixedChargeRequest;
import com.crawl.api.pojo.PricePlanRequest;
import com.crawl.api.pojo.PricePlanRequest2;
import com.crawl.api.pojo.ProductRequest;
import com.crawl.api.pojo.ReferansResponse;
import com.crawl.api.pojo.VFixedCharges; 

public interface CatalogDAO extends ConnectionDAO{
	/**--------------  PRICE PLANS ------------------------**/
	public Map addPricePlan(String P_PLAN_NAME, 
			   String P_DESCRIPTION, 
			   String P_CURRENCY_CODE,
			   String P_DML_TYPE,
			   BigInteger P_PRICE_PLAN_ID
			   );
	public  PricePlanRequest getPlanWithId(BigInteger pricePlanId);
	
	public  List<ReferansResponse> getCurrencies();
	
	public  List<PricePlanRequest> getPricePlans();
	
	public  List<PricePlanRequest2> getPricePlans2();
	/**--------------------------------------**/
	
	/**--------------  PRODUCT ------------------------**/
	public  List<ProductRequest> getAllProducts();
	
	public  ProductRequest getProductById(String productId);
	
	public Map ProductInsUpd(String P_PRODUCT_NAME,
	  String P_DESCRIPTION, 
	  Double P_PRODUCT_COST,
	  Integer P_DEFAULT_BILL_FREQ,
	  String P_PRODUCT_HIERARCH_TYPE,
	  String P_PRODUCT_CATEGORY_CODE,
	  String P_CHARGE_LEVEL,
	  String P_SUBPRODUCTS,
	  String P_IMAGE_LINK,
	  String P_DISABLED,
	  String P_DML_TYPE,
	  String P_PRODUCT_ID		   
			   );
	
	public List<ReferansResponse> getBillFreqs();
	
	public List<ReferansResponse> getProdCategories();
	/**--------------------------------------**/
	/**--------------  BUNDLE ------------------------**/
	public  List<ProductRequest> getAllBundles();
	public  List<VFixedCharges> getSubProducts(String productId); 
	public  List<VFixedCharges> getUnAssignedSubProducts(String productId);
	public  List<VFixedCharges> getUnAssignedUniqSubProducts(String productId);
	public Map FCSubproductsAdd(String fcSubProductsList);
	/**--------------------------------------**/
	
	/**--------------  FIXED CHARGE ------------------------**/
	public  List<FixedChargeRequest> getAllFixedCharges(String productId);
	
	public  List<VFixedCharges> getAllFixedCharges2(String productId);
	
	public Map FixedChargeInsUpd(String fcRequestList);
	/**--------------------------------------**/
}
