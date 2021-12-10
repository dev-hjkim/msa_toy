package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Setter
        private String password;

        @NotEmpty
        private String nickname;

        @NotEmpty
        @Size(min=10, max=11)
        private String phone;

        @NotEmpty
        private String gender;

        @NotEmpty
        @Size(min=8, max=8)
        private String birthDate;

        @NotEmpty
        private String marketingYn;

        @NotEmpty
        private String pathCode;

    }
}
