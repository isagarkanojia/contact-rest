package com.contact.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success","statusCode","total","data"})
public class SingleResponse<R> extends BaseResponse {
	@JsonInclude(Include.NON_NULL)
	private R data;
	
	@JsonInclude(Include.NON_NULL)
	private Integer total;

	@JsonInclude(Include.NON_NULL)
	private String statusCode = "200";
	
	public R getData() {
		return data;
	}

	public void setData(R data) {
		this.data = data;
	}
	

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public Integer getTotal() {
		return total;		
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	@SuppressWarnings("unchecked")
	public void addSingleObject(Object object) {
		if(object != null)
			data = ((R) object);
		
		if (data!=null)
			this.total = 1;
		else
			this.total = 0;
	}
}