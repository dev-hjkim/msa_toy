package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FindDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestId {
        @NotEmpty
        @Size(min=10, max=11)
        private String phone;
    }

    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestPw {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Size(min=10, max=11)
        private String phone;
    }

    @Data
    public static class ResponseIdPw {
        private String email = "";
        private String pathCode = "";
    }
}
