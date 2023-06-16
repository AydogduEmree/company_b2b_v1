package com.crawl.api.rest.util;

import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.fasterxml.jackson.databind.ObjectMapper;


public final class HttpExecuter {
	public static final String GET = "get";

	public static final String POST = "post";

	public static final String PUT = "put";

	public static final String DELETE = "delete";
	
	public static Map<String, Object> execute(String method, String urlString, 
			 Map<String, String> headers, Map<String, Object> requestBody,
			String authorizationType, String username, String password, 
			Integer port, boolean useSSL,  String token, boolean getHeaders) throws Exception {
		
		authorizationType = (authorizationType != null) ? authorizationType : null;
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		if (useSSL) {
			SSLContext sslContext = SSLContext.getInstance("SSL");

			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, new SecureRandom());
			SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslConnectionFactory).build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			httpClient = HttpClients.custom().setConnectionManager(cm).build();

		}
		
		URL url = new URL(urlString);
		URI uri = null;
		if(port != null) {
			uri = new URI(url.getProtocol(), null, url.getHost(), port, url.getPath(), url.getQuery(), null);
			System.out.println("### HTTP EXECUTER=" + url.getProtocol() +  url.getHost() + port + url.getPath()+ url.getQuery());
		}else {
			uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
		}
		HttpRequestBase request = null;
		if(method.equals(GET)) {
			request = new HttpGet(uri);
		}else if(method.equals(POST)) {
			request = new HttpPost(uri);
		}else if(method.equals(PUT)) {
			request = new HttpPut(uri);
		}else if(method.equals(DELETE)) {
			request = new HttpDelete(uri);
		}
		
		if(headers != null) {
			for( String key : headers.keySet()) {
				request.setHeader(key, headers.get(key) );
			}
		}
		if(requestBody!=null && (method.equals(POST) || method.equals(PUT))) { // ?? 
			ObjectMapper mapper = new ObjectMapper();
			String body="";
			
			StringWriter stringWriter= new StringWriter();
			mapper.writeValue(stringWriter, requestBody);
			body=stringWriter.toString();
			
			if(request instanceof HttpPost ) {
				((HttpPost) request).setEntity(new StringEntity(body, "UTF-8"));
			}else {
				((HttpPut) request).setEntity(new StringEntity(body, "UTF-8"));
			}
		}
		if(authorizationType!=null && authorizationType.equals("Basic")) {
			String encodedCredential = Base64Coder.encodeString(username + ":" + password);
			request.setHeader("Authorization", "Basic "+encodedCredential); 
			//https://stackoverflow.com/questions/44178257/add-http-basic-authentication-to-servletrequest
		}
		
		System.out.println("$$$$$$ HTTP REQUEST" + uri.toString());
		HttpResponse httpResponse = httpClient.execute(request);
		Map<String, Object> result = new HashMap<>();
		result.put("headers", httpResponse.getAllHeaders());
		result.put("status", httpResponse.getStatusLine());
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null) {
			result.put("body", IOUtils.toByteArray(entity.getContent()) );
			System.out.println("€€€€€ HTTP RESPONSE" + result);
		}
		
		
		return result;
	}


}
