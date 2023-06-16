package com.crawl.api.entity;

import java.math.BigInteger;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.crawl.api.pojo.BaseEntity;

import lombok.Data;

@SuppressWarnings("all")
@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private BigInteger productId; //alter table products modify    PRODUCT_ID   NUMBER(20) ;
	
	@Column(name = "PRODUCT_NAME", length = 100)
	private String productName;
	
	@Column(name = "DESCRIPTION", length = 250)
	private String description;
	
	@Column(name = "PRODUCT_COST", columnDefinition="Decimal(10,2)")
	private Double productCost;
	
	@Column(name = "DEFAULT_BILL_FREQ")
	private Integer defaultBillFreq;  	//0,1,2,3,4,6,12
	
	@Column(name = "PRODUCT_HIERARCHY_TYPE", length = 30)
	private String productHiearchType;  	//just for Product; NONE, PARENT, CHILD
	
	@Column(name = "PRODUCT_CATEGORY_CODE", length = 50)
	private String productCategoryCode; 	//Hardware, Charge Element, Subscription, Bundle, Surcharge
	
	
	@Column(name = "CHARGE_LEVEL", length = 30)
	private String chargeLevel; 	//For just Bundle item ; Bundle Level, Item Level
	
	@Column(name = "SUBPRODUCTS", length = 2000)
	private String subproducts;
	
	@Lob
	@Column(name = "IMAGE_LINK")
	private Clob imageLink;
	
	@Column(name = "DISABLED", length = 1)
	private String disabled;
	
	
	//Default Constructor
	public Product() {
		
	}

	

	public Product(BigInteger productId, String productName, String description, Double productCost,
			Integer defaultBillFreq, String productHiearchType, String productCategoryCode, String chargeLevel,
			String subproducts, Clob imageLink, String disabled) {
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.productCost = productCost;
		this.defaultBillFreq = defaultBillFreq;
		this.productHiearchType = productHiearchType;
		this.productCategoryCode = productCategoryCode;
		this.chargeLevel = chargeLevel;
		this.subproducts = subproducts;
		this.imageLink = imageLink;
		this.disabled = disabled;
	}
	
	
	
}
