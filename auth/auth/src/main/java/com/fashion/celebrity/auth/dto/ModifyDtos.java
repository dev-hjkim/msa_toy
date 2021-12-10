package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ModifyDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestPw {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Setter
        private String password;
    }
}
