package com.fashion.celebrity.auth.common.dto;

public enum SurveyResCode {
    SV001("성공적으로 등록되었습니다."),
    SV002("성공적으로 조회되었습니다."),
    SV003("성공적으로 수정되었습니다."),
    SV004("성공적으로 삭제되었습니다."),
    SV005("조회 결과가 없습니다.");

    private final String message;

    SurveyResCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
