package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        private String password;

        @Setter
        private int count;

        @Setter
        private String status;
    }

    @Data
    public static class Response {
        private String email = "";
        private String status = "";
        private String accessToken = "";
        private String refreshToken = "";
    }
}
