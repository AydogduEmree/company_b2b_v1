package com.crawl.api.dao.sp;

import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

@SuppressWarnings("all")
public class FCSubProductsInsSP extends StoredProcedure{
	
	/** The Constant subProgramName. */
    public static final String subProgramName = 
        "PKG_CATALOG_CRUD.P_FC_SUBPRODUCT_INS";
    
    public FCSubProductsInsSP(DataSource ds) {
        super(ds, subProgramName);
        declareParameter(new SqlInOutParameter("P_FC_SUBPRODUCTS_LIST", Types.VARCHAR));
        compile(); 
    }

   
    public Map execute(String fcRequestList) throws Exception {
        
    	Map in = new HashMap();

        in.put("P_FC_SUBPRODUCTS_LIST", fcRequestList); 
        return super.execute(in);
    }
}
