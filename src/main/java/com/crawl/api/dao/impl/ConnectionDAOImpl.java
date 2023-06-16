package com.crawl.api.dao.impl;

import java.sql.Blob;
import java.sql.Clob;

import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.crawl.api.dao.AbstractConnDAO;
import com.crawl.api.dao.ConnectionDAO;
@SuppressWarnings("all")
public class ConnectionDAOImpl extends AbstractConnDAO implements ConnectionDAO{
    
	
	/** The data source. */
	private DataSource dataSource;
	
	@Override
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@//localhost:1521/ORCL");
		dataSource.setUsername("system");
		dataSource.setPassword("1234");/*orcl*/
		return dataSource;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		if (!(dataSource instanceof TransactionAwareDataSourceProxy)) {
			this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
		} else {
			this.dataSource = dataSource;
		}
		
	}

	@Override
	public Session getSession() {
		return getSessionFactory().openSession();//getSessionFactory().getCurrentSession();
	}

	@Override
	public void commit() {
		try {
			getSession().flush();
			getSession().clear();
			getSession().createSQLQuery("commit").executeUpdate();
		} catch (RuntimeException re) {
			System.out.println("ConnectionDAOImpl - commit failure!");
			throw re;
		}
		
	}

	@Override
	public Blob createBlob(byte[] blobByte) {
		try {
			Blob blob = Hibernate.getLobCreator(getSession()).createBlob(blobByte);
			if(blob != null){
				return blob;
			}
			return null;
		} catch (Exception e) {
			System.out.println(" ConnectionDAOImpl " +  e.getMessage() );
			throw new RuntimeException(e);
		}
	}

	@Override
	public Clob createClob(String clobString) {
		try {
			Clob clob = Hibernate.getLobCreator(getSession()).createClob(clobString);
			if (clob != null) {
				return clob;
			}
			return null;
		} catch (Exception e) {
			System.out.println(" ConnectionDAOImpl " +  e.getMessage() );
			throw new RuntimeException(e);
		}
	}

}
