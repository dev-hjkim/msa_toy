package com.fashion.celebrity.auth.common.dto;

public enum AuthResCode {
    AU001("성공적으로 등록되었습니다."),
    AU002("성공적으로 조회되었습니다."),
    AU003("성공적으로 수정되었습니다."),
    AU004("성공적으로 삭제되었습니다."),
    AU005("조회 결과가 없습니다."),
    AU006("이미 존재하는 아이디입니다."),
    AU007("인증번호를 발송하였습니다."),
    AU008("인증번호 발송에 실패하였습니다."),
    AU009("이메일 인증에 성공하였습니다."),
    AU010("이메일 인증에 실패하였습니다."),
    AU011("이미 존재하는 닉네임입니다."),
    AU012("사용 가능한 닉네임입니다."),
    AU013("로그인에 성공하였습니다."),
    AU014("아이디 또는 비밀번호가 일치하지 않습니다."),
    AU015("가입을 완료해주세요."),
    AU016("잠금되었거나 중지된 계정입니다."),
    AU017("로그인 5회 이상 오류로 계정이 잠금되었습니다."),
    AU018("access token이 정상적으로 재발급되었습니다."),
    AU019("재로그인이 필요합니다."),
    AU020("처리 중 오류가 발생하였습니다.");


    private final String message;

    AuthResCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
