package com.fashion.celebrity.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResStyleDto {
    private String styleCode;
    private String styleDesc;
}