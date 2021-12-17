package com.fashion.celebrity.auth.common.dto;

public enum CommonResCode {
    CM001("유효하지 않은 요청입니다."),
    CM002("토큰이 유효하지 않습니다."),
    CM003("유효하지 않은 method의 요청입니다."),
    CM004("누락된 필수 파라미터가 존재합니다."),
    CM005("요청 파라미터를 다시 한 번 확인해주세요."),
    CM006("데이터 타입을 다시 한 번 체크해주세요."),
    CM007("이미 존재하는 정보입니다."),
    CM008("DB 저장 과정에서 오류가 발생하였습니다."),
    CM009("누락된 값이 있어 DB 저장에 실패하였습니다.");


    private final String message;

    CommonResCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
