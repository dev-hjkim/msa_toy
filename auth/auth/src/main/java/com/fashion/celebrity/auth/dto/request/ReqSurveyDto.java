package com.fashion.celebrity.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqSurveyDto {
    private String username;
    private String skin;
    private String height;
    private String weight;
    private String bodyGood1;
    private String bodyGood2;
    private String bodyBad1;
    private String bodyBad2;
    private String style;
    private String color1;
    private String color2;
}
