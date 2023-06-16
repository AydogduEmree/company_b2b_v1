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
public class AddPricePlanSP extends StoredProcedure{
	
	/** The Constant subProgramName. */
    public static final String subProgramName = 
        "PKG_CATALOG_CRUD.P_ADD_PRICE_PLAN";
    
    public AddPricePlanSP(DataSource ds) {
        super(ds, subProgramName);

        declareParameter(new SqlParameter("P_PLAN_NAME", Types.VARCHAR));
        declareParameter(new SqlParameter("P_DESCRIPTION", Types.VARCHAR));
        declareParameter(new SqlParameter("P_CURRENCY_CODE", Types.VARCHAR));	
        declareParameter(new SqlParameter("P_DML_TYPE", Types.VARCHAR));	
        declareParameter(new SqlInOutParameter("P_PRICE_PLAN_ID", Types.NUMERIC));	
        compile();
    }

   
    public Map execute(String P_PLAN_NAME, 
					   String P_DESCRIPTION, 
					   String P_CURRENCY_CODE,
					   String P_DML_TYPE,
					   BigInteger P_PRICE_PLAN_ID) throws Exception {
        
    	Map in = new HashMap();

        in.put("P_PLAN_NAME", P_PLAN_NAME);
        in.put("P_DESCRIPTION", P_DESCRIPTION);
        in.put("P_CURRENCY_CODE", P_CURRENCY_CODE);
        in.put("P_DML_TYPE", P_DML_TYPE);
        in.put("P_PRICE_PLAN_ID", P_PRICE_PLAN_ID);
        return super.execute(in);
    }
}
