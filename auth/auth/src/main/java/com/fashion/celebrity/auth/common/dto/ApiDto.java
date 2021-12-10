package com.fashion.celebrity.auth.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiDto {
    private boolean Success;
    private String code;
    private String message;
    private Object obj;
}
