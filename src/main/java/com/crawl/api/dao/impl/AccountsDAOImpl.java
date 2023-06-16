package com.crawl.api.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crawl.api.dao.AccountsDAO;
import com.crawl.api.dao.sp.AddUserSP;
import com.crawl.api.entity.User;

@SuppressWarnings("all")
@Service
public class AccountsDAOImpl extends ConnectionDAOImpl implements AccountsDAO{
	
	public AccountsDAOImpl() {
	
	}
		
	@Override
	public Map addNewUser(String P_USERNAME,  String P_PASSWORD,String P_EMAIL, String P_DML_TYPE, BigInteger P_USER_ID) {

		Map resultMap = new HashMap();
		try {
			AddUserSP addUserSp = new AddUserSP(getDataSource());
			resultMap = addUserSp.execute(P_USERNAME, P_PASSWORD, P_EMAIL, P_DML_TYPE, P_USER_ID);
			
			if(resultMap == null)
				return null;
			
		} catch (Exception e) {
			if(e!= null) {
				String messageStr= e.getMessage();
				String errorStr;
				//System.out.println( "AccountsDAOImpl has error !" + e.getMessage());				
			    		
				errorStr=messageStr.substring(  
								messageStr.indexOf("*")+1, 
								messageStr.indexOf("*",messageStr.indexOf("*")+1)
                        				 );
			    System.out.println(errorStr + " - AccountsDAOImpl has error !");
				resultMap.put("errorMessage", errorStr);
			}
			return resultMap;
			
		}
		
		return resultMap;
	}


	@Override
	public User getUserWithId(BigInteger userId) {
		try {
			String queryString = "from User where user_id = :userId";//User is entity name, referring Users table in DB
			Query query = getSession().createQuery(queryString).setParameter("userId", userId);
			if (query.uniqueResult() == null) {
				return null;
			}
			return (User) query.uniqueResult();
		} catch (Exception re) {
			System.out.println("AccountsDAO getUserWithId error" +  re);
			throw new RuntimeException(re);
		}
		
	}

}
