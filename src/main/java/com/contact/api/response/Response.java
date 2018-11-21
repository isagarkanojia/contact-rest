package com.contact.api.response;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success","total","data"})
@JsonInclude(Include.NON_NULL)
public class Response<R> extends BaseResponse {

	private Collection<R> data;
	private Integer total;
	
	@JsonInclude(Include.NON_NULL)
	private String statusCode = "200";

	public Response() {
		data = new ArrayList<>();
	}
		
	public Integer getTotal() {
		return total;		
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	


	/**
	 * @return the data
	 */
	public Collection<R> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Collection<R> data) {
		this.data = data;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	
	
}