package com.crawl.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.crawl.api.pojo.BaseEntity;

import lombok.Data;

@SuppressWarnings("all")
@Entity
@Table(name = "referans")
@Data
@IdClass(PricePlanId.class) //ReferenId.class olacakti heheheh:) 
public class Referans extends BaseEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L; 
	
	@Id
	@Column(name = "TABLE_NAME", length = 100)
	private String tableName;
	
	@Id
	@Column(name = "KOD", length = 100)
	private String kod;
	
	@Column(name = "ACK", length = 255)
	private String ack;
	@Column(name = "ACK_LANGUAGE_1", length = 255)
	private String ack_language_1;
	@Column(name = "KRT1", length = 255)
	private String krt1;
	@Column(name = "KRT2", length = 255)
	private String krt2;
	@Column(name = "KRT3", length = 255)
	private String krt3;
	@Column(name = "KRT4", length = 255)
	private String krt4;
	@Column(name = "VALID_FLAG", length = 1)
	private String valid_flag;
	
	public Referans() {
		
	}
	public Referans( String kod, String ack) {
		this.kod = kod;
		this.ack = ack; 
	}
	public Referans( String kod, String ack, String ack_language_1) {
		
		this.kod = kod;
		this.ack = ack;
		this.ack_language_1 = ack_language_1;
	}
	
	public Referans(String tableName, String kod, String ack, String ack_language_1, String krt1, String krt2,
			String krt3, String krt4, String valid_flag) {
		super();
		this.tableName = tableName;
		this.kod = kod;
		this.ack = ack;
		this.ack_language_1 = ack_language_1;
		this.krt1 = krt1;
		this.krt2 = krt2;
		this.krt3 = krt3;
		this.krt4 = krt4;
		this.valid_flag = valid_flag;
	}
	
	public String getKod() {
		return this.kod;
	}
    public void setKod(String kod) {
		this.kod = kod;
	}
    public String getAck() {
		return ack;
	}
    public void setAck(String ack) {
		this.ack = ack;
	}
	
	
}
