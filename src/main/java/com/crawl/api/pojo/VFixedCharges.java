package com.crawl.api.pojo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("all")
@Entity
@Immutable
@Table( name = "V_FIXED_CHARGES")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VFixedCharges  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
		@Id
	 	private Long fcSeqId;
	    private String pricePlanId;// BigInteger
	    private String PlanName; 
		private String productId;// BigInteger
		private String productName;
		private String productDescription;
		private String productHierarchyType;
		private Integer billFreq;
		private String billFreqAck;
		private Date effectDate; 
		private Double priceCost;
		private String currencyKod;
		private String currencyAck;
		private String refund;
}
