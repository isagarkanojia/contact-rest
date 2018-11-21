package com.contact.api.response;

import com.contact.api.payload.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Setter @Getter
public class BaseResponse {

	private Boolean success;
	private ErrorDto error;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public ErrorDto getError() {
		return error;
	}
	public void setError(ErrorDto error) {
		this.error = error;
	}
	
}