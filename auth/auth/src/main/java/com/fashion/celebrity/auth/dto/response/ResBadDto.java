package com.fashion.celebrity.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResBadDto {
    private String badCode;
    private String badDesc;
}

