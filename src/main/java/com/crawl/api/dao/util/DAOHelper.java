package com.crawl.api.dao.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

public class DAOHelper {
	
	public static java.sql.Date getSQLDate(Date date) {
		if(date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}
	public static String readClob(Clob clob) throws SQLException, IOException {
	    StringBuilder sb = new StringBuilder((int) clob.length());
	    Reader r = clob.getCharacterStream();
	    char[] cbuf = new char[2048];
	    int n;
	    while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
	        sb.append(cbuf, 0, n);
	    }
	    return sb.toString();
	}
}
