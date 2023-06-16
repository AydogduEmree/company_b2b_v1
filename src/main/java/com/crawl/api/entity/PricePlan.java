package com.crawl.api.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.crawl.api.pojo.BaseEntity;

import lombok.Data;

@SuppressWarnings("all")
@Entity
@Table(name = "price_plans")
@Data
public class PricePlan extends BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "S_PRICE_PLAN_ID", sequenceName = "S_PRICE_PLAN_ID", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PRICE_PLAN_ID")
	@Column(name = "PRICE_PLAN_ID")
	private BigInteger pricePlanId;
	
	@Column(name = "PLAN_NAME", length = 100)
	private String planName;
	@Column(name = "DESCRIPTION", length = 250)
	private String description;
	@Column(name = "CURRENCY_CODE", length = 10)
	private String currencyCode;
	
	
	//Default Constructor
	public PricePlan(){
		
	}
	public PricePlan( String planName, String description, String currencyCode) {
		this.planName = planName;
		this.description = description;
		this.currencyCode = currencyCode;
	}

	public PricePlan(BigInteger pricePlanId, String planName, String description, String currencyCode) {
		this.pricePlanId = pricePlanId;
		this.planName = planName;
		this.description = description;
		this.currencyCode = currencyCode;
	}
	
	public BigInteger getPlanId() {
		return this.pricePlanId;
	}
	public void setPlanId(BigInteger pricePlanId) {
		this.pricePlanId = pricePlanId;
	}
	
	public String getPlanName() {
		return this.planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCurrencyCode() {
		return this.currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
}
