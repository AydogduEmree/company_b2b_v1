package com.crawl.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crawl.api.service.RestServiceStructure;
import com.crawl.api.service.message.ServiceExecutionResult;


@Service
public class RestServiceStructureImpl implements RestServiceStructure{

	
	public RestServiceStructureImpl () {
		
	}
	
	@Override
	public ResponseEntity<ServiceExecutionResult> getResponseResult(ServiceExecutionResult result) {
		return new ResponseEntity<ServiceExecutionResult>(result, HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<byte[]> getResponseResultForFileBlob(ServiceExecutionResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<byte[]> getResponseResultForFileURL(ServiceExecutionResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> X invoke(Object obj, String methodName, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
