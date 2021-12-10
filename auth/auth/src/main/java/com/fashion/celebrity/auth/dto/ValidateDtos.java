package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ValidateDtos {
    @ToString
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestEmail {
        @NotEmpty
        @Email
        private String email;

        private String certCode;
    }

    @ToString
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestNickname {
        @NotEmpty
        private String nickname;
    }
}
