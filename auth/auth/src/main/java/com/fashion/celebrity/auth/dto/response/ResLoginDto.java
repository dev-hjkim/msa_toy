package com.fashion.celebrity.auth.dto.response;

import lombok.Data;

@Data
public class ResLoginDto {
    private String username;
    private String status;
    private String accessToken;
    private String refreshToken;
    private String count;
}
