package com.fashion.celebrity.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

public class SurveyDtos {
    @ToString
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        private String skinCode;

        @NotNull
        @Positive
        private int height;

        @NotNull
        @Positive
        private int weight;

        @NotEmpty
        private String colorCode1;

        @NotEmpty
        private String colorCode2;
    }
}
