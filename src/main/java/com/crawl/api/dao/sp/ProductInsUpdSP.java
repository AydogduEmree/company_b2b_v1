package com.crawl.api.dao.sp;

import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

@SuppressWarnings("all")
public class ProductInsUpdSP extends StoredProcedure{
	
	/** The Constant subProgramName. */
    public static final String subProgramName = 
        "PKG_CATALOG_CRUD.P_PRODUCT_INS_UPD";
    
    public ProductInsUpdSP(DataSource ds) {
        super(ds, subProgramName);

        declareParameter(new SqlParameter("P_PRODUCT_NAME", Types.VARCHAR));
        declareParameter(new SqlParameter("P_DESCRIPTION", Types.VARCHAR));
        declareParameter(new SqlParameter("P_PRODUCT_COST", Types.NUMERIC));
        declareParameter(new SqlParameter("P_DEFAULT_BILL_FREQ", Types.NUMERIC));
        declareParameter(new SqlParameter("P_PRODUCT_HIERARCH_TYPE", Types.VARCHAR));
        declareParameter(new SqlParameter("P_PRODUCT_CATEGORY_CODE", Types.VARCHAR));
        declareParameter(new SqlParameter("P_CHARGE_LEVEL", Types.VARCHAR));
        declareParameter(new SqlParameter("P_SUBPRODUCTS", Types.VARCHAR));
        declareParameter(new SqlParameter("P_IMAGE_LINK", Types.CLOB));
        declareParameter(new SqlParameter("P_DISABLED", Types.VARCHAR));
        declareParameter(new SqlParameter("P_DML_TYPE", Types.VARCHAR));	
        declareParameter(new SqlInOutParameter("P_PRODUCT_ID", Types.VARCHAR));	
        compile();
    }

   
    public Map execute(String P_PRODUCT_NAME,
    		  String P_DESCRIPTION, 
    		  Double P_PRODUCT_COST,
    		  Integer P_DEFAULT_BILL_FREQ,
    		  String P_PRODUCT_HIERARCH_TYPE,
    		  String P_PRODUCT_CATEGORY_CODE,
    		  String P_CHARGE_LEVEL,
    		  String P_SUBPRODUCTS,
    		  Clob P_IMAGE_LINK,
    		  String P_DISABLED,
    		  String P_DML_TYPE,
    		  String P_PRODUCT_ID) throws Exception {
        
    	Map in = new HashMap();

        in.put("P_PRODUCT_NAME", P_PRODUCT_NAME);
        in.put("P_DESCRIPTION", P_DESCRIPTION);
        in.put("P_DEFAULT_BILL_FREQ", P_DEFAULT_BILL_FREQ);
        in.put("P_PRODUCT_HIERARCH_TYPE", P_PRODUCT_HIERARCH_TYPE);
        in.put("P_PRODUCT_CATEGORY_CODE", P_PRODUCT_CATEGORY_CODE);
        in.put("P_CHARGE_LEVEL", P_CHARGE_LEVEL);
        in.put("P_SUBPRODUCTS", P_SUBPRODUCTS);
        in.put("P_IMAGE_LINK", P_IMAGE_LINK);
        in.put("P_DISABLED", P_DISABLED);
        in.put("P_DEFAULT_BILL_FREQ", P_DEFAULT_BILL_FREQ);
        in.put("P_DEFAULT_BILL_FREQ", P_DEFAULT_BILL_FREQ);
        in.put("P_DML_TYPE", P_DML_TYPE);
        in.put("P_PRODUCT_ID", P_PRODUCT_ID);
        return super.execute(in);
    }
}
