package com.crawl.api.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crawl.api.dao.CatalogDAO;
import com.crawl.api.dao.sp.AddPricePlanSP;
import com.crawl.api.dao.sp.FCSubProductsInsSP;
import com.crawl.api.dao.sp.FixedChargeInsUpdSP;
import com.crawl.api.dao.sp.ProductInsUpdSP;
import com.crawl.api.dao.util.DAOHelper;
import com.crawl.api.entity.FixedCharge;
import com.crawl.api.entity.PricePlan;
import com.crawl.api.entity.Product;
import com.crawl.api.entity.Referans;
import com.crawl.api.pojo.FixedChargeRequest;
import com.crawl.api.pojo.PricePlanRequest;
import com.crawl.api.pojo.PricePlanRequest2;
import com.crawl.api.pojo.ProductRequest;
import com.crawl.api.pojo.ReferansResponse;
import com.crawl.api.pojo.VFixedCharges;

@SuppressWarnings("all")
@Service
public class CatalogDAOImpl extends ConnectionDAOImpl implements CatalogDAO {
	
	public CatalogDAOImpl() {
		
	}
	
	@Override
	public Map addPricePlan(String P_PLAN_NAME, String P_DESCRIPTION, String P_CURRENCY_CODE, String P_DML_TYPE,
			BigInteger P_PRICE_PLAN_ID) {
		Map resultMap = new HashMap();
		try {
			AddPricePlanSP addPricePlanSp = new AddPricePlanSP(getDataSource());
			System.out.println(P_PLAN_NAME+ "-"+ P_DESCRIPTION+ "-"+
			                 P_CURRENCY_CODE+ "-"+ P_DML_TYPE+ "-"+ P_PRICE_PLAN_ID);
			
			resultMap = addPricePlanSp.execute(P_PLAN_NAME, P_DESCRIPTION, P_CURRENCY_CODE, P_DML_TYPE, P_PRICE_PLAN_ID);
			
			if(resultMap == null)
				return null;
			
		} catch (Exception e) {
			if(e!= null) {
				String messageStr= e.getMessage();
				String errorStr;
			    		
				errorStr=messageStr.substring(  
								messageStr.indexOf("*")+1, 
								messageStr.indexOf("*",messageStr.indexOf("*")+1)
                        				 );
			    //System.out.println(errorStr + " - CatalogDAOImpl has error !");
				resultMap.put("errorMessage", errorStr);
			}
			return resultMap;
			
		}
		
		return resultMap;
	}

	@Override
	public PricePlanRequest getPlanWithId(BigInteger pricePlanId) {
		try {
			PricePlanRequest  data=new PricePlanRequest();
			
			PricePlan  dataTemp;
			String queryString = "from PricePlan where price_plan_id = :pricePlanId";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString).setParameter("pricePlanId", pricePlanId);
			if (query.uniqueResult() == null) {
				return null;
			}
			dataTemp = (PricePlan) query.uniqueResult();
			data.setPrice_plan_id(dataTemp.getPlanId());
			data.setPlanName(dataTemp.getPlanName());
			data.setDescription(dataTemp.getDescription());
			data.setCurrency_code(dataTemp.getCurrencyCode());
			return data;
		} catch (Exception re) {
			System.out.println("CatalogDAO with ID getPlanWithId error" +  re);
			throw new RuntimeException(re);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReferansResponse> getCurrencies() {
		try {
			List<ReferansResponse>  data=new ArrayList<ReferansResponse>();
			
			List<Referans>  dataTemp;
			String queryString = "from Referans where TABLE_NAME = 'CURRENCIES' AND VALID_FLAG IS NULL";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<Referans>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				ReferansResponse referansResponse = new ReferansResponse();
				referansResponse.setKod(dataTemp.get(i).getKod());
				referansResponse.setAck(dataTemp.get(i).getAck());
				referansResponse.setAck_language_1(dataTemp.get(i).getAck_language_1());
				data.add(referansResponse);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data;//(List<ReferansResponse>) data.get(0);
		} catch (Exception re) {
			System.out.println("CatalogDAO with ID getCurrencies error" +  re);
			throw new RuntimeException(re);
		}
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<PricePlanRequest> getPricePlans() {
		try {
			List<PricePlanRequest>  data=new ArrayList<PricePlanRequest>();
			
			List<PricePlan>  dataTemp;
			String queryString = "from PricePlan";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<PricePlan>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				PricePlanRequest pricePlanRequest = new PricePlanRequest();
				pricePlanRequest.setPrice_plan_id(dataTemp.get(i).getPlanId());
				pricePlanRequest.setPlanName(dataTemp.get(i).getPlanName());
				pricePlanRequest.setDescription(dataTemp.get(i).getDescription());
				pricePlanRequest.setCurrency_code(dataTemp.get(i).getCurrencyCode());
				
				data.add(pricePlanRequest);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data;//(List<ReferansResponse>) data.get(0);
		} catch (Exception re) {
			System.out.println("CatalogDAO for getPricePlans error" +  re);
			throw new RuntimeException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PricePlanRequest2> getPricePlans2() {
		try {
			List<PricePlanRequest2>  data=new ArrayList<PricePlanRequest2>();
			
			List<PricePlan>  dataTemp;
			String queryString = "from PricePlan";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<PricePlan>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				PricePlanRequest2 pricePlanRequest = new PricePlanRequest2();
				pricePlanRequest.setPrice_plan_id(dataTemp.get(i).getPlanId().toString());
				pricePlanRequest.setPlanName(dataTemp.get(i).getPlanName());
				pricePlanRequest.setDescription(dataTemp.get(i).getDescription());
				pricePlanRequest.setCurrency_code(dataTemp.get(i).getCurrencyCode());
				
				data.add(pricePlanRequest);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data;//(List<ReferansResponse>) data.get(0);
		} catch (Exception re) {
			System.out.println("CatalogDAO for getPricePlans2 error" +  re);
			throw new RuntimeException(re);
		}
	}
	/*-------------- PRODUCTS ---------------------  */
	@Override
	public ProductRequest getProductById(String productId) {
		try {
			
			
			Product  dataTemp;
			String queryString = "from Product where PRODUCT_ID = :productId AND DISABLED IS NULL";//Product is entity name, referring products table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(Product) query.uniqueResult();
			if (dataTemp == null) {
				return null;
			}
					
			ProductRequest productRequest = new ProductRequest();
			productRequest.setProductName(dataTemp.getProductName());
			productRequest.setDescription(dataTemp.getDescription());
			productRequest.setProductCost(dataTemp.getProductCost());
			productRequest.setDefaultBillFreq(dataTemp.getDefaultBillFreq());
			productRequest.setProductHiearchType(dataTemp.getProductHiearchType());
			productRequest.setProductCategoryCode(dataTemp.getProductCategoryCode());
			productRequest.setChargeLevel(dataTemp.getChargeLevel());
			productRequest.setProductName(dataTemp.getProductName());
			productRequest.setSubproducts(dataTemp.getSubproducts());//DAOHelper daoHelper = new DAOHelper();Clob clob = dataTemp.get(i).getImageLink(); System.out.println(daoHelper.readClob(dataTemp.get(i).getImageLink()));
			
			if(dataTemp.getImageLink() == null) {
				productRequest.setImageLink("");
			}else {
				productRequest.setImageLink(DAOHelper.readClob(dataTemp.getImageLink()));
			}
			
			
			
			productRequest.setProductId(dataTemp.getProductId().toString());
				
		 	 
			return productRequest; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getProductById error" +  re);
			throw new RuntimeException(re);
		}
		
		
	}
	
	@Override
	public List<ProductRequest> getAllProducts() {
		try {
			List<ProductRequest>  data=new ArrayList<ProductRequest>();
			
			List<Product>  dataTemp;
			String queryString = "from Product where DISABLED IS NULL and PRODUCT_CATEGORY_CODE <> 'Bundle'";//Product is entity name, referring products table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<Product>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				ProductRequest productRequest = new ProductRequest();
				productRequest.setProductName(dataTemp.get(i).getProductName());
				productRequest.setDescription(dataTemp.get(i).getDescription());
				productRequest.setProductCost(dataTemp.get(i).getProductCost());
				productRequest.setDefaultBillFreq(dataTemp.get(i).getDefaultBillFreq());
				productRequest.setProductHiearchType(dataTemp.get(i).getProductHiearchType());
				productRequest.setProductCategoryCode(dataTemp.get(i).getProductCategoryCode());
				productRequest.setChargeLevel(dataTemp.get(i).getChargeLevel());
				productRequest.setProductName(dataTemp.get(i).getProductName());
				productRequest.setSubproducts(dataTemp.get(i).getSubproducts());//DAOHelper daoHelper = new DAOHelper();				Clob clob = dataTemp.get(i).getImageLink(); System.out.println(daoHelper.readClob(dataTemp.get(i).getImageLink()));
				 
				if(   dataTemp.get(i).getImageLink() != null   ) {
					productRequest.setImageLink(DAOHelper.readClob(dataTemp.get(i).getImageLink()));
				}				
				productRequest.setProductId(dataTemp.get(i).getProductId().toString());
				data.add(productRequest);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getAllProducts error" +  re);
			throw new RuntimeException(re);
		}
	}

	@Override
	public Map ProductInsUpd(String P_PRODUCT_NAME, String P_DESCRIPTION, Double P_PRODUCT_COST,
			Integer P_DEFAULT_BILL_FREQ, String P_PRODUCT_HIERARCH_TYPE, String P_PRODUCT_CATEGORY_CODE,
			String P_CHARGE_LEVEL, String P_SUBPRODUCTS, String P_IMAGE_LINK, String P_DISABLED, String P_DML_TYPE,
			String P_PRODUCT_ID) {
		Map resultMap = new HashMap();
		try {
			ProductInsUpdSP productInsUpdSp = new ProductInsUpdSP(getDataSource());
			
			resultMap = productInsUpdSp.execute(P_PRODUCT_NAME, P_DESCRIPTION, P_PRODUCT_COST,P_DEFAULT_BILL_FREQ,
					 				  P_PRODUCT_HIERARCH_TYPE,  P_PRODUCT_CATEGORY_CODE, P_CHARGE_LEVEL,
					 				  P_SUBPRODUCTS, P_IMAGE_LINK, P_DISABLED, P_DML_TYPE, P_PRODUCT_ID);
			
			if(resultMap == null)
				return null;
			
		} catch (Exception e) {
			if(e!= null) {
				String messageStr= e.getMessage();
				String errorStr;
			    		
				errorStr=messageStr.substring(  
								messageStr.indexOf("*")+1, 
								messageStr.indexOf("*",messageStr.indexOf("*")+1)
                        				 );
				resultMap.put("errorMessage", errorStr);
			}
			resultMap.put("P_PRODUCT_ID", resultMap.get("P_PRODUCT_ID"));
			return resultMap;
			
		}
		
		return resultMap;
	}
	
	@Override
	public List<ReferansResponse> getBillFreqs() {
		try {
			List<ReferansResponse>  data=new ArrayList<ReferansResponse>();
			
			List<Referans>  dataTemp;
			String queryString = "from Referans where TABLE_NAME = 'BILL_FREQ' AND VALID_FLAG IS NULL";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<Referans>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				ReferansResponse referansResponse = new ReferansResponse();
				referansResponse.setKod(dataTemp.get(i).getKod());
				referansResponse.setAck(dataTemp.get(i).getAck());
				referansResponse.setAck_language_1(dataTemp.get(i).getAck_language_1());
				data.add(referansResponse);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data;//(List<ReferansResponse>) data.get(0);
		} catch (Exception re) {
			System.out.println("CatalogDAO with ID getBillFreqs error" +  re);
			throw new RuntimeException(re);
		}
	}

	@Override
	public List<ReferansResponse> getProdCategories() {
		try {
			List<ReferansResponse>  data=new ArrayList<ReferansResponse>();
			
			List<Referans>  dataTemp;
			String queryString = "from Referans where TABLE_NAME = 'PRODUCT_CATEGORY_CODE' AND VALID_FLAG IS NULL";//PricePlan is entity name, referring Price_Plans table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<Referans>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				ReferansResponse referansResponse = new ReferansResponse();
				referansResponse.setKod(dataTemp.get(i).getKod());
				referansResponse.setAck(dataTemp.get(i).getAck());
				referansResponse.setAck_language_1(dataTemp.get(i).getAck_language_1());
				data.add(referansResponse);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data;//(List<ReferansResponse>) data.get(0);
		} catch (Exception re) {
			System.out.println("CatalogDAO with ID getProdCategories error" +  re);
			throw new RuntimeException(re);
		}
	}
	/*---------------    BUNDLES     ---------------------*/
	@Override
	public List<ProductRequest> getAllBundles() {
		try {
			List<ProductRequest>  data=new ArrayList<ProductRequest>();
			
			List<Product>  dataTemp;
			String queryString = "from Product where DISABLED IS NULL and PRODUCT_CATEGORY_CODE = 'Bundle'";//Product is entity name, referring products table in DB
			Query query = getSession().createQuery(queryString);
			dataTemp = 	(List<Product>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				ProductRequest productRequest = new ProductRequest();
				productRequest.setProductName(dataTemp.get(i).getProductName());
				productRequest.setDescription(dataTemp.get(i).getDescription());
				productRequest.setProductCost(dataTemp.get(i).getProductCost());
				productRequest.setDefaultBillFreq(dataTemp.get(i).getDefaultBillFreq());
				productRequest.setProductHiearchType(dataTemp.get(i).getProductHiearchType());
				productRequest.setProductCategoryCode(dataTemp.get(i).getProductCategoryCode());
				productRequest.setChargeLevel(dataTemp.get(i).getChargeLevel());
				productRequest.setProductName(dataTemp.get(i).getProductName());
				productRequest.setSubproducts(dataTemp.get(i).getSubproducts());//DAOHelper daoHelper = new DAOHelper();				Clob clob = dataTemp.get(i).getImageLink(); System.out.println(daoHelper.readClob(dataTemp.get(i).getImageLink()));
				 
				if(   dataTemp.get(i).getImageLink() != null   ) {
					productRequest.setImageLink(DAOHelper.readClob(dataTemp.get(i).getImageLink()));
				}				
				productRequest.setProductId(dataTemp.get(i).getProductId().toString());
				data.add(productRequest);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getAllBundles error" +  re);
			throw new RuntimeException(re);
		}
	}
    
	@Override
	public List<VFixedCharges> getSubProducts(String productId) {
		try {
			 
			List<VFixedCharges>  dataTemp;
			String queryString = "from VFixedCharges where price_plan_id = :productId and product_hierarchy_type ='CHILD'";//FixedCharge is immutable entity name, referring V_FIXED_CHARGES table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<VFixedCharges>) query.list();
			 
			if (dataTemp == null || dataTemp.isEmpty())
				return null;
			return dataTemp; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getSubProducts error" +  re);
			throw new RuntimeException(re);
		}
	}
	/*
	@Override
	public List<VFixedCharges> getUniqSubProducts(String productId) {
		try {
			 
			List<VFixedCharges>  dataTemp;
			String queryString = "select distinct FC_SEQ_ID, PRICE_PLAN_ID, PLAN_NAME, PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_HIERARCHY_TYPE, BILL_FREQ, BILL_FREQ_ACK, EFFECT_DATE, PRICE_COST, CURRENCY_KOD, CURRENCY_ACK, REFUND from VFixedCharges where price_plan_id = :productId and product_hierarchy_type ='CHILD'";//FixedCharge is immutable entity name, referring V_FIXED_CHARGES table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<VFixedCharges>) query.list();
			 
			if (dataTemp == null || dataTemp.isEmpty())
				return null;
			return dataTemp; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getUniqSubProducts error" +  re);
			return null;
			//throw new RuntimeException(re);
		}
	}
	*/
	@Override
	public List<VFixedCharges> getUnAssignedSubProducts(String productId) {
		try {
			 
			List<VFixedCharges>  dataTemp;
			String queryString  = "from VFixedCharges vfc WHERE vfc.productHierarchyType ='CHILD' and (:productId =-1 OR (vfc.productId NOT IN(SELECT vf.productId FROM VFixedCharges vf WHERE vf.pricePlanId = :productId))";
					//FixedCharge is immutable entity name, referring V_FIXED_CHARGES table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<VFixedCharges>) query.list();
			 
			if (dataTemp == null || dataTemp.isEmpty())
				return null;
			return dataTemp; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getUnAssignedSubProducts error" +  re);
			throw new RuntimeException(re);
		}
	}
	@Override
	public List<VFixedCharges> getUnAssignedUniqSubProducts(String productId) {
		try {
			 
			List<VFixedCharges>  dataTemp; 
			String queryString  ="select distinct productId, productName, productDescription ";
			queryString = queryString+ "from VFixedCharges vfc WHERE vfc.productHierarchyType ='CHILD' and vfc.productId NOT IN(SELECT vf.productId FROM VFixedCharges vf WHERE vf.pricePlanId = :productId)";
					//FixedCharge is immutable entity name, referring V_FIXED_CHARGES table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<VFixedCharges>) query.list();
			/*  VFixedCharges vfc =
								new VFixedCharges(null,
										null,
										null, 
										dataTemp.get(i).getProductId(),
										dataTemp.get(i).getProductName(),
										dataTemp.get(i).getProductDescription(),
										null,
										null,
										null,
										null,
										0.00,
										null,
										null, 
										null);
				   dataTemp2.add(vfc);*/
			 
			
			if (dataTemp == null || dataTemp.isEmpty())
				return null;
			return dataTemp; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getUnAssignedUniqSubProducts error" +  re);
			return null;
			//throw new RuntimeException(re);
		}
	}
	
	@Override
	public Map FCSubproductsAdd(String fcSubProductsList) {
		Map resultMap = new HashMap();
		try {
			FCSubProductsInsSP fcSubProductSP = new FCSubProductsInsSP(getDataSource());
			System.out.println("Burada -2 - Catalog DAO Impl - FCSubproductsAdd ");
			System.out.println(fcSubProductsList);
			resultMap = fcSubProductSP.execute(fcSubProductsList); 
			
			if(resultMap == null)
				return null;
			
		} catch (Exception e) {
			if(e!= null) {
				String messageStr= e.getMessage();
				String errorStr;
			    		
				errorStr=messageStr.substring(  
								messageStr.indexOf("*")+1, 
								messageStr.indexOf("*",messageStr.indexOf("*")+1)
                        				 );
				resultMap.put("errorMessage", errorStr);
			}
			return resultMap;
			
		}
		
		return resultMap;
	}
	/*---------------    FIXED CHARGES   ---------------------*/
	@Override
	public List<FixedChargeRequest> getAllFixedCharges(String productId) {
		try {
			List<FixedChargeRequest>  data=new ArrayList<FixedChargeRequest>();
			
			List<FixedCharge>  dataTemp;
			String queryString = "from FixedCharge where product_id = :productId";//FixedCharge is entity name, referring fixed_charges table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<FixedCharge>) query.list();
			for(int i=0; i< dataTemp.size() ; i++) {
				FixedChargeRequest fixedChargeRequest = new FixedChargeRequest();
				fixedChargeRequest.setBillFreq(dataTemp.get(i).getBillFreq());
				fixedChargeRequest.setEffectDate(dataTemp.get(i).getEffectDate());
				fixedChargeRequest.setPriceCost(dataTemp.get(i).getPriceCost()); 
				fixedChargeRequest.setPricePlanId(dataTemp.get(i).getPricePlanId().toString());
				fixedChargeRequest.setProductId(dataTemp.get(i).getProductId().toString());
				fixedChargeRequest.setRefund(dataTemp.get(i).getRefund());
				fixedChargeRequest.setFcSeqId(dataTemp.get(i).getFcSeqId());
				
				data.add(fixedChargeRequest);
			}	
			if (data == null || data.isEmpty())
				return null;
			return data; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getAllFixedCharges error" +  re);
			throw new RuntimeException(re);
		}
	}
	@Override
	public List<VFixedCharges> getAllFixedCharges2(String productId) {
		try {
			 
			List<VFixedCharges>  dataTemp;
			String queryString = "from VFixedCharges where product_id = :productId";//FixedCharge is immutable entity name, referring V_FIXED_CHARGES table in DB
			Query query = getSession().createQuery(queryString).setParameter("productId", productId);
			dataTemp = 	(List<VFixedCharges>) query.list();
			 
			if (dataTemp == null || dataTemp.isEmpty())
				return null;
			return dataTemp; 
		} catch (Exception re) {
			System.out.println("CatalogDAO for getAllFixedCharges2 error" +  re);
			throw new RuntimeException(re);
		}
	}
	@Override
	public Map FixedChargeInsUpd(String fcRequestList) {
		Map resultMap = new HashMap();
		try {
			FixedChargeInsUpdSP fcInsUpdSp = new FixedChargeInsUpdSP(getDataSource());
			System.out.println("Burada -2 - Catalog DAO Impl - FixedChargeInsUpd ");
			System.out.println(fcRequestList);
			resultMap = fcInsUpdSp.execute(fcRequestList); 
			
			if(resultMap == null)
				return null;
			
		} catch (Exception e) {
			if(e!= null) {
				String messageStr= e.getMessage();
				String errorStr;
			    		
				errorStr=messageStr.substring(  
								messageStr.indexOf("*")+1, 
								messageStr.indexOf("*",messageStr.indexOf("*")+1)
                        				 );
				resultMap.put("errorMessage", errorStr);
			}
			return resultMap;
			
		}
		
		return resultMap;
	}

	 

	

	
	

	

	

}
