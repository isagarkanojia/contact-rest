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

	@JsonIgnore private Instant modifiedDate;
	@JsonIgnore private Instant createdDate;
	@JsonIgnore private String modifiedBy;
	@JsonIgnore private String createdBy;
	@JsonIgnore private Boolean retired;


	@PrePersist
	protected void onCreate()
	{
		createdDate = Instant.now();
		modifiedDate = Instant.now();
		retired=false;
		
	}
	
	@PreUpdate
	protected void onUpdate()
	{
		modifiedDate = Instant.now();
		
	}





	
	
	

}
