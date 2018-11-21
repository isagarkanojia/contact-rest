package com.contact.api.payload;

import com.contact.api.model.User;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
