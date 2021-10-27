package com.fashion.celebrity.auth.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiInfo {
    private boolean Success;
    private String code;
    private String message;
    private Object obj;
}
