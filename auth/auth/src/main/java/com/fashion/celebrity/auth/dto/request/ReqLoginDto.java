package com.fashion.celebrity.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqLoginDto {
    private String username;
    private String password;
    private String pathCode;

    @Setter
    private Integer count;

    @Setter
    private String status;
}
