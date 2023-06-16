package com.crawl.api.service;

import org.springframework.http.ResponseEntity;

import com.crawl.api.service.message.ServiceExecutionResult;

public interface RestServiceStructure {
	/**
	 * Gets the response result.
	 *
	 * @param result the result
	 * @return the response result
	 */
	ResponseEntity<ServiceExecutionResult> getResponseResult(ServiceExecutionResult result);

	/**
	 * Gets the response result for file.
	 *
	 * @param result the result
	 * @return the response result for file
	 */
	ResponseEntity<byte[]> getResponseResultForFileBlob(ServiceExecutionResult result);

	/**
	 * Gets the response result for file.
	 *
	 * @param result the result
	 * @return the response result for file
	 */
	ResponseEntity<byte[]> getResponseResultForFileURL(ServiceExecutionResult result);
	
	/**
	 * Invoke.
	 *
	 * @author eaydogdu
	 * @param <X> the generic type
	 * @param obj the obj
	 * @param methodName the method name
	 * @param params the params
	 * @return the x
	 */
	<X> X invoke(Object obj, String methodName, Object ... params);
}
