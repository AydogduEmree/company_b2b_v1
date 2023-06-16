package com.crawl.api.service.message;

import java.io.Serializable;

public class ServiceExecutionResult implements Serializable {

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The execution successful. */
	private boolean executionSuccessful = true;
	
	/** The success code. */
	private String successCode;
	
	/** The error code. */
	private String errorCode;
	
	/** The message. */
	private String message;

	public ServiceExecutionResult() {
	}

	public boolean isExecutionSuccessful() {
		return executionSuccessful;
	}

	public void setExecutionSuccessful(boolean executionSuccessful) {
		this.executionSuccessful = executionSuccessful;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
