package com.fashion.celebrity.auth.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiDto {
    private boolean success;
    private String code;
    private String message;
    private Object obj;

    public ApiDto(boolean success, AuthResCode authResCode) {
        this.success = success;
        this.code = authResCode.name();
        this.message = authResCode.getMessage();
    }

    public ApiDto(boolean success, AuthResCode authResCode, Object obj) {
        this.success = success;
        this.code = authResCode.name();
        this.message = authResCode.getMessage();
        this.obj = obj;
    }
}
