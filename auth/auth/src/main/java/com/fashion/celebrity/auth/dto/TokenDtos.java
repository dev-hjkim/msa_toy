package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class TokenDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestRenew {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        private String accessToken;

        @NotEmpty
        private String refreshToken;
    }

    @Data
    public static class ResponseRenew {
        private String accessToken = "";
    }
}
