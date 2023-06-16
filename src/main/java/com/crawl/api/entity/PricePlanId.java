package com.crawl.api.entity;

import java.io.Serializable;

//The NAme of class must be ReferenceID of Referans entity :) ahehehhahh
public class PricePlanId implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tableName;

    private String kod;
    
    public PricePlanId( ) { 
	}
	public PricePlanId(String tableName, String kod) {
		this.tableName = tableName;
		this.kod = kod;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
