package com.fashion.celebrity.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqJoinDto {
    private String username;

    @Setter
    private String password;

    private String nickname;
    private String phone;
    private String gender;
    private String birthday;
    private String mktYn;
    private String pathCode;
}
