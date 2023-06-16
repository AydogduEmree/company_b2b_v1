package com.crawl.api.entity;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.crawl.api.pojo.BaseEntity;

import lombok.Data;

@SuppressWarnings("all")
@Entity
@Table(name = "fixed_charges")
@Data
public class FixedCharge extends BaseEntity implements java.io.Serializable {
  
	private static final long serialVersionUID = 1L;
    
	@Id
	@SequenceGenerator(name = "s_fc_id", sequenceName = "s_fc_id", initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_fc_id")
	@Column(name = "FC_SEQ_ID")
	private Long fcSeqId;
	
	@Column(name = "PRODUCT_ID")
	private BigInteger productId;
	
	@Column(name = "PRICE_PLAN_ID")
	private BigInteger pricePlanId;
	
	@Column(name = "EFFECT_DATE")
	@DateTimeFormat(pattern="DD/MM/YYYY")
	private Date effectDate; 
	
	@Column(name = "REFUND", length = 1)
	private String refund;
	
	@Column(name = "BILL_FREQ")
	private Integer billFreq;

	@Column(name = "PRICE_COST")
	private Double priceCost;

	// Constructors

	/** default constructor **/
	public FixedCharge() {
	}

	public FixedCharge(Long fcSeqId, BigInteger productId, BigInteger pricePlanId, 
				Date effectDate, String refund,Integer billFreq, Double priceCost) {
		this.fcSeqId = fcSeqId;
		this.productId = productId;
		this.pricePlanId = pricePlanId;
		this.effectDate = effectDate;
		this.refund = refund;
		this.billFreq = billFreq;
		this.priceCost = priceCost;
	}
	
}
