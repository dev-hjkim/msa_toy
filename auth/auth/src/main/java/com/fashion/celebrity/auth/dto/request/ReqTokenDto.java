package com.fashion.celebrity.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqTokenDto {
    private String username;
    private String accessToken;
    private String refreshToken;
}
