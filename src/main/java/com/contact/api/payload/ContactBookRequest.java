package com.contact.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class ContactBookRequest {
   
	@NotBlank
    @Size(min = 4, max = 40)
    private String name;
    
}