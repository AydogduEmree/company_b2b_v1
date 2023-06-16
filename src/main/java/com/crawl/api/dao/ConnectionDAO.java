package com.crawl.api.dao;

import java.sql.Blob;
import java.sql.Clob;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

@SuppressWarnings("all")
public interface ConnectionDAO {
	
	public DataSource getDataSource();
	
	public void setDataSource(DataSource dataSource);
	
	public Session getSession();
	 
	public SessionFactory getSessionFactory();
	 
	public void commit();
	
	public Blob createBlob(byte[] blobByte);
		 	 
	public Clob createClob(String clobString);
	
}
