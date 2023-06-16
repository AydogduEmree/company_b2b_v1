package com.crawl.api.dao;

import java.math.BigInteger;
import java.util.Map;

import com.crawl.api.entity.User;

public interface AccountsDAO extends ConnectionDAO{
	
	
	public Map addNewUser(String P_USERNAME, 
			   String P_PASSWORD, 
			   String P_EMAIL,
			   String P_DML_TYPE,
			   BigInteger P_USER_ID
			   );
	public User getUserWithId(BigInteger userId);
	//public Map updateUser(User user); // UserDTO
}
