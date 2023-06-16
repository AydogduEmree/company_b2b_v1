package com.crawl.api.testApi;


import java.util.List;

import com.crawl.api.dao.impl.CatalogDAOImpl;
import com.crawl.api.pojo.ReferansResponse;

 
public class Test01 {
   public static void main(String[] args) {
	    CatalogDAOImpl catalogDAOImpl = new CatalogDAOImpl();
	   List<ReferansResponse> referansList=  catalogDAOImpl.getCurrencies();
	   
	   for (ReferansResponse i : referansList) {
			System.out.println(i.toString());
       }
   }
}
