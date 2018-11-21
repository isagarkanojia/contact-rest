package com.contact.api.payload;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base error class to handle any kind of thrown error during execution.
 * 
 * @author Chad Sherwood
 *
 */
public class ErrorDto {

	private String status;
	private String error;
	private String message;
	private Date timeStamp;
	private String trace;
	
	/**
	 * Default Constructor
	 */
	public ErrorDto() {}

	/**
	 * Use this constructor when you know what happened and want to use a custom error
	 */
	public ErrorDto(String status, String error) {
		super();
		this.status = status;
		this.error = error;
		this.message =error;
		this.timeStamp = new Date();
	}
	
	/**
	 * Use this constructor when you know what happened and want to use a custom error
	 */
	public ErrorDto(String status, String error,String message) {
		super();
		this.status = status;
		this.error = error;
		this.message =message;
		this.timeStamp = new Date();
	}


	/**
	 * Returns the error text.
	 * 
	 * @return String
	 */
	public String getError() {
		return error;
	}

	/**
	 * Updates the error text.
	 * 
	 * @param error String
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Returns the error message.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Updates the error message
	 * 
	 * @param message String
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Returns the error status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Updates the error status.
	 * 
	 * @param status String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the error time stamp.
	 * 
	 * @return String
	 */
	public String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		return sdf.format(this.timeStamp);
	}

	/**
	 * Updates the error time stamp.
	 * 
	 * @param timeStamp String
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Returns the error stack trace.
	 * 
	 * @return String
	 */
	public String getTrace() {
		return trace;
	}

	/**
	 * Updates the error trace.
	 * 
	 * @param trace String
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
}
