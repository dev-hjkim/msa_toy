package com.fashion.celebrity.auth.common.model;

import lombok.Data;

@Data
public class ApiInfo {
    private boolean success;
    private String code;
    private String message;
    private Object obj;
}
