package com.crawl.api.dao.sp;

import java.math.BigInteger;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

@SuppressWarnings("all")
public class AddUserSP extends StoredProcedure{
	
	/** The Constant subProgramName. */
    public static final String subProgramName = 
        "PKG_USERS_CRUD.P_USER_SIGN_UP";
    
    public AddUserSP(DataSource ds) {
        super(ds, subProgramName);

        declareParameter(new SqlParameter("P_USERNAME", Types.VARCHAR));
        declareParameter(new SqlParameter("P_PASSWORD", Types.VARCHAR));
        declareParameter(new SqlParameter("P_EMAIL", Types.VARCHAR));	
        declareParameter(new SqlParameter("P_DML_TYPE", Types.VARCHAR));	
        declareParameter(new SqlInOutParameter("P_USER_ID", Types.NUMERIC));	
        compile();
    }

   
    public Map execute(String P_USERNAME, 
    				   String P_PASSWORD, 
    				   String P_EMAIL,
    				   String P_DML_TYPE,
    				   BigInteger P_USER_ID) throws Exception {
        
    	Map in = new HashMap();

        in.put("P_USERNAME", P_USERNAME);
        in.put("P_PASSWORD", P_PASSWORD);
        in.put("P_EMAIL", P_EMAIL);
        in.put("P_DML_TYPE", P_DML_TYPE);
        in.put("P_USER_ID", P_USER_ID);
        return super.execute(in);
    }
}
