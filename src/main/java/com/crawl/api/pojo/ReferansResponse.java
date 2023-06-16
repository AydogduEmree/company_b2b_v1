package com.crawl.api.pojo;

public class ReferansResponse {
	private String kod;
    private String ack; 
    private String ack_language_1;
    
    public ReferansResponse() {
    	
    }
    
	public String getKod() {
		return kod;
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
	public String getAck_language_1() {
		return ack_language_1;
	}
	public void setAck_language_1(String ack_language_1) {
		this.ack_language_1 = ack_language_1;
	}

	@Override
	public String toString() {
		return "ReferansResponse [kod=" + kod + ", ack=" + ack + ", ack_language_1=" + ack_language_1 + "]";
	}
    
	
    
}
