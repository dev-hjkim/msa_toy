package com.fashion.celebrity.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqCertMailDto {
    private String username;
    private String certCode;
}
