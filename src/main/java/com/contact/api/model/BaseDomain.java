package com.contact.api.model;

import java.time.Instant;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseDomain 
{

	@JsonIgnore private Instant modifieddate;
	@JsonIgnore private Instant createddate;
	@JsonIgnore private String modifiedby;
	@JsonIgnore private String createdby;
	@JsonIgnore private Boolean retired;


	@PrePersist
	protected void onCreate()
	{
		createddate = Instant.now();
		modifieddate = Instant.now();
		retired=false;
		
	}
	
	@PreUpdate
	protected void onUpdate()
	{
		modifieddate = Instant.now();
		
	}





	
	
	

}
